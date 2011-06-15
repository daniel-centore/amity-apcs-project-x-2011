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

import java.awt.Point;

/**
 * A ProjectileWeapon that fires Bullets into field.
 *
 * @author Mike DiBuduo
 * @author Joe Stein
 */
public abstract class Gun extends ProjectileWeapon
{
    private static final long serialVersionUID = 605L;

    public static final Point DEFAULT_GUN_POINT = new Point(52,45);

    public Gun(int range, int ammo, int maxAmmo, double fireRate, int rpm, int damage)
    {
        super(range, ammo, maxAmmo, fireRate, rpm, damage);
    }



}
