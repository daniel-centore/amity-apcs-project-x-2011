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
 **/
package org.amityregion5.projectx.common.entities.items.field;

import org.amityregion5.projectx.common.entities.Damageable;

/**
 * The basic Block. Used to protect prevent direct attack or movement. Has health which is drained by attacks.
 * 
 * @author Mike DiBuduo
 * @author Mike Wenke
 */
public class Block extends FieldItem implements Damageable {

    private static final long serialVersionUID = 1L;
    public static final int DEFAULT_HEALTH = 100;
    private int hp;
    private final int maxHealth;

    public Block(int x, int y)
    {
        this(x, y, DEFAULT_HEALTH);
    }

    public Block(int x, int y, int health)
    {
        super(x, y);
        hp = health;
        maxHealth = health;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }

    public void damage(int damage)
    {
        hp -= damage;
        killed();
    }

    /**
     * Not sure if Blocks should have a max health. For now it can be infinitely reinforced. Perhaps a fence has a max health and a wall can be infinitely upgraded?
     * 
     * @param health amount to be healed by
     */
    public void heal(int health)
    {
        hp += health;
    }

    /**
     * If hp drops to zero, the block is destroyed and removed
     */
    public boolean killed()
    {
        if (hp <= 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Block";
    }

    public int getMaxHp()
    {
        return maxHealth;
    }
}
