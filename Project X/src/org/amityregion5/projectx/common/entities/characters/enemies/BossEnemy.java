/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.characters.enemies;

import org.amityregion5.projectx.common.entities.items.held.Zombie_Hands;

/**
 *
 * @author Mike DiBuduo
 */
public class BossEnemy extends Enemy{

    private static final long serialVersionUID = 1L;

    public BossEnemy(int damage, int max, int x, int y)
    {
        super(max, x, y);
        setValue(10);
        addWeapon(new Zombie_Hands(damage));
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/Enemy_Big";
    }

}
