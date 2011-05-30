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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import org.amityregion5.projectx.client.Game;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Handles repainting
 * 
 * @author Daniel Centore
 * @author Mike DiBuduo
 */
public class RepaintHandler extends Thread {

    private static Game game; // game we are based from
    public static final int CHAT_WIDTH = 500;
    public static final int CHAT_HEIGHT = 150;

    /**
     * Sets the game we are using
     * 
     * @param g Game to set it to
     */
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
        if (img == null)
        {
            return null; // save us from NPE later in this code!
        }
        Graphics2D g = (Graphics2D) img.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, GameWindow.GAME_WIDTH, GameWindow.GAME_HEIGHT);

        AbstractMap map = game.getMap();
        Image k = map.getBackground();

        if (k != null)
        {
            g.drawImage(k, 0, 0, null);
        }

        for (Entity e : map.getEntities()) // draw constant entities
        {
            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
        }

        for (Entity e : game.getEntities()) // draw temporary entities
        {
            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
            g.setColor(Color.WHITE);
            g.draw(e.getHitBox());
            if (e instanceof Player)
            {
                // draw a laser sight for weapon direction
                g.setColor(Color.red);
                int x2 = (int) (Math.cos(Math.toRadians(e.getDirectionFacing()))
                        * 800) + e.getCenterX();
                int y2 = (int) (Math.sin(Math.toRadians(e.getDirectionFacing()))
                        * 800) + e.getCenterY();
                g.drawLine(e.getCenterX(), e.getCenterY(),
                        x2, y2);
                // TODO draw the weapon. we need sprites for this!

                // TODO draw a fire if it just fired
                if (e.getFired())
                {
                    Stroke old = g.getStroke();
                    g.setStroke(new BasicStroke(4));
                    g.setColor(Color.YELLOW);
                    g.drawLine(e.getCenterX(), e.getCenterY(),
                            x2, y2);
                    g.setStroke(old);
                    e.setFired(false);
                }
            }
        }

        // draw chat
        BufferedImage chat = ChatDrawing.getChat(CHAT_WIDTH, CHAT_HEIGHT);
        if (chat != null)
        {
            g.drawImage(chat, 5,
                    img.getHeight(null) - CHAT_HEIGHT - 5,
                    null);
        }

        g.draw(game.getMap().getPlayArea());
        
        return img;
    }
}
