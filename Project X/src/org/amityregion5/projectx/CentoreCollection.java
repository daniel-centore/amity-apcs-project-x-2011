package org.amityregion5.projectx;
import java.io.EOFException;
import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Collection which uses revisions to eliminate
 * {@link ConcurrentModificationException} while being relatively efficient
 * 
 * @author Daniel Centore
 * @param E The type of data to store
 */
public class CentoreCollection<E> extends AbstractCollection<E>
{
	private CentoreNode<E> rootNode; // the first (empty) node in the collection
	private CentoreNode<E> endNode; // the closing (empty) node in the collection
	private volatile long revision; // first revision will be 0
	// shows how many iterators are present on each revision
	private volatile HashMap<Long, Integer> revisionUsage = new HashMap<Long, Integer>();
	private volatile int size; // Number of elements this collection contains

	/**
	 * Creates a new {@link CentoreCollection}
	 */
	public CentoreCollection()
	{
		clear();
	}

	@Override
	public synchronized boolean add(E arg0)
	{
		endNode.former.addNext(arg0, ++revision);

		return true;
	}

	@Override
	public void clear()
	{
		endNode = new CentoreNode<E>(null, null);
		rootNode = new CentoreNode<E>(endNode);
		revision = 0;
		size = 0;
	}

	@Override
	public boolean contains(Object arg0)
	{
		for (E e : this)
		{
			if (e == arg0)
				return true;
		}

		return false;
	}

	@Override
	public boolean isEmpty()
	{
		return (rootNode.next == endNode);
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Itr(revision);
	}

	@Override
	public synchronized boolean remove(Object arg0)
	{
		Iterator<E> i = iterator();
		while (i.hasNext())
		{
			if (i.next() == arg0)
			{
				i.remove();
				return true;
			}
		}

		return false;
	}

	@Override
	public int size()
	{
		return size;
	}

	/**
	 * Cleans out unreferenced revisions
	 */
	private void cleanupRevisions()
	{
		long rev = this.revision;

		boolean done = false;
		CentoreNode<E> node = rootNode;
		do
		{
			synchronized (node.map)
			{
				long r;
				try
				{
					r = node.latestRevision(rev);
				} catch (EOFException e)
				{
					return;
				}
				HashMap<Long, CentoreNode<E>> map = node.map;

				for (Long l : map.keySet())
				{
					if (l < r && !isAlive(l))
					{
						node.map.remove(l);
						// don't need to modify size here b/c it has already been taken care of
						// in the latest revision
					}
				}
			}

			try
			{
				node = node.next(rev);
			} catch (EOFException e)
			{
				done = true;
			}

		} while (!done);
	}

	/**
	 * Checks if a revision is being referenced by any iterators
	 * @param revision Revision number to check
	 * @return True if it is; false otherwise
	 */
	private boolean isAlive(long revision)
	{
		return (revisionUsage.containsKey(revision) && revisionUsage.get(revision) > 0);
	}

	/**
	 * The main iterator
	 * @author Daniel Centore
	 * @author Michael Zuo
	 */
	private class Itr implements Iterator<E>
	{
		private volatile long revision; // the revision this iterator runs across
		private volatile CentoreNode<E> current; // the last node that was returned

		/**
		 * Creates an iterator
		 * @param revision The revision it should traverse
		 */
		public Itr(long revision)
		{
			this.revision = revision;
			current = rootNode;

			synchronized (revisionUsage)
			{
				if (revisionUsage.containsKey(revision))
					revisionUsage.put(revision, revisionUsage.get(revision) + 1);
				else
					revisionUsage.put(revision, 1);
			}
		}

		@Override
		public void finalize()
		{
			try
			{
				super.finalize();
			} catch (Throwable e)
			{
				e.printStackTrace();
			}

			synchronized (revisionUsage)
			{
				revisionUsage.put(revision, revisionUsage.get(revision) - 1);
			}

			CentoreCollection.this.cleanupRevisions();
		}

		@Override
		public boolean hasNext()
		{
			try
			{
				current.next(revision);
			} catch (EOFException e)
			{
				return false;
			}

			return true;
		}

		@Override
		public E next()
		{
			try
			{
				return (current = current.next(revision)).holding;
			} catch (EOFException e)
			{
				throw new ArrayIndexOutOfBoundsException("No elements exist after this one");
			}
		}

		@Override
		public void remove()
		{
			if (current == rootNode)
				throw new IllegalStateException("Next has not yet been called");
			current.removeMe(++CentoreCollection.this.revision);
		}
	}

	private class CentoreNode<E>
	{
		private volatile E holding; // the object we are storing
		private volatile HashMap<Long, CentoreNode<E>> map; // list of revisions we link to
		private volatile CentoreNode<E> next; // the CentoreNode that follows this one on latest revision
		private volatile CentoreNode<E> former; // the CentoreNode that precedes this one on latest revision
		private volatile long largestRevision; // the largest revision contained in the map

		/**
		 * Default, only for use to make RootNode
		 */
		private CentoreNode(CentoreNode<E> endNode)
		{
			this(null, null);

			largestRevision = 0;
			map.put(0L, endNode);
			next = endNode;
			endNode.former = this;
		}

		/**
		 * Creates a CentoreNode
		 * @param holding What it will be storing
		 * @param former The node that comes before this one
		 */
		private CentoreNode(E holding, CentoreNode<E> former)
		{
			this.holding = holding;
			this.former = former;
			next = null;
			map = new HashMap<Long, CentoreNode<E>>();
		}

		/**
		 * Adds a node right after this one
		 * @param holding What the new node should hold
		 * @param revision The revision to put it in as
		 * @return The node we created
		 */
		private synchronized CentoreNode<E> addNext(E holding, long revision)
		{
			CentoreNode<E> node = new CentoreNode<E>(holding, this);
			synchronized (map)
			{
				largestRevision = revision;
				node.largestRevision = revision;
				map.put(revision, node); // Give us our next link in the chain
				node.map.put(revision, next); // Give the node its next link
			}
			next.former = node;
			node.next = next;

			next = node;

			CentoreCollection.this.size++;

			return node;
		}

		/**
		 * Removes this entity from its current revision links
		 * @param revision The new revision to mark the new link as
		 */
		private synchronized void removeMe(long revision)
		{
			synchronized (map)
			{
				former.largestRevision = revision;
				former.map.put(revision, next);
			}
			former.next = next;
			next.former = former;
			CentoreCollection.this.size--;
		}

		/**
		 * Finds the largest revision
		 * @param start The revision it should be <= to
		 * @return The revision number or -1 if no more
		 * @throws EOFException
		 */
		private long latestRevision(long start) throws EOFException
		{
			long r = Math.min(start, largestRevision);

			do
			{
				CentoreNode<E> node;
				synchronized (map)
				{
					if ((node = map.get(r)) != endNode && node != null)
						return r;
					else if (map.isEmpty())
						throw new EOFException("There are no more nodes");
				}

				r--;
			} while (r >= 0);

			throw new EOFException("There are no more nodes");
		}

		/**
		 * Finds the next Node along the path
		 * @param revision Finds the largest revision <= {@link revision}
		 * @return The node
		 * @throws EOFException If there are no more nodes along the path
		 */
		private CentoreNode<E> next(long revision) throws EOFException
		{
			return map.get(latestRevision(revision));
		}

		@Override
		public String toString()
		{
			return "CentoreNode [holding=" + holding + "]";
		}
	}

}
