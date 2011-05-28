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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mike DiBuduo
 */
public class ChatDrawing
{

    private static final int NUM_CHATS = 5; //max number of chats that can be visible at once
    private static ArrayList<String> chats = new ArrayList<String>(); //all of the chats
    private static boolean isChatting = false;
    private static StringBuffer currChat = new StringBuffer();//the current chat
    private static final int X_MARGIN = 10;
    private static final int Y_MARGIN = 50;
    private static final int HEIGHT_MARGIN = 15;

    static
    {
        // adds three empty strings so we don't get an
        // ArrayIndexOutOfBoundsException later
        for (int i = 1; i <= NUM_CHATS; i++)
        {
            chats.add("");
        }
    }

    public static BufferedImage getChat(int width, int height)
    {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        Rectangle r = new Rectangle(X_MARGIN, (HEIGHT_MARGIN * NUM_CHATS), width - Y_MARGIN, g2.getFont().getSize() + 2);
        final int DISTANCE_BETWEEN_CHATS = g2.getFont().getSize();
        final int MARGIN = Y_MARGIN - (HEIGHT_MARGIN * 2);
        int j = 0;// used for printing chats lower than the last
        for (int i = chats.size() - NUM_CHATS; i < chats.size(); i++)
        {
            g2.setColor(Color.BLACK);
            g2.drawString(chats.get(i), X_MARGIN, j * DISTANCE_BETWEEN_CHATS + MARGIN);
            j++;
        }
        if (isChatting)
        {
            g2.setColor(Color.BLACK);
            g2.draw(r);
            g2.drawString(currChat.toString(), X_MARGIN + 5, g2.getFont().getSize() + (HEIGHT_MARGIN * NUM_CHATS));
            g2.setColor(Color.BLACK);
        }

        return result;
    }

    /**
     * adds the chat to the ArrayList
     * @param chat the chat that is about to be sent
     */
    public static void drawChat(String chat)
    {
        chats.add(chat);
    }

    /**public static void setChat(boolean chat)
    {
        isChatting = chat;
    }
     */

    /**
     * clears the chat box and sets isChatting to false
     */
    public static void clearChat()
    {
        currChat.delete(0, currChat.length());
        isChatting = false;
    }

    /**
     * Sets whether or not we are chatting.
     * @param chatting true if we're chatting, false if not
     */
    public static void setChatting(boolean chatting)
    {
        isChatting = chatting;
    }

    /**
     * gets the String value of the currChat
     * @return the current text that is in the chat box
     */
    public static String getTextChat()
    {
        return currChat.toString();
    }

    /**
     * removes one character from the end of currChat
     */
    public static void backspace()
    {
        if (currChat.length() > 0)
        {
            currChat.delete(currChat.length() - 1, currChat.length());
        }
    }

    /**
     * added another character to the end of currChat
     * @param c the character to add
     */
    public static void addLetter(char c)
    {
        currChat.append(c);
    }

    /**
     * Returns whether or not we are chatting.
     * @return whether or not we are chatting
     */
    public static boolean isChatting()
    {
        return isChatting;
    }

    static int i = 0;

    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        final JPanel jp = new JPanel()
        {

            @Override
            public void paintComponent(Graphics g)
            {
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.drawImage(getChat(this.getWidth(), this.getHeight()), 0, 0, null);
            }
        };
        jf.addKeyListener(new KeyListener()
        {

            public void keyTyped(KeyEvent e)
            {
                // ignore
            }

            public void keyPressed(KeyEvent e)
            {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_T && !isChatting)
                {
                    isChatting = true;
                } else if (keyCode == KeyEvent.VK_ENTER && isChatting)
                {
                    String s = currChat.toString().trim();
                    if (s.length() <= 0)
                    {
                        clearChat();
                    } else
                    {
                        drawChat(s);
                        clearChat();
                    }
                } else if (keyCode == KeyEvent.VK_BACK_SPACE)
                {
                    backspace();
                } 
                else if (isChatting && !(e.isActionKey() || keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_ALT))
                {
                    addLetter(e.getKeyChar());
                }

                jp.repaint();
            }

            public void keyReleased(KeyEvent e)
            {
                //
            }
        });
        jf.setSize(600, 200);
        jf.add(jp, BorderLayout.CENTER);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
