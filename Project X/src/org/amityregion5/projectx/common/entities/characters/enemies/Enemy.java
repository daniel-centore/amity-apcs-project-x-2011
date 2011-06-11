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

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.items.held.ZombieHands;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Character that attacks player
 *
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public abstract class Enemy extends CharacterEntity implements Damageable {

    private static final long serialVersionUID = 1L;
    private int value;
    private int hp; // hitpoints
    private final int maxHealth;
    private boolean hasHit = false;
    public static final double DEFAULT_SPEED = 1; //arbitrary speed

    /**
     * Creates an enemy with specified health.
     *
     * @param health
     */
    public Enemy(int max, int x, int y)
    {
        super(x, y);
        value = 5;
        hp = max;
        maxHealth = max;
        setMoveSpeed(DEFAULT_SPEED);

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
    @Override
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
        return hp <= 0;
    }

    /**
     * Removes health from character, then checks to see if it has been killed.
     *
     * @param damage the amount of health to remove
     */
    public int damage(int damage)
    {
        if(damage < 0)
            return 0;

        if(hp < damage)
        {
            int pr = hp;
            hp = 0;
            return pr;
        }

        hp -= damage;

        return damage;
    }

    /**
     * Adds health to hp. If the total exceeds maximum, it is set to maximum.
     *
     * @param health health to add
     */
    public void heal(int health)
    {
        hp += health;
        if(hp > maxHealth)
            hp = maxHealth;
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Enemy";
//    }
    private static final BufferedImage image = ImageHandler.loadImage("Enemy");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    public int getMaxHp()
    {
        return maxHealth;
    }

    /**
     * Convenience method for setMoveSpeed(0)
     */
    public void stop()
    {
        setMoveSpeed(0);
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    /**
     * @return How much score and money the enemy is worth
     */
    public int getValue()
    {
        return value;
    }
}
