/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
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
 *
 */
package org.amityregion5.projectx.common.entities.items.field;

/**
 * A FieldItem that is fired from a ProjectileWeapon onto the map.
 *
 * @author Mike DiBuduo
 */
public abstract class Projectile extends FieldItem {

    private static final long serialVersionUID = 1L;
    
    private int damage;

    public Projectile(int dm)
    {
        super(0, 0); //TODO Implement it!
        damage = dm;
    }

    public int getDamage()
    {
        return damage;
    }
}
