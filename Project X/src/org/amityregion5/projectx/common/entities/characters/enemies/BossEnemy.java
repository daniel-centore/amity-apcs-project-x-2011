/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.common.entities.characters.enemies;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.held.ZombieHands;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 *
 * @author Mike DiBuduo
 */
public class BossEnemy extends Enemy {

    private static final long serialVersionUID = 605L;

    public BossEnemy(int damage, int max, int x, int y)
    {
        super(max, x, y);
        setValue(100);
        addWeapon(new ZombieHands(damage));
    }

    // TODO: need better boss image
    private static final BufferedImage image = ImageHandler.loadImage("Enemy_Boss");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }
}
