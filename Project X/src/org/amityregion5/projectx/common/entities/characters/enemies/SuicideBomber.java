/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.characters.enemies;

/**
 *
 * @author dibuduomi
 */
public class SuicideBomber extends Enemy {

    private static final long serialVersionUID = 1L;
    
    private int damage;

    public SuicideBomber(int damage, int max, int x, int y)
    {
        super(max, x, y);
        this.damage = damage;
    }

    public int getDamage()
    {
        return damage;
    }

}
