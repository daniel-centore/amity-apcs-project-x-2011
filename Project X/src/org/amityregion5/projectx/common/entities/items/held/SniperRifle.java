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

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.ImageHandler;

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
    public String getName()
    {
        return "M40";
    }

    public BufferedImage getDefaultImage()
    {
        return image;
    }

    @Override
    public int getMagCost() {
        return super.getMagCost() / 10;
    }

    public int getUpgradeCost()
    {
        return (int) (UPGRADE_COST * Math.pow(1.2, upgradeLevel - 1));
    }

    public void upgrade()
    {
        if (upgradeLevel < LVL_CAP)
        {
            upgradeLevel++;

            setRPM(getRPM() + 2);

            setAttackRate(getAttackRate() + 1);
            setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE);
            setReloadTime(DEFAULT_RELOAD_TIME - 50*(upgradeLevel - 1));
        }
    }

    private final Point ORIG_TIP = new Point(75,42);
    @Override
    public Point getOrigWeaponTip()
    {
        return ORIG_TIP;
    }

    @Override
    public int getDamage(double dist) {

        if (dist > super.getRange())
            return 0; // out of range!

        return getDamage();
    }
}
