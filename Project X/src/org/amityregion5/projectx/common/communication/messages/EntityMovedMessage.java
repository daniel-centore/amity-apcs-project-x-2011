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
 * @author Daniel Centore
 */
public class EntityMovedMessage extends Message {

    private static final long serialVersionUID = 1L;

    private long entityID; // The entity that moved
    private double newX; // new x coord
    private double newY; // new y coord
    private int newDir; // the new direction the entity is facing

    /**
     * Note that an entity has moved and/or turned.
     * 
     * @param entityID the id entity in question
     * @param newLoc the entity's new location. null indicates destruction
     * @param dir the entity's new location
     */
    private EntityMovedMessage(long entityID, Point2D newLoc, int dir)
    {
        newDir = dir;
        this.entityID = entityID;
        this.newX = newLoc.getX();
        this.newY = newLoc.getY();
    }

    /**
     * Note that an entity has changed
     * 
     * @param entity the entity in question
     */
    public EntityMovedMessage(Entity entity)
    {
        this(entity.getUniqueID(), entity.getLocation(), entity.getDirectionFacing());
    }

    /**
     * @return Gets the new entity location
     */
    public Point2D getNewLoc()
    {
        return new Point2D.Double(newX,newY);
    }

    /**
     * @return The id of the entity that was changed
     */
    public long getEntityID()
    {
        return entityID;
    }

    /**
     * @return the new direction that the entity will face
     */
    public int getNewDir()
    {
        return newDir;
    }
}
