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
package org.amityregion5.projectx.common.communication.messages;



/**
 * A message that says an entity has changed speed or direction.
 * Includes stopping (speed = 0).
 *
 * @author Joe Stein
 */
public class ClientMovingMessage extends Message {
    private int speed;
    private int dir;
    /**
     * Creates the new message.
     * @param id the id of the entity
     * @param speed the new speed of the entity
     * @param dir the new direction of the entity, in degrees
     */
    public ClientMovingMessage(int speed, int dir)
    {
        this.speed = speed;
        this.dir = dir;
    }

    /**
     * Returns the new speed of the entity.
     * @return the new speed of the entity
     */
    public int getSpeed()
    {
        return speed;
    }

    /**
     * Returns the new direction of the entity, in degrees.
     * @return the new direction of the entity
     */
    public int getDir()
    {
        return dir;
    }
}