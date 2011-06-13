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
package org.amityregion5.projectx.common.entities.items.held;

import java.awt.image.BufferedImage;

import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * Class documentation.
 *
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class SniperRifle extends Gun implements DamageDealing, Upgradeable
{
    public static final long serialVersionUID = 605L;

    private final int UPGRADE_COST = 150;
    private static final BufferedImage image = ImageHandler.loadImage("Sniper");

    public SniperRifle()
    {
        super(1000, 100, 100, 1, 1, 30);
    }

    @Override
    public Sound getSound()
    {
        // TODO sniper rifle sound
        return null;
    }

    @Override
    public String getName()
    {
        return "M40";
    }

    public BufferedImage getDefaultImage()
    {
        return image;
    }

    public int getMagCost() {
        return super.getMagCost() / 10;
    }

    public int getUpgradeCost()
    {
        return UPGRADE_COST;
    }

    public void upgrade()
    {
        if (upgradeLevel < LVL_CAP)
        {
            upgradeLevel++;

            if (upgradeLevel % 3 == 2)
                setRPM(getRPM() + 1);

            setAttackRate(getAttackRate() + 1);
            setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE);
        }
    }
}
