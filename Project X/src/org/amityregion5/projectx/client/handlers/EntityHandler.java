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
public class EntityHandler implements MessageListener {

    private static List<Entity> entities = new ArrayList<Entity>(); // the list of current entities

    public static void initialize(CommunicationHandler ch)
    {
        ch.registerListener(new EntityHandler());
    }

    private static synchronized void addEntity(Entity e)
    {
        for (Entity q : entities)
        {
            if (e.getUniqueID() == q.getUniqueID()) // already exists
               return;
        }
        
        e.selectImage(e.getDefaultImage());
        entities.add(e);
    }

    private static synchronized void removedEntity(Entity e)
    {
        entities.remove(e);
    }

    /**
     * @return The list of entities
     */
    public static synchronized List<Entity> getEntities()
    {
        return entities;
    }

    public void handle(Message m)
    {
        if (m instanceof EntityMovedMessage)
        {
            EntityMovedMessage em = (EntityMovedMessage) m;

            Entity e = em.getEntity();

            for (Entity q : entities)
                if (q.getUniqueID() == e.getUniqueID())
                {
                    q.setLocation(em.getNewLoc());
                    return;
                }

            throw new RuntimeException("BAD MOVE REQUEST: NON-EXISTANT ENTITY!");
        } else if (m instanceof AddEntityMessage)
        {
            System.out.println("added");
            AddEntityMessage aem = (AddEntityMessage) m;
            addEntity(aem.getEntity());
            GameWindow.fireRepaintRequired();
        }
    }

    public void tellSocketClosed()
    {
    }

}
