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

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.entities.Entity;

/**
 * Stores all current entities
 * 
 * @author Daniel Centore
 * @author Joe Stein
 * @author Michael Zuo
 */
public class EntityHandler {
    private Map<Long,Entity> entities = new HashMap<Long,Entity>(); // the set of current entities

    private synchronized void addEntity(Entity e) // adds an entity (should receive request from server)
    {
        if (entities.containsKey(e.getUniqueID()))
            return;

        e.selectImage(e.getDefaultImage());

        entities.put(e.getUniqueID(), e);

        GameWindow.fireRepaintRequired();
    }

    private synchronized Entity removeEntity(Entity e) // receive an entity (should receive request from server)
    {
        return entities.remove(e.getUniqueID());
    }

    /**
     * @return A collection of entities.
     */
    public synchronized Collection<Entity> getEntities()
    {
        return entities.values();
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
        //TODO: implement
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
        if (entities.containsKey(entity.getUniqueID())) {
            entities.put(entity.getUniqueID(), entity);
            return true;
        }

        return false;
    }
}
