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

import org.amityregion5.projectx.common.entities.items.DamageDealing;
/**
 * Proper Enemy melee weapon
 *
 * @author Joe Stein
 */
public class Zombie_Hands extends MeleeWeapon implements DamageDealing
{
    private static final long serialVersionUID = 1L;

    private static int SWORD_RATE = 42; // FIXME: arbitrary.

    public Zombie_Hands(int damage)
    {
        super(SWORD_RATE, damage, 0);
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/E_Hands";
    }

}