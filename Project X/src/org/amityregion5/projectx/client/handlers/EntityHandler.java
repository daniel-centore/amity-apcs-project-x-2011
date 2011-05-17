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
import javax.swing.text.html.parser.Entity;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.Message;

/**
 * Stores all current entities
 *
 * @author Daniel Centore
 */
public class EntityHandler implements MessageListener {

    private static List<Entity> entities = new ArrayList<Entity>();

    public static synchronized void addEntity(Entity e)
    {
        entities.add(e);
    }

    public static synchronized void removedEntity(Entity e)
    {
        entities.remove(e);
    }

    public static synchronized List<Entity> getEntities()
    {
        return entities;
    }

    public void handle(Message m)
    {
        //TODO: register this class and handle entity moved messages
    }

    public void tellSocketClosed()
    {
    }

}
