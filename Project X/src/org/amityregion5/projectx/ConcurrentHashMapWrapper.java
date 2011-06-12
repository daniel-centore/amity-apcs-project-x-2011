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

/**
 * Basically just wraps a {@link ConcurrentHashMap} so we don't have
 * to rewrite large amounts of code designed to use a {@link Collection}.
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

}
