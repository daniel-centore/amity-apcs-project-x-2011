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

import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * Character controlled by a Client which fights Enemies. Capable of using multiple weapons.
 * 
 * @author Mike DiBuduo
 * @author Joe Stein
 */
public class Player extends CharacterEntity {
    public  static final int INITIAL_SPEED = 10; // initial move speed
    private static final long serialVersionUID = 1L;
    private static final int MAX_HEALTH = 100; // Player's health should be 100

    public Player()
    {
        super(MAX_HEALTH, 0, 0);
        this.setMoveSpeed(INITIAL_SPEED);
        /*System.out.println("adding weapon!");
        addWeapon(new Weapon(1000,1) {

            @Override
            public String getDefaultImage()
            {
                return "sprites/Pistol1";
            }
        }); */
    }

    public Player(Weapon wp)
    {
        this();
        addWeapon(wp);
    }

    public Player(int i, int j)
    {
        super(MAX_HEALTH, i, j);
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
}
