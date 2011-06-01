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
package org.amityregion5.projectx.client;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.communication.RawCommunicationHandler;
import org.amityregion5.projectx.client.gui.ChatDrawing;
import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.client.gui.RepaintHandler;
import org.amityregion5.projectx.client.gui.input.InputHandler;
import org.amityregion5.projectx.client.gui.input.Keys;
import org.amityregion5.projectx.client.handlers.EntityHandler;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.RawListener;
import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.AddMeMessage;
import org.amityregion5.projectx.common.communication.messages.AnnounceMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.ClientMovingMessage;
import org.amityregion5.projectx.common.communication.messages.EntityMovedMessage;
import org.amityregion5.projectx.common.communication.messages.FiredMessage;
import org.amityregion5.projectx.common.communication.messages.FiringMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.communication.messages.RemoveEntityMessage;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.entities.items.held.Gun;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * The umbrella logistics class for the client-side game.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 * @author Mike DiBuduo
 * @author Mike Wenke
 */
public class Game implements GameInputListener, MessageListener, RawListener, FocusListener {

    private CommunicationHandler communicationHandler; // current CommunicationHandler
    private RawCommunicationHandler rch; // current raw communications handler
    private AbstractMap map; // current AbstractMap
    private Player me; // current Player (null at initialization!)
    private EntityHandler entityHandler; // current EntityHandler
    private List<Integer> depressedKeys = new ArrayList<Integer>();
    private DirectionalUpdateThread dUpThread;
    private int lastMouseX; // last mouse coordinates, so we can update direction as moving
    private int lastMouseY;
    private String username;

    public Game(CommunicationHandler ch, AbstractMap m, String username)
    {
        this.username = username;
        entityHandler = new EntityHandler();
        communicationHandler = ch;
        me = null;
        map = m;
        rch = new RawCommunicationHandler(ch.getServerIP(), username);
        dUpThread = new DirectionalUpdateThread();
        // do not start the dUpThread until me != null

        rch.registerRawListener(this);
        rch.start();
        ch.registerListener(this);
        InputHandler.registerListener(this);
        RepaintHandler.setGame(this);
    }

    public void mouseDragged(int x, int y)
    {
        mouseMoved(x, y);
    }

    public void mouseMoved(int x, int y)
    {
        lastMouseX = x;
        lastMouseY = y;

        // send to server and let server deal with it?
        if (me == null || me.getImage() == null)
        {
            return;
        }
        int x1 = me.getCenterX();
        int y1 = me.getCenterY();
        int angle = (int) Math.toDegrees(Math.atan2(y - y1, x - x1));

        me.setDirectionFacing(angle);
        GameWindow.fireRepaintRequired();
    }

    public void mousePressed(int x, int y, int button)
    {
        getCommunicationHandler().send(new FiringMessage(true));
    }

