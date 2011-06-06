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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.amityregion5.projectx.client.Game;
import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.items.field.Area;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Handles repainting
 * 
 * @author Daniel Centore
 * @author Mike DiBuduo
 */
public class RepaintHandler extends Thread {

    public static final int HEALTHBAR_HEIGHT = 5; // default healthbar height

    private static boolean showingGrid = false;
    private static Game game; // game we are based from

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
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.white);
        g.fillRect(0, 0, GameWindow.GAME_WIDTH, GameWindow.GAME_HEIGHT);

        AbstractMap map = game.getMap();
        Image k = map.getBackground();

        if (k != null)
        {
            g.drawImage(k, 0, 0, null);
        }

        if (isShowingGrid())
        {
            drawGrid(g);
        }

        for (Entity e : map.getEntities()) // draw constant entities
        {
            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
        }

        synchronized (game.getEntityHandler())
        {
            for (Entity e : game.getEntityHandler().getEntities()) // draw temporary entities
            {
                if (e == null)
                    break;
                g.drawImage(e.getImage(), e.getX(), e.getY(), null);
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(1));

                if (e instanceof PlayerEntity)
                {
                    drawFiring((PlayerEntity) e, g);
                }

                if (e instanceof CharacterEntity)
                {
                    CharacterEntity ce = (CharacterEntity) e;
                    if (ce.hasWeapons())
                        drawWeapon(ce, g);
                }
            }

            for (Entity e : game.getEntityHandler().getEntities())
            {
                drawHealthbar(e, g);
            }

        }

        return img;
    }

    private static void drawHealthbar(Entity e, Graphics2D g)
    {
        if (e instanceof Damageable)
        {
            // Enemy en = (Enemy) e;
            Damageable d = (Damageable) e;
            double percent = (double) d.getHp() / d.getMaxHp();
            int x = e.getX();
            int y = e.getY() - HEALTHBAR_HEIGHT;
            g.setColor(Color.RED);
            g.fillRect(x, y, e.getWidth(), HEALTHBAR_HEIGHT);
            g.setColor(Color.GREEN);
            g.fillRect(x, y, (int) (e.getWidth() * percent), HEALTHBAR_HEIGHT);
        }
        Area a = game.getMap().getArea();
        double percent = (double) a.getHp() / a.getMaxHp();
        int x = a.getX();
        int y = a.getY() - 10;
        g.setColor(Color.RED);
        g.fillRect(x, y, a.getWidth(), HEALTHBAR_HEIGHT);
        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) (a.getWidth() * percent), HEALTHBAR_HEIGHT);
    }

    private static void drawFiring(PlayerEntity pe, Graphics2D g)
    {
        if (pe.hasWeapons())
        {
            Weapon wep = pe.getCurrWeapon();

            int x2 = (int) (Math.cos(Math.toRadians(pe.getDirectionFacing())) * wep.getRange()) + pe.getCenterX();
            int y2 = (int) (Math.sin(Math.toRadians(pe.getDirectionFacing())) * wep.getRange()) + pe.getCenterY();

            if (pe.getFired())
            {
                Stroke old = g.getStroke();
                Color oldColor = g.getColor();
                g.setStroke(new BasicStroke(6));
                g.setColor(Color.YELLOW);
                g.drawLine(pe.getCenterX(), pe.getCenterY(), x2, y2);
                g.setColor(oldColor);
                g.setStroke(old);
                pe.setFired(false);
            }
        }
    }

    private static void drawWeapon(CharacterEntity pe, Graphics2D g)
    {
        // draw the weapon
        BufferedImage wepImg = pe.getCurrWeapon().getImage();
        AffineTransform at = new AffineTransform();
        at.translate(pe.getCenterX(), pe.getCenterY());
        at.rotate(Math.toRadians(pe.getDirectionFacing()));
        at.translate(0, -1 * wepImg.getHeight() / 2);
        g.drawImage(wepImg, at, null);
    }

    private static void drawGrid(Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.green);
        g.setStroke(new BasicStroke(2f));
        for (int i = 0; i < GameWindow.GAME_HEIGHT; i += PopupMenuHandler.GRID_SIZE)
        {
            g.drawLine(0, i, GameWindow.GAME_WIDTH, i);
        }

        for (int i = 0; i < GameWindow.GAME_WIDTH; i += PopupMenuHandler.GRID_SIZE)
        {
            g.drawLine(i, 0, i, GameWindow.GAME_HEIGHT);
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public static void setShowingGrid(boolean showingGrid)
    {
        RepaintHandler.showingGrid = showingGrid;
    }

    public static boolean isShowingGrid()
    {
        return showingGrid;
    }

    public static void switchShowingGrid()
    {
        showingGrid = !showingGrid;
    }
}
