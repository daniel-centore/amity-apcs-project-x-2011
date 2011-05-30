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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * An entity. All should be created server-side to guarantee they each get unique ids.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static transient long nextUniqueID = 0; // so each entity has a unique ID

    private final long uniqueID; // necessary to check identity content changes.
    private Point2D location; // the entity's location

    protected transient BufferedImage image; // default image representing the entity
    protected transient BufferedImage currentImage; // current image (ie including rotation)

    private int directionFacing; // Constants in EntityConstants
    private int directionMoving; // The direction we are moving in

    // How fast we move in pixels per time period as defined in EntityConstants
    private double moveSpeed;

    private transient boolean needUpdate; // do we need to resend the location?

    private transient boolean justFired; // did this client fire since the last repaint?
    private Rectangle hitBox; // a rudimentary hit boundary

    /**
     * Default entity constructor. Sets sane (but not really useful) default values. Use this directly as little as possible.
     */
    public Entity()
    {
        uniqueID = nextUniqueID++;
        hitBox = new Rectangle(1,1); // default tiny hitbox
        location = new Point2D.Double(0, 0);
        image = null;
        directionFacing = 0;
        directionMoving = 0;
        moveSpeed = 0;
        selectImage(getDefaultImage());
    }

    /**
     * Creates an entity
     * 
     * @param x X coordinate to put it at
     * @param y Y coordinate to put it at
     */
    public Entity(int x, int y)
    {
        this();

        setX(x);
        setY(y);
    }

    /**
     * Creates a new entity with the important information set.
     * 
     * @param image Image to represent it.
     * @param location Location to occupy.
     */
    public Entity(BufferedImage image, Point2D location)
    {
        this();

        this.image = image;
        this.location = location;
    }

    /**
     * @return X coordinate
     */
    public int getX()
    {
        return (int) location.getX();
    }

    /**
     * Sets the X coord.
     * 
     * @param x the new x-coordinate
     */
    public void setX(double x)
    {
        location.setLocation(x, location.getY());
        requestUpdate();
    }

    /**
     * Returns the y-coordinate of this Entity.
     * 
     * @return Y coordinate
     */
    public int getY()
    {
        return (int) location.getY();
    }

    /**
     * Sets the Y coord.
     * 
     * @param y the new y-coordinate
     */
    public void setY(double y)
    {
        location.setLocation(location.getX(), y);
        requestUpdate();
    }

    /**
     * Returns the image of this entity.
     * 
     * @return The image of this entity
     */
    public BufferedImage getImage()
    {
        return currentImage;
    }

    /**
     * Sets the image of the entity.
     * 
     * @param image Image to set it to
     */
    public void setImage(BufferedImage image)
    {
        this.image = image;
        updateImage();
    }

    /**
     * @return Current facing direction
     */
    public int getDirectionFacing()
    {
        return directionFacing;
    }

    /**
     * Sets the current direction we are facing.
     * 
     * @param directionFacing
     */
    public void setDirectionFacing(int directionFacing)
    {
        this.directionFacing = directionFacing;
        updateImage();
        requestUpdate();
    }

    /**
     * @return The direction we are moving in
     */
    public int getDirectionMoving()
    {
        return directionMoving;
    }

    /**
     * Sets the direction we are moving in.
     * 
     * @param directionMoving
     */
    public void setDirectionMoving(int directionMoving)
    {
        this.directionMoving = directionMoving;
        requestUpdate(); // TODO: do we need this?
    }

    /**
     * @return The move speed
     */
    public double getMoveSpeed()
    {
        return moveSpeed;
    }

    /**
     * Sets the move speed.
     * 
     * @param moveSpeed
     */
    public void setMoveSpeed(double moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Checks if two the Entity has a given id.
     * 
     * @param id The id to check for.
     * @return The equivalence of the id to the Entity's.
     */
    public boolean equals(long id)
    {
        return uniqueID == id;
    }

    /**
     * Checks if two Entities are the same using uniquely-assigned ids.
     * 
     * @param e The other Entity.
     * @return The equivalence of the two Entities' ids.
     */
    public boolean equals(Entity e)
    {
        return this.uniqueID == e.uniqueID;
    }

    /**
     * @return The width of the image
     */
    public int getWidth()
    {
        return currentImage.getWidth();
    }

    /**
     * @return The height of the image
     */
    public int getHeight()
    {
        return currentImage.getHeight();
    }

    /**
     * Sets the image of this entity to that from a particular source.
     * 
     * @param src A String representing the source (ie "Player" for "./resources/Player.png")
     */
    public void selectImage(String src)
    {
        // System.out.println(new File(RESOURCES + src + ".png").exists());
        image = ImageHandler.loadImage(src);
        updateImage();
    }

    protected void updateImage() // updates image including rotation, etc
    {
        if (image == null)
            return;
        currentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) currentImage.getGraphics();

        g2.drawImage(image, getAffineTransform(), null);
    }

    /**
     * @return The transformation you should use to transform images
     */
    public AffineTransform getAffineTransform()
    {
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(getDirectionFacing()), (getWidth() / 2), (getHeight() / 2));
        return at;
    }

    /**
     * @return The center X of this entity
     */
    public int getCenterX()
    {
        return getX() + (getWidth() / 2);
    }

    /**
     * @return The center Y of this entity
     */
    public int getCenterY()
    {
        return getY() + (getHeight() / 2);
    }

    /**
     * @return Returns a String representation of the default image
     */
    public abstract String getDefaultImage();

    /**
     * @return The unique ID of this entity
     */
    public long getUniqueID()
    {
        return uniqueID;
    }

    /**
     * @return The location of this entity
     */
    public Point2D getLocation()
    {
        return location;
    }

    /**
     * Sets the location of this entity
     * 
     * @param location Location to set it to
     */
    public void setLocation(Point2D location)
    {
        this.location = location;
        requestUpdate();
    }

    /**
     * Requests that the location be resent to clients.
     */
    public void requestUpdate()
    {
        needUpdate = true;
    }

    /**
     * Claims that an update has been completed.
     * 
     * (there's a microoptimization possible here, yes)
     * 
     * @return whether we need an update.
     */
    public boolean updateCheck()
    {
        boolean pre = needUpdate;
        needUpdate = false;
        return pre;
    }

    public boolean getFired()
    {
        return justFired;
    }

    public void setFired(boolean justFired)
    {
        this.justFired = justFired;
    }

    /**
     * Sets a rudimentary hit box for primitive collision detection.
     * NOTE danielle, we can remove this later. CALM DOWN
     * @param width width of the hit box
     * @param height height of the hit box
     */
    public void setHitBox(int width, int height)
    {
        hitBox = new Rectangle(width,height);
    }

    /*
     * Returns the hit box including the x and y.
     */
    public Rectangle getHitBox()
    {
        return new Rectangle((int) location.getX(), (int) location.getY(),
                (int) hitBox.getWidth(), (int) hitBox.getHeight());
    }

}
