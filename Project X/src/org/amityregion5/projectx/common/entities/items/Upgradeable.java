/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * * This program is free software: you can redistribute it and/or
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
package org.amityregion5.projectx.common.entities.items;

/**
 * Class documentation.
 *
 * @author Mike DiBuduo
 */
public interface Upgradeable
{
   public static final int AMMO_UPGRADE = 0;
   public static final int CLIP_UPGRADE = 1;
   public static final int FIRE_RATE_UPGRADE = 2;
   public static final int RANGE_UPGRADE = 3;
   public static final int DAMAGE_UPGRADE = 4;
   public static final int HEALTH_UPGRADE = 5; //For main wall and supporting fences
   
   public void upgradeMaxAmmo(int x);//x is index of an ArrayList(in this case 0) where the upgrade value will be stored
   public void upgradeClip(int x);
   public void upgradeFireRate(int x);
   public void upgradeRange(int x);
   public void upgradeDamage(int x);
   public void upgradeHealth(int x);
   
}
