/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.common.entities.items.held;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.tools.Sound;

/**
 *
 * @author Mike DiBuduo
 */
public class BigZombieHands extends MeleeWeapon implements DamageDealing {

    private static final long serialVersionUID = 605L;
    private static int SWORD_RATE = 42;
    private static final BufferedImage image = ImageHandler.loadImage("E_Hands");

    public BigZombieHands(int damage)
    {
        super(SWORD_RATE, damage);
    }

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }

    @Override
    public Sound getSound()
    {
        return Sound.NULL_SOUND;
    }

    @Override
    public String getName()
    {
        return "Big Hands";
    }

    @Override
    public int getAmmo()
    {
        return -1;
    }
}
