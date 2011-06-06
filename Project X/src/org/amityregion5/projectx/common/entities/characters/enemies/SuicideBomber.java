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

/**
 * Fast enemy that explodes upon reaching base
 * @author Mike Dibuduo
 * @author Mike Wenke
 */
public class SuicideBomber extends Enemy {

    private static final long serialVersionUID = 1L;
    
    private int damage;

    public SuicideBomber(int damage, int max, int x, int y)
    {
        super(max, x, y);
        this.damage = damage;
        setMoveSpeed(Enemy.DEFAULT_SPEED * 4);
    }

    public int getDamage()
    {
        return damage;
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Suicide_Bomber";
    }

}
