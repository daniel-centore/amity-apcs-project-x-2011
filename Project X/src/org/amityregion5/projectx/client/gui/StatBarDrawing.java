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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.items.held.Pistol;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class StatBarDrawing {

    private static final int B_WIDTH = 3;
    private static final int B_MARG = 1;
    private static final int SPACE = 5;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 50;
    private static int waveNumber = 0;
    private static String waveStr = "Wave " + String.valueOf(waveNumber);

    public static BufferedImage getStatBar(PlayerEntity p)
    {
        // create and set up canvas
        BufferedImage result = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.70f));
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);

        // draw border
        g2.setStroke(new BasicStroke(B_WIDTH));
        g2.drawRect(B_MARG,B_MARG, WIDTH - B_MARG * 2, HEIGHT - B_MARG * 2);

        int left = SPACE + 5;
        // draw cash
        StringBuilder b = new StringBuilder();
        b.append("Cash: $");
        b.append(p.getCash());
        
        g2.setFont(g2.getFont().deriveFont(14.0f));
        g2.drawString(b.toString(), left, g2.getFontMetrics().getHeight() + SPACE);
        
        // draw points
        b = new StringBuilder();
        b.append("Points: ");
        b.append(p.getPoints());

        g2.drawString(b.toString(), left, HEIGHT - SPACE * 2);

        try {
            // draw weapon info
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16.0f));
            g2.drawString(p.getCurrWeapon().getName(),140,g2.getFontMetrics().getHeight());
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN));
            
            // draw ammo
            b = new StringBuilder("Ammo: ");
            if (p.getCurrWeapon().getAmmo() < 0)
            {
                b.append("inf");
            } else
            {
                b.append(p.getCurrWeapon().getAmmo());
            }
            g2.drawString(b.toString(),140,HEIGHT - SPACE * 2);
        } catch (Exception ioobe)
        {
            g2.drawString("none",140,g2.getFontMetrics().getHeight());
        }

        // draw wave number
        g2.drawString(waveStr,WIDTH - 75,g2.getFontMetrics().getHeight());

        return result;
    }

    public static void setWaveNumber(int n)
    {
        waveNumber = n;
        waveStr = "Wave " + String.valueOf(waveNumber);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(700,200);
        final PlayerEntity p = new PlayerEntity(0,0,"bob");
        p.addWeapon(new Pistol());
        p.setPoints(20974);
        p.setCash(500);
        StatBarDrawing.setWaveNumber(2);
        final JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(getStatBar(p), 0, 0, null);
            }
        };
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
