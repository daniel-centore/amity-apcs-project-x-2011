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

import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.tools.Sound;

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
        super(400, 500, 500, 10, 50, 5);
    }

    @Override
    public Sound getSound()
    {
        return Sound.PISTOL_SHOT;
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
            setDamage(getDamage() + 1);
            setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE * 5);
        }
    }

    public int getUpgradeCost()
    {
        return UPGRADE_COST;
    }
}
