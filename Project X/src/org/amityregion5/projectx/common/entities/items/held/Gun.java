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
 * A ProjectileWeapon which fires Bullets into field.
 *
 * @author Mike DiBuduo
 */
public class Gun extends ProjectileWeapon
{
    private static final long serialVersionUID = 1L;

    public Gun(int range, int ammo, int maxAmmo, int fireRate, int roundsPerMag, int mags)
    {
        super(range, ammo, maxAmmo, 
                fireRate, roundsPerMag, mags);
    }

    public int getDamage()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Pistol1";
    }
}
