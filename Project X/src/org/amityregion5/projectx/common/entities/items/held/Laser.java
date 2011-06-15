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
 * Shoots through stuff. Ridiculously long range. Perfect accuracy.
 * Really, really fast.
 *
 * @author Michael Zuo
 * @author Joe Stein
 */
public class Laser extends Gun implements Upgradeable {

    private static final long serialVersionUID = 605L;
    private final int UPGRADE_COST = 200;

    public Laser()
    {
        super(900, 1000, 1000, 20, 100, 2);
    }

    private static final BufferedImage image = ImageHandler.loadImage("laser");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    @Override
    public String getName()
    {
        return "Laser";
    }

    public void upgrade()
    {
        if (upgradeLevel < LVL_CAP)
        {
            upgradeLevel++;
            setDamage(getDamage() + 2);
            setMaxAmmo(getMaxAmmo() + 100);
        }
    }

    public int getUpgradeCost()
    {
        return (int) (UPGRADE_COST * Math.pow(1.2, upgradeLevel - 1));
    }

    private final Point ORIG_TIP = new Point(59,45);
    @Override
    public Point getOrigWeaponTip()
    {
        return ORIG_TIP;
    }
}
