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
package org.amityregion5.projectx.common.entities;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * An entity
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public abstract class Entity {

    private Point location;
    private BufferedImage image;
    private int directionFacing;
    private int directionMoving;
    private double moveSpeed;

    /**
     * Default entity constructor. Use as little as possible.
     * Sets location to (0,0),
     */
    public Entity()
    {
        location = new Point(0, 0);
    }

    public Entity(BufferedImage image, int x, int y)
    {
        this.image = image;
        location = new Point(x, y);
    }

    public int getX()
    {
        return (int) location.getX();
    }

    public void setX(int x)
    {
        location.setLocation(x, location.getY());
    }

    public int getY()
    {
        return (int) location.getY();
    }

    public void setY(int y)
    {
        location.setLocation(location.getX(), y);
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public int getDirectionFacing()
    {
        return directionFacing;
    }

    public void setDirectionFacing(int directionFacing)
    {
        this.directionFacing = directionFacing;
    }

    public int getDirectionMoving()
    {
        return directionMoving;
    }

    public void setDirectionMoving(int directionMoving)
    {
        this.directionMoving = directionMoving;
    }

    public double getMoveSpeed()
    {
        return moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }
}
