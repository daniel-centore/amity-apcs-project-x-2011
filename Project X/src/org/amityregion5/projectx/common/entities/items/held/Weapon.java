/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.items.held;

/**
 * Basic Weapon
 * used to attack enemies
 * the Weapon itself does not actually do damage
 * @author Joe Stein
 */
public abstract class Weapon extends HeldItem {
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

    public int getAttackRate()
    {
        return attackRate;
    }
}
