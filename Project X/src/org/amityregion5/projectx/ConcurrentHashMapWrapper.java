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
package org.amityregion5.projectx;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityList;

/**
 * Basically just wraps a {@link ConcurrentHashMap} so we don't have
 * to rewrite large amounts of code designed to use a {@link Collection}.
 * 
 * It is specific to storing entities, but it should -NOT- be specific to
 * storing them in any particular way. For example, we use {@link EntityList}
 * for some custom uses.
 * 
 * @author Daniel Centore
 *
 */
public class ConcurrentHashMapWrapper extends AbstractCollection<Entity> {
    
    private ConcurrentHashMap<Long, Entity> map; // the map of items
    
    /**
     * Creates a {@link ConcurrentHashMapWrapper}
     */
    public ConcurrentHashMapWrapper()
    {
        map = new ConcurrentHashMap<Long, Entity>();
    }
    
    @Override
    public boolean add(Entity e)
    {
        map.put(e.getUniqueID(), e);
        
        return true; 
    }
    
    @Override
    public Iterator<Entity> iterator()
    {
        return map.values().iterator();
    }

    @Override
    public int size()
    {
        return map.size();
    }
    
    /**
     * Removes an entity
     * @param e entity to remove
     * @return the removed entity
     */
    public synchronized Entity removeEntity(Entity e)
    {
        return map.remove(e.getUniqueID());
    }

    /**
     * Removes an entity.
     * @param id the unique id of the entity to remove
     * @return the removed entity
     */
    public synchronized Entity removeEntity(long id)
    {
        return map.remove(id);
    }

    /**
     * Gets an entity from this EntityHandler's list.
     * 
     * @param uniqueId the unique id of the entity to get
     * @return the entity, or null if unique id was not matched
     */
    public Entity getEntity(long uniqueId)
    {
        return map.get(uniqueId);
    }

}
