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
 **/
package org.amityregion5.projectx.common.entities.items.field;

/**
 * The basic Block. Used to protect prevent direct attack or movement.
 * Has health which is drained by attacks.
 *
 * @author Mike DiBuduo
 */
public class Block extends FieldItem
{

   private int hp;

   public Block(int health)
   {
      hp = health;
   }

   public int getHp()
   {
      return hp;
   }

   public void setHp(int hp)
   {
      this.hp = hp;
   }
}
