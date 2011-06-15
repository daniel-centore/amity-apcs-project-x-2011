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

import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * A submachine gun. Starts out with low damage and range but high fire rate.
 *
 * @author Joe Stein
 */
public class Uzi extends Gun implements Upgradeable {

    private static final long serialVersionUID = 605L;
    private final int UPGRADE_COST = 150;

    public Uzi()
    {
        super(400, 500, 500, 10, 50, 10);
    }

    private static final BufferedImage image = ImageHandler.loadImage("Uzi");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    @Override
    public String getName()
    {
        return "Uzi";
    }

    public void upgrade()
    {
        if (upgradeLevel < LVL_CAP)
        {
            upgradeLevel++;
            setAttackRate(getAttackRate() + 1);
            setDamage(getDamage() + 2);
            setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE * 5);
        }
    }

    public int getUpgradeCost()
    {
        return (int) (UPGRADE_COST * Math.pow(1.2, upgradeLevel - 1));
    }

    private final Point ORIG_TIP = new Point(53,47);
    @Override
    public Point getOrigWeaponTip()
    {
        return ORIG_TIP;
    }
}
