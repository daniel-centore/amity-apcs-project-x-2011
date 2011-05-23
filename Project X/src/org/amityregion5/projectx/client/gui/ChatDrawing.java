/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.client.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mike DiBuduo
 */
public class ChatDrawing {

    private static final int NUM_CHATS = 3;
    private static ArrayList<String> chats = new ArrayList<String>();
    public static BufferedImage getChat()
    {
        BufferedImage result = new BufferedImage(600, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        final int DISTANCE_BETWEEN_CHATS = result.getHeight() / NUM_CHATS;
        System.out.println(DISTANCE_BETWEEN_CHATS);
        int testHeight = g2.getFont().getSize();
        final int MARGIN = testHeight * 2;
        int j = 0;// used for printing chats lower than the last
        for(int i = chats.size() - NUM_CHATS; i < chats.size(); i++)
        {
            g2.setColor(Color.BLACK);
            g2.drawString(chats.get(chats.size() - NUM_CHATS), 20, j * DISTANCE_BETWEEN_CHATS + MARGIN);
            j++;
        }

        return result;
    }

    public static void drawChat(String chat)
    {
        chats.add(chat);

    }
}