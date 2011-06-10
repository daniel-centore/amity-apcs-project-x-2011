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
package org.amityregion5.projectx.common.entities.characters.enemies;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.items.held.Bomb;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Fast enemy that explodes upon reaching base
 * @author Mike Dibuduo
 * @author Mike Wenke
 */
public class SuicideBomber extends Enemy {

    private static final long serialVersionUID = 1L;
    public static final double MULTIPLIER = 1.5; // how many times faster than enemy default

    public SuicideBomber(int max, int x, int y)
    {
        super(max, x, y);
        setValue(2);
        addWeapon(new Bomb());
        setMoveSpeed(Enemy.DEFAULT_SPEED * MULTIPLIER);
    }
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Suicide_Bomber";
//    }
    private static final BufferedImage image = ImageHandler.loadImage("Suicide_Bomber");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }
}
