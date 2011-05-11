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
package org.amityregion5.projectx.client.entities;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.amityregion5.projectx.client.gui.RepaintHandler;
import org.amityregion5.projectx.client.tools.CollisionDetector;
import org.amityregion5.projectx.client.tools.PixelOutline;

/**
 * Represents an entity in the game
 * 
 * @author Daniel Centore
 *
 */
public abstract class Entity
{
    private BufferedImage img;  //Image of the entity
    public PixelOutline outline;

    /**
     * @return X location of the entity
     */
    public abstract int getX();

    /**
     * @return Y location of the entity
     */
    public abstract int getY();

    /**
     * Sets the x coordinate
     * @param x X coordinate
     */
    public abstract void setX(int x);

    /**
     * Sets the y coordinate
     * @param y Y coordinate
     */
    public abstract void setY(int y);

    /**
     * Increments the X coordinate
     * @param by How much to increment by
     */
    public void incrementX(int by)
    {
        setX(getX() + by);
        RepaintHandler.fireUpdateRequired();
    }

    /**
     * Increments the Y coordinate
     * @param by How much to increment by
     */
    public void incrementY(int by)
    {
        setY(getY() + by);
        RepaintHandler.fireUpdateRequired();
    }

    /**
     * @return The entity's width in pixels
     */
    public int getWidth()
    {
        return img.getWidth(null);
    }

    /**
     * @return The entity's height in pixels
     */
    public int getHeight()
    {
        return img.getHeight(null);
    }

    /**
     * Sets the entity's position
     * @param p Point to set it to
     * @deprecated Use setX(int) and setY(int)
     */
    @Deprecated
    public void setPosition(Point p)
    {
        setX(p.x);
        setY(p.y);
    }

    /**
     * Gets the entity's position
     * @return The point it is at
     * @deprecated Use getX() and getY()
     */
    @Deprecated
    public Point getPosition()
    {
        return new Point(getX(), getY());
    }

    /**
     * @return The image to draw of this entity
     */
    public BufferedImage getImage()
    {
        return img;
    }

    /**
     * Sets the image of this entity
     * @param img The image to set it to
     */
    public void setImage(BufferedImage img)
    {
        this.img = img;

        outline = new PixelOutline(img);
    }

    /**
     * Checks if this entity would be colliding with another
     * @param e The other entity
     * @param xOffset How much to offset it by
     * @param yOffset
     * @return Whether or not it would collide
     */
    public boolean hasCollision(Entity e, int xOffset, int yOffset)
    {
        return CollisionDetector.hasCollision(this, xOffset, yOffset, e);
    }

    /**
     * Checks if this entity is touching another
     * @param e The entity to check against
     * @return True if they are colliding; false otherwise
     */
    public boolean hasCollision(Entity e)
    {
        return hasCollision(e, 0, 0);
    }

    public boolean isAbove(Entity e)
    {
        return (this.getY() + this.getHeight() < e.getY() + e.getHeight());
    }

}
