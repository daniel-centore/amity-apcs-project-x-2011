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

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.held.Weapon;

public abstract class Character extends Entity
{

   private ArrayList<Weapon> weapons;
   private int currWeapon;
   private int hp;

   public Character(int health)
   {
      hp = health;
   }

   public int getCurrWeapon()
   {
      return currWeapon;
   }

   public void setCurrWeapon(int currWeapon)
   {
      this.currWeapon = currWeapon;
   }

   public int getHp()
   {
      return hp;
   }

   public void setHp(int hp)
   {
      this.hp = hp;
   }

   public void addWeapon(Weapon wp)
   {
      weapons.add(wp);
   }
}
