/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
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
package org.amityregion5.projectx.common.entities;

import java.util.Iterator;

import org.amityregion5.projectx.common.tools.ConcurrentHashMapWrapper;

/**
 * Holds all of our entities
 * 
 * @author Daniel Centore
 */
public class EntityList extends ConcurrentHashMapWrapper {

    private ConcurrentHashMapWrapper toRemove; // what should we remove next update cycle?

    /**
     * Creates an {@link EntityList}
     */
    public EntityList()
    {
        toRemove = new ConcurrentHashMapWrapper();
    }

    @Override
    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException("Instead, you should \"RequestRemove\" so that we notify our clients first");
    }

    @Override
    public synchronized Entity removeEntity(Entity e)
    {
        throw new UnsupportedOperationException("Instead, you should \"RequestRemove\" so that we notify our clients first");
    }

    @Override
    public synchronized Entity removeEntity(long id)
    {
        throw new UnsupportedOperationException("Instead, you should \"RequestRemove\" so that we notify our clients first");
    }

    /**
     * Requests that we remove this entity next update cycle
     * @param o Entity to remove
     * @return Whether or not the request has been added
     */
    public boolean requestRemove(Entity o)
    {
        return toRemove.add(o);
    }

    /**
     * Actually removes an entity from the list
     * @param o Oject to remove
     */
    public void reallyRemove(Object o)
    {
        toRemove.remove(o);
        super.remove(o);
    }

    public void reallyRemoveEntity(Long l)
    {
        toRemove.remove(getEntity(l));
        super.removeEntity(l);
    }

    /**
     * @return An iterator for going over entities that are scheduled to be removed
     */
    public Iterator<Entity> getRemovalIterator()
    {
        return toRemove.iterator();
    }

}
