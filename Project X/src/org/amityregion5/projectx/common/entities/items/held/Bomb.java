/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.items.held;

import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.tools.Sound;

/**
 *
 * @author Mike DiBuduo
 */
public class Bomb extends MeleeWeapon implements DamageDealing
{
    private static final long serialVersionUID = 1L;

    private static int SWORD_RATE = 42; // FIXME: arbitrary.

    public Bomb(int damage)
    {
        super(SWORD_RATE, damage);
    }

    @Override
    public String getDefaultImage()
    {
        return "sprites/E_Hands";// TODO make bomb sprite
    }

    @Override
    public Sound getSound()
    {
        return Sound.NULL_SOUND;
    }

    @Override
    public String getName()
    {
        return "Bomb";
    }

    @Override
    public int getAmmo()
    {
        return -1;
    }
}
