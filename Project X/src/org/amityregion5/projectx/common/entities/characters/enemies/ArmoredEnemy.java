/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.characters.enemies;

import org.amityregion5.projectx.common.entities.items.held.Zombie_Hands;

/**
 * Slower enemy with damage-reducing armor
 * @author Michael Wenke
 */
public class ArmoredEnemy extends Enemy {

    private static final long serialVersionUID = 1L;

    private int armor;
    public static final double MULTIPLIER = (double)1 / 2; // How many times slower this is slowe than normal enemy

    public ArmoredEnemy(int ar, int max, int x, int y)
    {
        super(max, x, y);
        armor = ar;

        setMoveSpeed(Enemy.DEFAULT_SPEED * MULTIPLIER);
        setValue(2);
        
        addWeapon(new Zombie_Hands(1));

        // Should make this enemy slower than normal enemy, but normal enemy has slowest speed possible right now
    }

    public int getArmor()
    {
        return armor;
    }

    @Override
    public int damage(int amt)
    {
        return super.damage(amt - armor);
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Enemy_Big"; // Need real graphic!!
    }

}
