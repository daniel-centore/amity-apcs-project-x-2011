/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * * This program is free software: you can redistribute it and/or
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
package org.amityregion5.projectx.client.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.entities.Entity;

/**
 * Stores all current entities for the client-side Game
 * 
 * @author Daniel Centore
 * @author Joe Stein
 * @author Michael Zuo
 */
public class EntityHandler {

    private volatile HashMap<Long, Entity> entities = new HashMap<Long, Entity>(); // the set of current entities

    public synchronized void addEntity(Entity e) // adds an entity (should receive request from game)
    {
        if (entities.containsKey(e.getUniqueID()))
            return;

        e.selectImage(e.getDefaultImage());

        entities.put(e.getUniqueID(), e);

        GameWindow.fireRepaintRequired();
    }

    public synchronized Entity removeEntity(Entity e) // receive an entity (should receive request from game)
    {
        return entities.remove(e.getUniqueID());
    }

    /**
     * Gets an entity from this EntityHandler's list.
     * 
     * @param uniqueId the unique id of the entity to get
     * @return the entity, or null if unique id was not matched
     */
    public Entity getEntity(long uniqueId)
    {
        return entities.get(uniqueId);
    }

    /**
     * @return a collection of entities.
     */
    public synchronized Collection<Entity> getEntities()
    {
        return entities.values();
        // clone the collection to avoid CMEs!!
        // return ((HashMap<Long, Entity>) entities.clone()).values();
    }

    /**
     * @return A collection of entity ids.
     */
    public synchronized Collection<Long> getEntityIDs()
    {
        return entities.keySet();
    }

    public void tellSocketClosed()
    {
    }

    /**
     * Updates an entity.
     * 
     * @param entity Entity to compare (by id) and update to.
     * 
     * @return whether such an entity was properly updated.
     */
    public boolean updateEntity(Entity entity)
    {
        if (entities.containsKey(entity.getUniqueID()))
        {
            entities.put(entity.getUniqueID(), entity);
            return true;
        }

        return false;
    }
}
