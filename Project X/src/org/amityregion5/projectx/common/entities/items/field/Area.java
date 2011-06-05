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
package org.amityregion5.projectx.common.entities.items.field;

import org.amityregion5.projectx.common.entities.Damageable;

/**
 * Area in the center of the game
 *
 * @author Daniel Centore
 */
public class Area extends FieldItem implements Damageable {

    private static final long serialVersionUID = 1L;
    
    public static final int MAXIMUM_HEALTH = 1000;
    
    private int hp;

    public Area(int x, int y)
    {
        super(x, y);
        hp = MAXIMUM_HEALTH;
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Area";
    }

    /**
     * Checks to see if a character's health is below 0.
     */
    public boolean killed()
    {
        return hp <= 0;
    }

    /**
     * Removes health from character, then checks to see if it has been killed.
     *
     * @param damage the amount of health to remove
     */
    public void damage(int damage)
    {
        hp -= damage;
    }

    /**
     * Adds health to hp. If the total exceeds maximum, it is set to maximum.
     *
     * @param health health to add
     */
    public void heal(int health)
    {
        hp += health;
        if (hp > MAXIMUM_HEALTH)
            hp = MAXIMUM_HEALTH;
    }

    @Override
    public int getHp()
    {
        return hp;
    }

    public int getMaxHp()
    {
        return MAXIMUM_HEALTH;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

}
