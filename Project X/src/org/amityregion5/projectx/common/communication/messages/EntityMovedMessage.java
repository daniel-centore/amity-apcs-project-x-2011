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
 */
public class EntityMovedMessage extends Message {
    private static final long serialVersionUID = 1L;

    private Entity entity;
    private Point2D oldLoc;
    private Point2D newLoc;

    /**
     * Create meaningless message.
     */
    public EntityMovedMessage()
    {
        this.entity = null;
        this.oldLoc = null;
        this.newLoc = null;
    }

    /**
     * Note that an entity has moved elsewhere.
     * 
     * @param entity The entity in question.
     * @param oldLoc The entity's old location. null indicates creation.
     * @param newLoc The entity's new location. null indicates destruction.
     */
    public EntityMovedMessage(Entity entity, Point2D oldLoc, Point2D newLoc)
    {
        this.entity = entity;
        this.oldLoc = oldLoc;
        this.newLoc = newLoc;
    }
}
