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

import java.awt.geom.Point2D;

import org.amityregion5.projectx.common.entities.Entity;

/**
 * Notes that a change in the positions of entities has occured.
 * 
 * @author Michael Zuo <sreservoir@gmail.com>
 * @author Joe Stein
 */
public class EntityMovedMessage extends Message {

    private static final long serialVersionUID = 1L;

    private Entity entity; // The entity that moved
    private Point2D newLoc; // Where it moved to
    private int newDir; // the new direction the entity is facing

    /**
     * Note that an entity has moved and/or turned.
     * 
     * @param entity the entity in question
     * @param newLoc the entity's new location. null indicates destruction
     * @param dir the entity's new location
     */
    public EntityMovedMessage(Entity entity, Point2D newLoc, int dir)
    {
        newDir = dir;
        this.entity = entity;
        this.newLoc = newLoc;
    }

    /**
     * @return Gets the new entity location
     */
    public Point2D getNewLoc()
    {
        return newLoc;
    }

    /**
     * @return The entity that was changed
     */
    public Entity getEntity()
    {
        return entity;
    }

    /**
     * @return the new direction that the entity will face
     */
    public int getNewDir()
    {
        return newDir;
    }
}