    public void mouseReleased(int x, int y, int button)
    {
        getCommunicationHandler().send(new FiringMessage(false));
    }

    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (ChatDrawing.isChatting() && !(e.isActionKey() || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_CONTROL))
        {
            ChatDrawing.addLetter(e.getKeyChar());
        } else if (ChatDrawing.isChatting())
        {
            if (keyCode == KeyEvent.VK_ENTER)
            {

                String s = ChatDrawing.getTextChat().trim();
                if (s.length() <= 0)
                {
                    ChatDrawing.clearChat();
                    return;
                }
                ChatMessage cm = new ChatMessage(ChatDrawing.getTextChat(), username);
                ChatDrawing.clearChat();
                getCommunicationHandler().send(cm);
            } else if (keyCode == KeyEvent.VK_BACK_SPACE)
            {
                ChatDrawing.backspace();
            }
        } else
        // not chatting
        {
            if (Keys.isKey(Keys.CHAT, keyCode))
            {
                ChatDrawing.setChatting(true);
                return;
            }
            if (me == null)
            {
                return;
            }
            if (!depressedKeys.contains(keyCode))
            {
                depressedKeys.add(keyCode);
            } else
            // key already pressed, don't need to do anything!
            {
                return;
            }
            int deg = calcMeDeg();
            if (deg == Integer.MIN_VALUE)
            {
                return;
            }
            ClientMovingMessage c = new ClientMovingMessage(Player.INITIAL_SPEED, deg);
            getCommunicationHandler().send(c);
        }
    }

    private int calcMeDeg()
    {
        int deg = Integer.MIN_VALUE;

        int left    = Keys.numIsKey(Keys.LEFT,  depressedKeys);
        int right   = Keys.numIsKey(Keys.RIGHT, depressedKeys);
        int up      = Keys.numIsKey(Keys.DOWN,  depressedKeys);
        int down    = Keys.numIsKey(Keys.UP,    depressedKeys);

        int lr = right - left;
        int ud = up - down;

        // .round() isn't actually needed here, for two reasons:
        // one, it's negligible for the players
        // two, it's always right anyway
        deg = (int) Math.toDegrees(Math.atan2(ud, lr));

        if (lr == 0 && ud == 0)
            deg = Integer.MIN_VALUE;

        return deg;
    }

    public void keyReleased(int keyCode)
    {
        int speed = Player.INITIAL_SPEED;

        while (depressedKeys.contains(keyCode))
        {
            depressedKeys.remove((Integer) keyCode);
        }
        if (depressedKeys.isEmpty()) // stop moving
        {
            speed = 0;
        }
        int deg = calcMeDeg();
        if (deg == Integer.MIN_VALUE)
        {
            speed = 0;
            deg = me.getDirectionMoving();
        }
        ClientMovingMessage c = new ClientMovingMessage(speed, deg);
        getCommunicationHandler().send(c);
    }

    /**
     * @returnm Current map
     */
    public AbstractMap getMap()
    {
        return map;
    }

    public void handle(Message m)
    {
        if (m instanceof AnnounceMessage)
        {
            AnnounceMessage am = (AnnounceMessage) m;
            ChatDrawing.drawChat(am.getText());
        } else if (m instanceof ChatMessage)
        {
            ChatMessage cm = (ChatMessage) m;
            ChatDrawing.drawChat(cm.getFrom() + ": " + cm.getText());
        } else if (m instanceof AnnounceMessage)
        {
            
        } else if (m instanceof AddMeMessage)
        {
            AddMeMessage amm = (AddMeMessage) m;
            me = (Player) amm.getEntity();
            //TODO: add this back in once joe commits his graphics
//            System.out.println("adding a weapon to me");
//            me.addWeapon(new Gun(100, 100, 10, 2, 6, 50));
            entityHandler.addEntity(me);
            dUpThread.start(); // start directional update thread
        } else if (m instanceof AddEntityMessage)
        {
            AddEntityMessage aem = (AddEntityMessage) m;
            entityHandler.addEntity(aem.getEntity());
        } else if (m instanceof EntityMovedMessage)
        {
            EntityMovedMessage emm = (EntityMovedMessage) m;
            Entity ent = entityHandler.getEntity(emm.getEntityID());
            ent.setLocation(emm.getNewLoc());
            ent.setDirectionMoving(emm.getNewDir());
            GameWindow.fireRepaintRequired();
        } else if (m instanceof FiredMessage)
        {
            FiredMessage fm = (FiredMessage) m;
            entityHandler.getEntity(fm.getID()).setFired(true);
            GameWindow.fireRepaintRequired();
        } else if (m instanceof RemoveEntityMessage)
        {
            RemoveEntityMessage rem = (RemoveEntityMessage) m;
            entityHandler.removeEntity(rem.getPlayer());
            GameWindow.fireRepaintRequired();
        }
    }

    public void tellSocketClosed()
    {
    }

    /**
     * @return Current Player
     */
    public Player getMe()
    {
        return me;
    }

    /**
     * Sets current player
     * 
     * @param me Player to set it to
     */
    public void setMe(Player me)
    {
        this.me = me;
    }

    /**
     * @return Current list of entities
     */
    public Iterable<Entity> getEntities()
    {
        return entityHandler.getEntities();
    }

    public void initWindow()
    {
        new GameWindow(map, this);
    }

    public void handle(String str)
    {
        String[] entStrs = str.split(";");
        for (int i = 0; i < entStrs.length; i++)
        {
            String[] entVals = entStrs[i].split(",");
            Entity e = entityHandler.getEntity(Long.valueOf(entVals[0]));
            if (e != null)
            {
                e.setX(Double.valueOf(entVals[1]));
                e.setY(Double.valueOf(entVals[2]));
                
                if (e == me)
                {
                    int x1 = me.getCenterX();
                    int y1 = me.getCenterY();
                    int angle = (int) Math.toDegrees(Math.atan2(lastMouseY - y1, lastMouseX - x1));

                    me.setDirectionFacing(angle);
                }
                else
                    e.setDirectionFacing(Integer.valueOf(entVals[3]));
                
//                System.out.println("Facing: " + entVals[3]);
            }
        }
//        mouseMoved(lastMouseX, lastMouseY); // update angle

        if (GameWindow.getInstance() != null)
        {
            // GameWindow is visible and running
            GameWindow.fireRepaintRequired();
        }

    }

    public void mouseScrolled(MouseWheelEvent e)
    {
        me.changeWeapon(e.getWheelRotation());
    }


    private class DirectionalUpdateThread extends Thread {

        private boolean keepRunning = true;

        /**
         * me must exist before this thread is started!
         */
        @Override
        public void run()
        {
            while (keepRunning)
            {
                try
                {
                    // System.out.println("sending");
                    rch.send(me.getDirectionFacing());
                    Thread.sleep(EntityConstants.DIR_UPDATE_TIME);
                } catch (Exception ex)
                {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    kill();
                }
            }
        }

        public void kill()
        {
            keepRunning = false;
        }

        public AbstractMap getMap()
        {
            return map;
        }
    }

    @Override
    public void focusGained(FocusEvent arg0)
    {
    }

    @Override
    public void focusLost(FocusEvent arg0)
    {
        depressedKeys.clear();
        me.setMoveSpeed(0);
    }

    public CommunicationHandler getCommunicationHandler()
    {
        return communicationHandler;
    }
}
