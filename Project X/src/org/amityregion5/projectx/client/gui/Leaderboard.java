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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class Leaderboard {
    private static final int Y_MARGIN = 5;
    private static final int X_MARGIN = 30;
    private static final int USER_WIDTH = 150;
    private static final int B_MARG = 2;
    private static final int B_WIDTH = 4;
    private static final String BOARDSTR = "LEADERBOARD";

    /**
     * Gets the leaderboard image. Recommended width x height: 300 x 300.
     * @param width
     * @param height
     * @param players
     * @return
     */
    public static BufferedImage getBoard(int width, int height, ArrayList<PlayerEntity> players)
    {
        // create and set up canvas
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, width, height);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);

        // draw border
        g2.setStroke(new BasicStroke(B_WIDTH));
        g2.drawRect(B_MARG, B_MARG, width - B_MARG * 2, height - B_MARG * 2);

        // draw leaderboard title
        g2.setFont(g2.getFont().deriveFont(24.0f).deriveFont(Font.BOLD));
        int w = g2.getFontMetrics().stringWidth(BOARDSTR);
        int left = (width / 2) - (w / 2);
        g2.drawString(BOARDSTR, left, Y_MARGIN + g2.getFontMetrics().getHeight());

        int draw = Y_MARGIN + g2.getFontMetrics().getHeight() + 5;
        g2.setFont(g2.getFont().deriveFont(16.0f).deriveFont(Font.PLAIN));
        draw += g2.getFontMetrics().getHeight();

        Collections.sort(players);
        // draw players
        int vh = g2.getFontMetrics().getHeight() + 5;
        for (PlayerEntity p : players)
        {
            g2.drawString(p.getUsername(), X_MARGIN, draw);
            g2.drawString(String.valueOf(p.getPoints()), X_MARGIN + USER_WIDTH, draw);
            draw += vh;
        }

        return result;
    }

    // public static void main(String[] args)
    // {
    // JFrame frame = new JFrame();
    // frame.setSize(500, 500);
    // final ArrayList<PlayerEntity> p = new ArrayList<PlayerEntity>();
    // p.add(new PlayerEntity(0, 0, "bob"));
    // p.get(0).setPoints(20970);
    // p.add(new PlayerEntity(0, 0, "bill"));
    // p.get(1).setPoints(1000);
    // final JPanel panel = new JPanel() {
    // private static final long serialVersionUID = 503L;
    //
    // @Override
    // public void paintComponent(Graphics g)
    // {
    // g.drawImage(getBoard(300, 200, p), 0, 0, 300, 200, null);
    // }
    // };
    // frame.add(panel);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setVisible(true);
    //
    // }
}
