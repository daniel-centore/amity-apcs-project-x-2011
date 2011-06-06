/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.characters.enemies;

/**
 * Slower enemy with damage-reducing armor
 * @author Michael Wenke
 */
public class ArmoredEnemy extends Enemy {
    private static final long serialVersionUID = 1L;

    private int armor;

    public ArmoredEnemy(int ar, int max, int x, int y)
    {
        super(max, x, y);
        armor = ar;

        setMoveSpeed(Enemy.DEFAULT_SPEED * 2 / 3);

        //Should make this enemy slower than normal enemy, but normal enemy has slowest speed possible right now
    }

    public int getArmor()
    {
        return armor;
    }

    public int damage(int amt)
    {
        return super.damage(amt - armor);
    }

    @Override
    public String getDefaultImage()
    {
        return null;
    }

}
