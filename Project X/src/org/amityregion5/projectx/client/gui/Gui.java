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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.amityregion5.projectx.client.gui.input.KeyboardInput;
import org.amityregion5.projectx.client.gui.input.MouseInput;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Handles the main game Gui
 * 
 * @author Daniel Centore
 * 
 */
public class Gui extends JFrame {

    private static final long serialVersionUID = 1L;
    
    public static final int GAME_WIDTH = 1024; // the game size we will draw at before resizing
    public static final int GAME_HEIGHT = 765;
    
    private static Gui instance; // the instance of Gui
    private static AbstractMap map; // the current game map
    private static JComponent panel; // the panel we will draw on
    private static Image buffer; // the image the panel should draw

    public Gui(AbstractMap map)
    {
        super("Amity Project X");

        instance = this;

        Gui.map = map;

        this.setBackground(Color.black);

        this.addWindowListener(new AbstractWindowListener() {

            @Override
            public void windowClosing(WindowEvent arg0)
            {
                // TODO: make this close connections and such
                System.exit(0);
            }
        });

        panel = new JComponent() {

            private static final long serialVersionUID = 1L;

            @Override
            // called when we run RepaintHandler.fireUpdateRequired()
            public void paintComponent(Graphics g)
            {
                if (buffer == null)
                    return;

                int w = this.getWidth();
                int h = this.getHeight();

                int[] scale = scaleImage(buffer, w, h);

                Image img = createImage(w, h);
                img.getGraphics().drawImage(buffer, scale[2], scale[3], scale[0], scale[1], null);

                g.drawImage(img, 0, 0, null);
            }
        };

        // register listeners
        MouseInput mi = new MouseInput();
        panel.addMouseListener(mi);
        panel.addMouseMotionListener(mi);
        panel.addKeyListener(new KeyboardInput());

        this.add(panel, BorderLayout.CENTER);

        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.setVisible(true);

        // FIXME this is kind of obnoxious to the user... is it necessary?
        // makes sure game has focus always, even if we add menus and such later
        new Thread() {

            @Override
            public void run()
            {
                while (true)
                {
                    panel.requestFocusInWindow();
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                    }
                }
            }
        }.start();

    }

    /**
     * Gets image scaling keeping its aspect ratio
     * 
     * @param image Image to scale
     * @param newWidth Width goal
     * @param newHeight Height goal
     * @return new int[] {Width to make it, height to make it, x to draw at, y to draw at}
     */
    public static int[] scaleImage(Image image, int newWidth, int newHeight)
    {
        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;

        int x = 0;
        int y = 0;

        if (thumbRatio < aspectRatio)
        {
            y = newHeight;
            newHeight = (int) (newWidth / aspectRatio);
            y /= 2;
            y -= newHeight / 2;
        } else
        {
            x = newWidth;
            newWidth = (int) (newHeight * aspectRatio);
            x /= 2;
            x -= newWidth / 2;
        }

        return new int[]
        { newWidth, newHeight, x, y };
    }

    /**
     * Notifies the Gui that it needs to recreate and redraw the screen image
     */
    public static void fireRepaintRequired()
    {
        buffer = instance.getMapFlatImage();

        panel.repaint();
    }

    /**
     * Returns a flat image of the map with all the entities painted on it.
     * 
     * @return a flat image of the map with its entities
     */
    private Image getMapFlatImage()
    {
        Image img = Gui.createImage();
        Graphics2D g = (Graphics2D) img.getGraphics();

        Image k = map.getBackground();

        if (k != null)
            g.drawImage(k, 0, 0, null);

        for (Entity e : map.getEntities())
        {
            g.drawImage(e.getImage(), e.getX(), e.getY(), null);
        }

        return img;
    }

    /**
     * Creates a blank image the width and size of the Game
     * @return The image
     */
    public static Image createImage()
    {
        return instance.createImage(GAME_WIDTH, GAME_HEIGHT);
    }

    /**
     * @return The instance of Gui
     */
    public static Gui getInstance()
    {
        return instance;
    }

    /**
     * @return The width of the game panel right now
     */
    public static int getCurrentWidth()
    {
        return panel.getWidth();
    }

    /**
     * @return The height of the game panel right now
     */
    public static int getCurrentHeight()
    {
        return panel.getHeight();
    }

    public static void main(String[] args)
    {
        new Gui(null);
    }
}
