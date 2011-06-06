/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * * This program is free software: you can redistribute it and/or
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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class Leaderboard
{
    private static final int Y_MARGIN = 20;
    private static final int X_MARGIN = 10;
    private static final int USER_WIDTH = 150;
    public static BufferedImage getBoard(int width, int height,
            ArrayList<PlayerEntity> players)
    {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        int draw = Y_MARGIN;
        
        for (PlayerEntity p : players)
        {
            System.out.println("drawing at " + X_MARGIN + "," + draw);
            g2.drawString(p.getUsername(), X_MARGIN, draw);
            g2.drawString(String.valueOf(p.getPoints()),X_MARGIN + USER_WIDTH, draw);
            draw += g2.getFontMetrics().getHeight() + 5;
        }

        return result;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        final ArrayList<PlayerEntity> p = new ArrayList<PlayerEntity>();
        p.add(new PlayerEntity(0,0,"bob"));
        p.add(new PlayerEntity(0,0,"bill"));
        final JPanel panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(getBoard(400,400,p), 0, 0, 400, 400, null);
            }
        };
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}