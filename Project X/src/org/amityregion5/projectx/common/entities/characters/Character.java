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
import org.amityregion5.projectx.common.entities.Damageable;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * Basic Character.
 * Has health and a set of Weapons.
 *
 * @author Mike DiBuduo
 * @author Mike Wenke
 */
public abstract class Character extends Entity implements Damageable
{
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Weapon> weapons;
    private int currWeapon;
    private int hp;
    private int maxHealth;

    /**
     * Create a character.
     *
     * @param health The amount of health the character begins with.
     * @param max_health The most amount of health this character can have.
     */
    public Character(int health, int maxHealth)
    {
        this.maxHealth = maxHealth;
        this.hp = health;
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

    /**
     * Gets the character's current HP.
     *
     * @return The character's HP.
     */
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
     * Adds a weapon to the character's arsenal.
     *
     * @param wp The weapon to be added.
     */
    public void addWeapon(Weapon wp)
    {
        weapons.add(wp);
    }

    /**
     * @return The character's maximum health.
     */
    public int getMaxHealth()
    {
        return maxHealth;
    }

    /**
     * Checks to see if a character's health is below 0.
     * If so, remove the character from the map.
     */
    public void killed()
    {
        if (hp <= 0)
        {
            //perhaps we need to rethink this, but calling the client is entirely wrong.
        }
    }

    /**
     * Removes health from character, then checks to see if it has been killed.
     * @param damage the amount of health to remove
     */
    public void damage(int damage)
    {
        hp -= damage;
        killed();
    }

    /**
     * Adds health to hp. If the total exceeds maximum, it is set to maximum.
     * 
     * @param health health to add
     */
    public void heal(int health)
    {
        hp += health;
        if (hp > maxHealth)
            hp = maxHealth;
    }
}
