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
import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * Basic melee weapon.
 * NOTE: Not used. And it doesn't have a sprite ATM.
 *
 * @author Mike DiBuduo
 * @author Joe Stein
 *
 */
public class Sword extends MeleeWeapon implements Upgradeable {

    private static final long serialVersionUID = 555L;
    private static int SWORD_RATE = 42; // FIXME: arbitrary.

    public Sword(int damage)
    {
        super(SWORD_RATE, damage);
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Pistol_1";
//    }
    private static final BufferedImage image = ImageHandler.loadImage("Pistol_1");

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
        return "Sword";
    }

    @Override
    public int getAmmo()
    {
        return -1;
    }

    public void upgrade()
    {
        upgradeLevel++;
    }

    public int getUpgradeCost()
    {
        return 0;
        //none yet
    }
}
