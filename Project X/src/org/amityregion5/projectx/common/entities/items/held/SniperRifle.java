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
import org.amityregion5.projectx.common.tools.Sound;

/**
 * Class documentation.
 *
 * @author Mike DiBuduo
 */
public class SniperRifle extends Gun implements DamageDealing, Upgradeable
{

    private final int UPGRADE_COST = 150;

    public SniperRifle(int range, int ammo, int maxAmmo, double fireRate, int roundsPerMag, int mags, int damage)
    {
        super(1000, -1, -1, 1, -1, -1, 20);
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
        //none
        return null;
        // TODO sniper rifle sprite
    }

    public int getUpgradeCost()
    {
        return UPGRADE_COST;
    }

    public void upgrade()
    {
        upgradeLevel++;
        setDamage(getDamage() + DAMAGE_UPGRADE);
        setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE * 5);
    }
}