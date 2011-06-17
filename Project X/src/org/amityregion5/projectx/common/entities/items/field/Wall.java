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
package org.amityregion5.projectx.common.entities.items.field;

import java.awt.image.BufferedImage;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Straight wall
 *
 * @author Mike DiBuduo
 */
public class Wall extends Block {
    
    private static final long serialVersionUID = 547L;
    
    public static final int DEFAULT_HEALTH = 10000;
    public static int PRICE = 250;

    public Wall(int x, int y)
    {
        this(x, y, DEFAULT_HEALTH);
    }

    public Wall(int x, int y, int health)
    {
        super(x, y, health);
        PRICE += 30;
    }

    public Wall(Entity e)
    {
        this((int) e.getX(), (int) e.getY());
    }
    
//    @Override
//    public String getDefaultImage()
//    {
//        return "sprites/Wall_Straight";
//    }

        private static final BufferedImage image = ImageHandler.loadImage("Wall_Straight");

    @Override
    public BufferedImage getDefaultImage()
    {
        return image;
    }
}
