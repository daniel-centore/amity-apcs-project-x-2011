/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    private static final int NUM_CHATS = 3;
    private static ArrayList<String> chats = new ArrayList<String>();
    private static boolean isChatting = false;
    private static StringBuffer currChat = new StringBuffer();

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

        Rectangle r = new Rectangle(10, 500, 500, 15);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) result.getGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        final int DISTANCE_BETWEEN_CHATS = result.getHeight() / NUM_CHATS - g2.getFont().getSize();
        System.out.println(DISTANCE_BETWEEN_CHATS);
        int testHeight = g2.getFont().getSize();
        final int MARGIN = testHeight * 2;
        int j = 0;// used for printing chats lower than the last
        for (int i = chats.size() - NUM_CHATS; i < chats.size(); i++)
        {
            g2.setColor(Color.BLACK);
            g2.drawString(chats.get(i), 20, j * DISTANCE_BETWEEN_CHATS + MARGIN);
            j++;
        }
        if (isChatting)
        {
            g2.setColor(Color.BLACK);
            g2.draw(r);
            g2.drawString(currChat.toString(), 15, 500 + g2.getFont().getSize());
            g2.setColor(Color.BLACK);
        }

        return result;
    }

    public static void drawChat(String chat)
    {
        chats.add(chat);
    }

    public static void setChat(boolean chat)
    {
        isChatting = chat;
    }

    public static void clearChat()
    {
        currChat.delete(0, currChat.length());
        isChatting = false;
    }

    public static String getTextChat()
    {
        return currChat.toString();
    }

    public static void backspace()
    {
        if (currChat.length() > 0)
        {
            currChat.delete(currChat.length() - 1, currChat.length());
        }
    }

    public static void addLetter(char c)
    {
        currChat.append(c);
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
        jf.setSize(600, 600);
        jf.add(jp, BorderLayout.CENTER);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
