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
 * Rocket-propelled grenade weapon. Currently not used.
 * We are planning to add this weapon to gameplay when we implement
 * splash damage.
 * 
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class RPG extends Gun implements Upgradeable, DamageDealing
{
    public static final long serialVersionUID = 1L;

    private final int UPGRADE_COST = 250;
    private final int AMMO_UPGRADE = 5;

    public RPG()
    {
        super(600, -1, -1, .25, -1, 10);
    }

    @Override
        public Sound getSound()
        {
            return null;
            //none yet
        }

    @Override
        public String getName()
        {
            return "RPG-7";
        }

    public BufferedImage getDefaultImage()
    {
        //none yet TODO rpg sprite
        return null;

    }

    public int getUpgradeCost()
    {
        return UPGRADE_COST;
    }

    public void upgrade()
    {
        upgradeLevel++;
        setDamage(getDamage() + DAMAGE_UPGRADE);
        setMaxAmmo(getMaxAmmo() + AMMO_UPGRADE);
    }

    @Override
        public boolean isSplash()
        {
            return true;
        }
}
