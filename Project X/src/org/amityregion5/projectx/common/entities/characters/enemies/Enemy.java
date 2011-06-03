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
package org.amityregion5.projectx.common.entities.characters.enemies;

import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.items.held.Sword;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * Character that attacks player
 *
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class Enemy extends CharacterEntity implements Damageable {

    private static final long serialVersionUID = 1L;

    private int hp; // hitpoints
    private final int maxHealth;

    public static final int DEFAULT_SPEED = 1; //arbitrary speed

    /**
     * Creates an enemy with specified health.
     *
     * @param health
     */
    public Enemy(int max, int x, int y)
    {
        super(x, y);
        hp = max;
        maxHealth = max;
        setMoveSpeed(DEFAULT_SPEED);
        addWeapon(new Sword(1));
        setCurrWeapon(0);
    }

    public Enemy(int max, Weapon wp, int x, int y)
    {
        this(max, x, y);
        addWeapon(wp);
    }

    /**
     * Gets the character's current HP.
     *
     * @return The character's HP.
     */
    public int getHp()
    {
        return hp;
    }

    /**
     * Changes the character's HP.
     *
     * @param hp The HP value to set to.
     */
    public void setHp(int hp)
    {
        this.hp = hp;
    }


    /**
     * Checks to see if a character's health is below 0.
     */
    public boolean killed()
    {
        if (hp <= 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Removes health from character, then checks to see if it has been killed.
     *
     * @param damage the amount of health to remove
     */
    public void damage(int damage)
    {
        hp -= damage;
        killed();
    }

    /**
     * Adds health to hp. If the total exceeds maximum, it is set to maximum.
     *
     * @param health health to add
     */
    public void heal(int health)
    {
        hp += health;
        if (hp > maxHealth)
            hp = maxHealth;
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Enemy";
    }

    public int getMaxHp()
    {
        return maxHealth;
    }
}