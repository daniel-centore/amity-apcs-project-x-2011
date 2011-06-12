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
 * An item which releases ammo when used.
 * Generates Projectiles.
 * 
 * @author Mike DiBuduo
 * @author Joe Stein
 * @author Michael Wenke
 */
public abstract class ProjectileWeapon extends Weapon {

    private static final long serialVersionUID = 1L;

    private int ammo; // current total rounds
    private int maxAmmo; // max total rounds
    private int damage;
    private Point weaponTip;

    /**
     * Creates a projectile weapon with the given characteristics.
     * @param range the range of the weapon, in linear pixels
     * @param startAmmo the ammo this weapon starts with
     * @param _maxAmmo the maximum amount of ammo this weapon can have
     * @param rate the attack rate of this weapon, in attacks per second
     * @param damage the amount of damage this weapon deals
     */
    public ProjectileWeapon(int range, int startAmmo, int _maxAmmo, double rate, int damage)
    {
        super(range, rate);
        ammo = startAmmo;
        maxAmmo = _maxAmmo;
        this.damage = damage;
    }

    @Override
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

    public void setMaxAmmo(int x)
    {
        maxAmmo = x;
    }

    public boolean hasAmmo()
    {
        return ammo != 0;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public boolean fire()
    {
        if (ammo != 0)
        {
            ammo--;
            return true;
        }
        return false;
    }

    public void setWeaponTip(Point p)
    {
        weaponTip = p;
    }

    public Point getWeaponTip()
    {
        return weaponTip;
    }

    public boolean isSplash()
   {
       return false;
    }

}
