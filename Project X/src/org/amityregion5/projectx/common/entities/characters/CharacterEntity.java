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
 *
 */
package org.amityregion5.projectx.common.entities.characters;

import java.util.ArrayList;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * Basic Character. Has health and a set of Weapons.
 *
 * @author Mike DiBuduo
 * @author Mike Wenke
 * @author Daniel Centore
 */
public abstract class CharacterEntity extends Entity {

    private static final long serialVersionUID = 1L;
    protected ArrayList<Weapon> weapons; // The weapons the character owns
    protected int currWeapon; // currently active weapon

    /**
     * Create a character.
     *
     * @param health The amount of health the character begins with.
     * @param max_health The most amount of health this character can have.
     */
    public CharacterEntity(int x, int y)
    {
        super(x, y);
        weapons = new ArrayList<Weapon>();
        currWeapon = 0;
    }

    public void changeWeapon(int wheelRotation)
    {
        int tmp = currWeapon + wheelRotation;
        while (tmp < 0)
        {
            tmp = currWeapon + tmp;
        }
        while (tmp > weapons.size() - 1)
        {
            tmp = currWeapon - tmp;
        }
    }

    /**
     * Gets a weapon the character has.
     *
     * @param weapon The weapon's identifier.
     * @return The weapon identified.
     */
    public Weapon getWeapon(int weapon)
    {
        return weapons.get(weapon);
    }

    /**
     * Gets the identifier for the character's current weapon.
     *
     * @return The current weapon's identifier.
     */
    public int getCurrWeapon()
    {
        return currWeapon;
    }

    /**
     * Changes the character's current weapon.
     *
     * @param newWeapon The identifier of the weapon to switch to.
     */
    public void setCurrWeapon(int newWeapon)
    {
        this.currWeapon = newWeapon;
    }

    public boolean hasWeapons()
    {
        return !weapons.isEmpty();
    }

    /**
     * Adds a weapon to the character's arsenal.
     *
     * @param wp The weapon to be added.
     */
    public void addWeapon(Weapon wp)
    {
        weapons.add(wp);
    }

    public void updateWeaponImages()
    {
        for (Weapon wep : weapons)
        {
            wep.selectImage(wep.getDefaultImage());
        }
    }
}
