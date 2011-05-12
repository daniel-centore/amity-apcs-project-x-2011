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
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The window that the main game is played in
 *
 * Hello my name is daniel and this is a chnage
 * 
 * @author Daniel Centore
 * 
 */
public class Gui {

    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 765;

    private static JFrame frame;
    private static Image buffer;
    private static JComponent panel;

    public Gui()
    {
        frame = new JFrame("Amity Project X");
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);

        frame.add(panel = new JComponent() {

            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g)
            {
                if (buffer == null)
                {
                    super.paintComponent(g);
                    return;
                }

                int w = this.getWidth();
                int h = this.getHeight();

                int[] scale = scaleImage(buffer, w, h);

                Image img = createImage(w, h);
                img.getGraphics().drawImage(buffer, scale[2], scale[3],
                        scale[0], scale[1], null);

                g.drawImage(img, 0, 0, null);
            }

        }, BorderLayout.CENTER);

        frame.setVisible(true);

        // to keep focus on panel, even with menus
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
     * Repaints the panel
     */
    protected static void repaintPanel(Image img)
    {
        buffer = img;
        panel.repaint();
    }

    /**
     * Gets image scaling keeping its aspect ratio
     * 
     * @param image Image to scale
     * @param newWidth Width goal
     * @param newHeight Height goal
     * @return new int[] {Width to make it, height to make it, x to draw at, y
     * to draw at}
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

}
