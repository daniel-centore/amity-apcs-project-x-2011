package org.amityregion5.projectx;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Collection which makes an ArrayList concurrent. Has the potential to skip elements.
 * Basically it only guarantees that most of the elements will be returned, and that
 * no {@link ConcurrentModificationException} will be thrown.
 * 
 * @author Daniel Centore
 * @param E The type of data to store
 */
public class ConcurrentArrayList<E> extends AbstractCollection<E> {

    private volatile ArrayList<E> backend; // backend which stores out objects

    /**
     * Creates a new {@link ConcurrentArrayList}
     */
    public ConcurrentArrayList()
    {
        backend = new ArrayList<E>();
    }

    @Override
    public synchronized boolean add(E arg0)
    {
        backend.add(arg0);
        return true;
    }

    @Override
    public void clear()
    {
        backend.clear();
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
        return backend.isEmpty();
    }

    @Override
    public Iterator<E> iterator()
    {
        return new Itr();
    }

    @Override
    public synchronized boolean remove(Object arg0)
    {
        backend.remove(arg0);

        return true;
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c)
    {
        for (Object o : c)
            remove(o);

        return true;
    }

    @Override
    public int size()
    {
        return backend.size();
    }

    /**
     * Iterator for iterating
     * 
     * @author Daniel Centore
     * @author Michael Zuo
     */
    private class Itr implements Iterator<E> {

        private volatile int currIndex; // the last index that was returned
        private volatile E nextObject; // the next object to return
        private volatile boolean didHasNext; // have we run hasNext()?
        private volatile boolean lastHasNext; // have we run next()?

        public Itr()
        {
            currIndex = -1;
        }

        @Override
        public boolean hasNext()
        {
            if (didHasNext)
                return lastHasNext;

            boolean result;
            currIndex++;
            if (currIndex < backend.size())
            {
                nextObject = backend.get(currIndex);
                // System.out.println((currIndex));
                result = true;
            } else
                result = false;

            didHasNext = true;
            return (lastHasNext = result);
        }

        @Override
        public E next()
        {
            if (didHasNext)
            {
                didHasNext = false;
                return nextObject;
            } else
                return backend.get(currIndex++);
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Not yet (but possibly will be!) supported");
        }
    }

}
