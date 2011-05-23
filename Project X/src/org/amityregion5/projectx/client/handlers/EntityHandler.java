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
import java.util.ArrayList;
import java.util.List;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.EntityMovedMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.entities.Entity;

/**
 * Stores all current entities
 * 
 * @author Daniel Centore
 */
public class EntityHandler
{

    private List<Entity> entities = new ArrayList<Entity>(); // the list of current entities

    private synchronized void addEntity(Entity e) // adds an entity (should receive request from server)
    {
        for (Entity q : entities)
        {
            if (e.getUniqueID() == q.getUniqueID()) // already exists
            {
                return;
            }
        }

        e.selectImage(e.getDefaultImage());
        entities.add(e);
        GameWindow.fireRepaintRequired();
    }

    private synchronized void removedEntity(Entity e) // receive an entity (should receive request from server)
    {
        entities.remove(e);
    }

    /**
     * @return The list of entities
     */
    public synchronized List<Entity> getEntities()
    {
        return entities;
    }

    public void tellSocketClosed()
    {
    }

    public void updateEntity(Entity entity, Point2D newLoc, int newDir)
    {
        for (Entity q : entities)
        {
            if (q.getUniqueID() == entity.getUniqueID())
            {
                q.setLocation(newLoc);
                q.setDirectionFacing(newDir);
                return;
            }
        }
    }
}
