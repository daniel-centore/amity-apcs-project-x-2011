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
 * An item which releases ammo when used.
 * Generates Projectiles.
 * 
 * @author Mike DiBuduo
 * @author Joe Stein
 * @author Michael Wenke
 */
public abstract class ProjectileWeapon extends Weapon {

    private static final long serialVersionUID = 1L;

    private int ammo;
    private int maxAmmo;
    private int roundsPerMag;
    private int mags;
    private int damage;

    // FIXME: Please comment this!
    
    public ProjectileWeapon(int range, int startAmmo, int _maxAmmo, int rate, int rpm, int mags, int damage)
    {
        super(range, rate);
        ammo = startAmmo;
        maxAmmo = _maxAmmo;
        roundsPerMag = rpm;
        this.mags = mags;
        this.damage = damage;
    }

    public int getAmmo()
    {
        return ammo;
    }

    public void addAmmo(int rounds)
    {
        ammo = (rounds + ammo > maxAmmo ? maxAmmo : rounds + ammo);
    }

    public int getMaxAmmo()
    {
        return maxAmmo;
    }

    public int getRoundsPerMag()
    {
        return roundsPerMag;
    }

    public int getMags()
    {
        return mags;
    }

    public void setMags(int mags)
    {
        this.mags = mags;
    }

    public void reload()
    {
        if (ammo == maxAmmo || mags == 0)
            return;
        ammo = roundsPerMag - ammo;
        mags--;
    }

    public boolean hasAmmo()
    {
        return (ammo > 0);
    }

    public int getDamage()
    {
        return damage;
    }

    public boolean fire()
    {
        if (ammo > 0)
        {
            ammo--;
            return true;
        }
        return false;
    }

}
