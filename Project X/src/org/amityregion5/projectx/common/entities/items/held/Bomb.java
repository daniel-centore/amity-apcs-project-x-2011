/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.common.entities.items.held;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.DamageDealing;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 *
 * @author Mike DiBuduo
 */
public class Bomb extends MeleeWeapon implements DamageDealing {

    private static final long serialVersionUID = 605L;
    private static int SWORD_RATE = 42;
    private static int DAMAGE = 100;

    public Bomb()
    {
        super(SWORD_RATE, DAMAGE);
    }

    // TODO: make bomb sprite
    private static final BufferedImage image = ImageHandler.loadImage("E_Hands");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
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
