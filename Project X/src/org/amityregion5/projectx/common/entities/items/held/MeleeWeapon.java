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
package org.amityregion5.projectx.common.entities.items.held;

/**
 * A Weapon which can be used only within melee range.
 *
 * @author Mike DiBuduo
 */
public abstract class MeleeWeapon extends Weapon {

    private static final long serialVersionUID = 605L;
    
    //shouldn't the range be the size of the weapon? 
    //Since this isn't implemented, this shouldn't be a problem :)
    public static final int MELEE_RANGE = 5; // default to 5 pixels range
    
    private int damage;

    public MeleeWeapon(int rate, int damage)
    {
        super(MELEE_RANGE, rate);
        this.damage = damage;
    }

    @Override
    public int getDamage()
    {
        return damage;
    }

    @Override
    public boolean fire()
    {
        // melee weapons don't run out of ammo
        return true;
    }
    
    @Override
    public boolean hasAmmo()
    {
        return true; // melee always have ammo! :D
    }
}
