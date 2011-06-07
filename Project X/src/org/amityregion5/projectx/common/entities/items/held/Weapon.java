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
import org.amityregion5.projectx.common.tools.Sound;

/**
 * A HeldItem used to effect effects on a target.
 * The Weapon itself does not necessarily do damage.
 *
 * @author Joe Stein
 * @author Cam Simpson
 */
public abstract class Weapon extends HeldItem implements DamageDealing {

    private static final long serialVersionUID = 1L;

    private int range; // Range (in pixels)
    private int attackRate;
    private Sound wepSound;

    public Weapon(int range, int rate)
    {
        this(range, rate, Sound.PISTOL_SHOT);
    }

    public Weapon(int range, int rate, Sound s)
    {
        this.range = range;
        this.attackRate = rate;
        this.wepSound = s;
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

    public Sound getSound()
    {
        return wepSound;
    }

    public abstract boolean hasAmmo();

    public abstract int getDamage();

    public int getDamage(double dist) {
        System.err.println("dist " + dist + " vs range " + range);

        if (dist > range)
            return 0; // out of range!

        // AN, accuracy: "random missing"
//        if (accuracy < 1) // 1 is perfect accuracy
//        {
//            double subrange = range / 2;
//            double accurate = subrange;
//            while (subrange > 1)
//                accurate += Math.random() * (subrange /= 2);
//            accurate *= accuracy / 2 + Math.random() * accuracy;
//
//            System.err.println(accurate + " from " + dist + "; range " + range);
//            if (dist > accurate) {
//                return 0; // missed
//            }
//        }

        double modifier = (4 - dist / range) / 4; // linear, down to 75% at full range.
        System.err.println("modifier is " + modifier);

        return (int) (getDamage() * modifier + 1);
    }

    public abstract boolean fire();

}
