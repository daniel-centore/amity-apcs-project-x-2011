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
package org.amityregion5.projectx.client.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import org.amityregion5.projectx.client.Game;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Handles repainting
 * 
 * @author Daniel Centore
 * @author Mike DiBuduo
 */
public class RepaintHandler extends Thread {

    private static Game game;

    public static void setGame(Game g)
    {
        game = g;
    }

    /**
     * Returns a flat image of the map with all the entities painted on it.
     * 
     * @return a flat image of the map with its entities
     */
    public static Image getMapFlatImage()
    {
        Image img = GameWindow.createImage();
        Graphics2D g = (Graphics2D) img.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, GameWindow.GAME_WIDTH, GameWindow.GAME_HEIGHT);

        AbstractMap map = game.getMap();
        Image k = map.getBackground();
        
        if (k != null)
        {
            g.drawImage(k, 0, 0, null);
        }

        for (Entity e : game.getEntities())
        {
            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
        }

        return img;
    }

}
