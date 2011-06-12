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
package org.amityregion5.projectx.common.entities.characters;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.held.ProjectileWeapon;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Character controlled by a Client which fights Enemies. Capable of using multiple weapons.
 * 
 * @author Mike DiBuduo
 * @author Joe Stein
 */
public class PlayerEntity extends CharacterEntity implements Comparable<PlayerEntity> {

    private static final long serialVersionUID = 594L;
    public static final int INITIAL_SPEED = 10; // initial move speed
    private int points; // points the player has
    private int cash; // how much money they have
    private String username; // their username

    /**
     * Creates a player
     * @param x X and Y coordinates
     * @param y
     */
    public PlayerEntity(int x, int y)
    {
        super(x, y);
    }

    /**
     * Creates a player
     * @param x X and Y coordinates
     * @param y
     * @param username Their username
     */
    public PlayerEntity(int x, int y, String username)
    {
        this(x, y);
        this.username = username;
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Player";
//    }
    private static final BufferedImage image = ImageHandler.loadImage("Player");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    /**
     * Increments X
     * @param offSetX How much to increment it by (can be negative)
     */
    public void incrementX(double offSetX)
    {
        setX(getX() + offSetX);
    }

    /**
     * Increments Y
     * @param offSetY How much to increment it by (can be negative)
     */
    public void incrementY(double offSetY)
    {
        setY(getY() + offSetY);
    }

    /**
     * Adds points
     * @param pts Number of points to add
     */
    public void addPoints(int pts)
    {
        points += pts;
    }

    /**
     * @return Number of points the player has
     */
    public int getPoints()
    {
        return points;
    }

    /**
     * Sets the player's points
     * @param amount What to set it to
     */
    public void setPoints(int amount)
    {
        points = amount;
    }

    /**
     * @return Amount of cash the player has
     */
    public int getCash()
    {
        return cash;
    }

    /**
     * Adds money
     * @param amount How much to add
     */
    public void addCash(int amount)
    {
        cash += amount;
    }

    public void spendCash(int amount)
    {
        cash -= amount;
    }

    public void setCash(int amount)
    {
        cash = amount;
    }

    public void setUsername(String name)
    {
        username = name;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public int compareTo(PlayerEntity arg0)
    {
        return (arg0.getPoints() - this.getPoints());
    }
}
