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
import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * A HeldItem used to effect effects on a target.
 *
 * @author Joe Stein
 * @author Cam Simpson
 */
public abstract class Weapon extends HeldItem implements DamageDealing {

    private static final long serialVersionUID = 1L;

    private int range; // Range (in pixels)
    private int attackRate;

    public Weapon(int range, int rate)
    {
        this.range = range;
        this.attackRate = rate;
    }

    public int getRange()
    {
        return range;
    }

    /**
     * Gets attack rate, in attacks per second.
     * @return attack rate in attacks per second
     */
    public int getAttackRate()
    {
        return attackRate;
    }
    public void setAttackRate(int x)
    {
        attackRate = x;
    }

    public abstract Sound getSound();
    public abstract boolean hasAmmo();
    public abstract int getAmmo();

    public abstract int getDamage();

    public int getDamage(double dist) {

        if (dist > range)
            return 0; // out of range!

        double modifier = (4 - dist / range) / 4; // linear, down to 75% at full range.

        return (int) (getDamage() * modifier + 1);
    }

    public abstract boolean fire();
    public abstract String getName();

    // contains upgrade stuff despite not implementing Upgradeable
    // would prefer not to put it in everything, though.
    protected final int DAMAGE_UPGRADE = 1;
    protected final int AMMO_UPGRADE = 25;

    protected int upgradeLevel = 1;

    public int getUpgradeLevel() {
        return upgradeLevel;
    }
}
