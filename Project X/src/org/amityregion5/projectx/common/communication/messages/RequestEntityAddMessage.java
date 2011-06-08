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
package org.amityregion5.projectx.common.communication.messages;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.items.field.Block;
import org.amityregion5.projectx.common.entities.items.field.Fence;
import org.amityregion5.projectx.common.entities.items.field.Wall;

/**
 * Requests that the server add an entity to the game :-)
 * 
 * @author Daniel Centore
 * 
 */
public class RequestEntityAddMessage extends Message {

    private static final long serialVersionUID = 1L;

    private String entity;
    private int x;
    private int y;

    public RequestEntityAddMessage(String s, int x, int y)
    {
        this.entity = s;
        this.x = x;
        this.y = y;
    }

    public String getEntity()
    {
        return entity;
    }

    public Entity getNewInstance()
    {
        if (entity.equals(EntityConstants.BLOCK))
            return new Block(x, y);
        else if (entity.equals(EntityConstants.FENCE))
            return new Fence(x, y);
        else if (entity.equals(EntityConstants.WALL))
            return new Wall(x, y);
        else
            throw new RuntimeException("Unimplemented entity requested!");
    }

}
