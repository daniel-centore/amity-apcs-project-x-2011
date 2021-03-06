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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.amityregion5.projectx.client.Game;
import org.amityregion5.projectx.client.gui.input.KeyboardInput;
import org.amityregion5.projectx.client.gui.input.MouseInput;
import org.amityregion5.projectx.common.entities.items.held.ProjectileWeapon;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Handles the main game Gui
 * 
 * @author Daniel Centore
 * @author Mike DiBuduo
 * 
 */
public class GameWindow extends JFrame {

    private static final BufferedImage MAP_IMAGE = ImageHandler.loadMap("TestMap");
    private static final long serialVersionUID = 594L;
    public static final int GAME_WIDTH = MAP_IMAGE.getWidth(); // the game size we will draw at before resizing
    public static final int GAME_HEIGHT = MAP_IMAGE.getHeight();
    private static GameWindow instance; // the instance of Gui
    private static JComponent panel; // the panel we will draw on
    private static Image buffer; // the image the panel should draw
    private int xOffset; // how much screen is offset horizontally
    private int yOffset; // how much screen is offset vertically
    private int lastWidth; // the last width of the window to reduce memory usage
    private int lastHeight; // the last height... ^^
    private static Game game;

    /**
     * Creates a GameWindow
     * @param map The map of the game
     * @param game The game
     */
    public GameWindow(AbstractMap map, Game game)
    {
        super("Amity Project X");
        
        this.game = game;
        
        instance = this;

        this.setBackground(Color.black);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent arg0)
            {
                System.exit(0);
            }
        });

        panel = new GamePanel();
        panel.addFocusListener(game);

        // register listeners
        MouseInput mi = new MouseInput();
        panel.addMouseListener(mi);
        panel.addMouseMotionListener(mi);
        panel.addMouseWheelListener(mi);
        panel.addKeyListener(new KeyboardInput());

        panel.addMouseListener(new PopupMenuHandler(game.getCommunicationHandler()));

        this.add(panel, BorderLayout.CENTER);

        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.setVisible(true);

        try
        {
            Thread.sleep(100);
        }
        catch(InterruptedException e)
        {
        }
        panel.requestFocusInWindow();

    }

    public static void setGame(Game g)
    {
        game = g;
    }

    private class GamePanel extends JComponent {

        private static final long serialVersionUID = 594L;

        @Override
        // called when we run RepaintHandler.fireUpdateRequired()
        public void paintComponent(Graphics g)
        {
            buffer = RepaintHandler.getMapFlatImage();

            int w = this.getWidth();
            int h = this.getHeight();

            if(w != lastWidth || h != lastHeight)
            {
                lastHeight = h;
                lastWidth = w;
            }

            Image buffer2 = createImage(getWidth(), getHeight());
            Graphics2D buffer2g = (Graphics2D) buffer2.getGraphics();
            buffer2g.setColor(Color.black);
            buffer2g.fillRect(0, 0, getWidth(), getHeight());

            int[] scale = scaleImage(buffer, w, h);

            buffer2g.drawImage(buffer, (xOffset = scale[2]), (yOffset = scale[3]), scale[0], scale[1], null);

            // draw chat
            BufferedImage chat = ChatDrawing.getChat(ChatDrawing.CHAT_WIDTH, ChatDrawing.CHAT_HEIGHT);
            if(chat != null)
            {
                buffer2g.drawImage(chat, xOffset, this.getHeight() - ChatDrawing.CHAT_HEIGHT - yOffset, null);
            }

            if(game.getMe() != null)
            {
                buffer2g.drawImage(StatBarDrawing.getStatBar(game.getMe()), xOffset,
                        this.getHeight() - StatBarDrawing.HEIGHT - yOffset, null);

                // draw ammo
                if(game.getMe().hasWeapons() && game.getMe().getCurrWeapon() != null)
                {
                    Point p = this.getMousePosition();
                    if(p != null)
                    {
                        int am = ((ProjectileWeapon) game.getMe().getCurrWeapon()).getAmmoInMag();
                        if(am > -1)
                        {
                            buffer2g.setFont(new Font("Sans", Font.BOLD, 16));
                            if(game.getMe().isReloading())
                            {
                                buffer2g.setColor(Color.red);
                                buffer2g.drawString(
                                        "-RELOADING-", (int) p.getX() - 5,
                                        (int) p.getY() - 5);
                            }
                            else if(am == 0)
                            {
                                buffer2g.setColor(Color.red);
                                buffer2g.drawString(
                                        "0", (int) p.getX() - 5,
                                        (int) p.getY() - 5);
                            }
                            else
                            {
                                buffer2g.setColor(Color.white);
                                buffer2g.drawString(
                                        String.valueOf(am), (int) p.getX() - 5,
                                        (int) p.getY() - 5);
                            }
                        }
                        else
                        {
                            buffer2g.drawString(
                                    "inf", (int) p.getX() - 5,
                                    (int) p.getY() - 5);
                        }
                    }
                }

                // draw reloading
            }


            g.drawImage(buffer2, 0, 0, null);
        }
    }

    /**
     * Closes this GameWindow.
     */
    public static void closeWindow()
    {
        GameWindow.getInstance().dispose();
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

        if(thumbRatio < aspectRatio)
        {
            y = newHeight;
            newHeight = (int) (newWidth / aspectRatio);
            y /= 2;
            y -= newHeight / 2;
        }
        else
        {
            x = newWidth;
            newWidth = (int) (newHeight * aspectRatio);
            x /= 2;
            x -= newWidth / 2;
        }

        return new int[]
                {
                    newWidth, newHeight, x, y
                };
    }

    /**
     * Notifies the Gui that it needs to recreate and redraw the screen image
     */
    public static void fireRepaintRequired()
    {
        if(panel == null)
        {
            System.err.println("Null panel");
            return;
        }
        panel.repaint();
    }

    /**
     * Creates a blank image the width and size of the Game
     * 
     * @return The image
     */
    public static Image createImage()
    {
        if(instance == null)
            return null;
        return instance.createImage(GAME_WIDTH, GAME_HEIGHT);
    }

    /**
     * @return A new BufferedImage the size and width of the screen
     */
    public static BufferedImage createBuffered()
    {
        return new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * @return The instance of Gui
     */
    public static GameWindow getInstance()
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

    /**
     * @return How many pixels the image is being drawn from the left
     */
    public int getXOffset()
    {
        return xOffset;
    }

    /**
     * @return How many pixels the image is being drawn from the top of the image
     */
    public int getYOffset()
    {
        return yOffset;
    }
}
