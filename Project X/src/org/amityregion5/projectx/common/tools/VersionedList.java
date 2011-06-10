/**
 * Copyright (c) 2011 res.
 * 
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation.
 */
package org.amityregion5.projectx.common.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public class VersionedList<E>
    implements Collection<E>
{
    protected volatile long limv = 0;
    protected volatile long last = 0;
    protected VersionedListNode head = new VersionedListNode(null);
    protected Map<Long,Integer> versionUsage
        = new HashMap<Long,Integer>();

    protected VersionedList<E> self = this;

    private void noop(Object... _) { }

    public int size() {
        int size = 0;
        for (E _ : this) {
            noop(_);
            size++;
        }
        return size;
    }

    public boolean isEmpty() {
        return !head.hasNext();
    }

    public boolean contains(Object o) {
        for (E e : this)
            if (o.equals(e))
                return true;
        return false;
    }

    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for (E e : this)
            array[i++] = e;
        return array;
    }
    @SuppressWarnings("unchecked")
    public<T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }

    public boolean add(E o) {
        throw new UnsupportedOperationException("unimplemented");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("unimplemented");
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean did = false;
        for (E o : c)
            if (add(o))
                did = true;
        return did;
    }

    public boolean removeAll(Collection<?> c) {
        boolean did = false;
        for (Object o : c)
            if (remove(o))
                return true;
        return did;
    }

    public boolean retainAll(Collection<?> c) {
        boolean did = false;
        for (E o : this)
            if (!c.contains(o))
                if (remove(o))
                    did = true;
        return did;
    }

    public void clear() {
        //removeAll(this); // inefficient!
        synchronized (this) {
            head.versionLinks.put(last++, null);
        }
    }

    public boolean equals(Object o) {
        // refusing to check the entire revison history!
        // and we can't check current values because we can only iterate.
        return this == o;
    }

    public int hashCode() {
        return (int) ((last & ((1 << 8) - 1)) ^ (last >> 8));
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            protected long vsn;
            protected VersionedListNode curr;

            { // pseudo-constructor
                vsn = last;
                curr = head.next(vsn);
                synchronized (versionUsage) {
                    versionUsage.put(vsn, versionUsage.get(vsn) + 1);
                }
            }

            public void finalize() {
                synchronized (versionUsage) {
                    versionUsage.put(vsn, versionUsage.get(vsn) - 1);
                }
            }

            public boolean hasNext() {
                return curr.hasNext(vsn);
            }

            public E next() {
                return (curr = curr.next(vsn)).elem;
            }

            public void remove() {
                self.remove(curr.elem);
            }
        };
    }

    protected class VersionedListNode
    {
        protected Map<Long,VersionedListNode> versionLinks
            = new HashMap<Long,VersionedListNode>();
        protected E elem;

        protected VersionedListNode(E e) {
            elem = e;
        }

        protected boolean hasNext() {
            return hasNext(last);
        }
        protected boolean hasNext(long high) {
            return next() != null;
        }

        protected VersionedListNode next() {
            return next(last);
        }
        protected VersionedListNode next(long high) {
            long best = -1;
            for (long vsn : versionLinks.keySet())
                if (vsn <= high && vsn > best)
                    best = vsn;
            if (best < 0)
                return null;
            return versionLinks.get(best);
        }
    }
}
