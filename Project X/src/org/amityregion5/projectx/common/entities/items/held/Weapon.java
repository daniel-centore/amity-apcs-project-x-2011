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
 * A HeldItem used to effect effects on a target.
 * The Weapon itself does not necessarily do damage.
 *
 * @author Joe Stein
 */
public abstract class Weapon extends HeldItem {
    private int range; // Range (in pixels)
    private int attackRate;

    public Weapon(int range, int rate)
    {
        this.range = range;
        this.attackRate = rate;
    }

    public int getRange()
    {
        return range;
    }

    public int getAttackRate()
    {
        return attackRate;
    }
}
