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
 * @author Mike DiBuduo
 */
package org.amityregion5.projectx.common.entities.characters;

import java.util.ArrayList;
import org.amityregion5.projectx.client.gui.Gui;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * Basic Character
 * has health as well as set of Weapons
 * @author Mike DiBuduo
 */
public abstract class Character extends Entity
{

   private ArrayList<Weapon> weapons;
   private int currWeapon;
   private int hp;
   private final int MAX_HEALTH;

   /**
    * Create a character.
    *
    * @param health The amount of health the character begins with.
    * @param max_health The most amount of health a character can have
    */
   public Character(int health, int max_health)
   {
      MAX_HEALTH = max_health;
      hp = health;
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
    * returns MAX_HEALTH
    * @return MAX_HEALTH
    */
   public int getMAX_HEALTH()
   {
      return MAX_HEALTH;
   }

   /**
    * checks to see if a character's health is bellow 0
    * if true, removes the character from the map
    */
   public void killed()
   {
      if (hp <= 0)
      {
         Gui.getMap().removeEntity(this);
      }
   }

   /**
    * removes health from character. Then checks to see if character is killed
    * @param damage the amount of health to remove
    */
   public void damage(int damage)
   {
      hp -= damage;
      killed();
   }

   /**
    * adds health to hp. If health to add makes hp over MAX_HEALTH, then hp = MAX_HEALTH
    * @param health health to add
    */
   public void heal(int health)
   {
        hp = (hp +health > MAX_HEALTH ? MAX_HEALTH : hp + health);
   }
}
