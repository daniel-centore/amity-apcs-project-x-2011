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

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * Proper Enemy melee weapon
 *
 * @author Joe Stein
 */
public class ZombieHands extends MeleeWeapon implements DamageDealing {

    private static final long serialVersionUID = 605L;
    private static int SWORD_RATE = 42; // FIXME: arbitrary.

    public ZombieHands(int damage)
    {
        super(SWORD_RATE, damage);
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/E_Hands";
//    }
    private static final BufferedImage image = ImageHandler.loadImage("E_Hands");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    @Override
    public Sound getSound()
    {
        return Sound.NULL_SOUND;
    }

    @Override
    public String getName()
    {
        return "Hands";
    }

    @Override
    public int getAmmo()
    {
        return -1;
    }

    public void upgrade()
    {
        upgradeLevel++;
        //no upgrades
    }

    public int getUpgradeCost()
    {
        return 0;
        //none
    }
}
