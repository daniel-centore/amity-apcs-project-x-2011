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


/**
 * Character controlled by a Client which fights Enemies. Capable of using multiple weapons.
 * 
 * @author Mike DiBuduo
 * @author Joe Stein
 */
public class PlayerEntity extends CharacterEntity {
    
    private static final long serialVersionUID = 1L;
    public  static final int INITIAL_SPEED = 10; // initial move speed
    private int points;
    private String username;

    /**
     * Creates a player
     * @param x X and Y coordinates
     * @param y
     */
    public PlayerEntity(int x, int y)
    {
        super(x, y);
    }

    public PlayerEntity(int x, int y, String username)
    {
        this(x,y);
        this.username = username;
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Player";
    }

    public void incrementX(double offSetX)
    {
        setX(getX() + offSetX);
    }

    public void incrementY(double offSetY)
    {
        setY(getY() + offSetY);
    }

    public void changePoints(int pts)
    {
        points += pts;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int amount)
    {
        points = amount;
    }

    public void setUsername(String name)
    {
        username = name;
    }

    public String getUsername()
    {
        return username;
    }
}
