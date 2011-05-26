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

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.ClientMovingMessage;
import org.amityregion5.projectx.common.communication.messages.EntityMovedMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.server.Server;

/**
 * Class documentation.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 * @author Mike DiBuduo
 */
public class Game implements GameInputListener, MessageListener, RawListener
{

    private CommunicationHandler ch; // current CommunicationHandler
    private RawCommunicationHandler rch; // current raw communications handler
    private AbstractMap map; // current AbstractMap
    private Player me; // current Player (null at initialization!)
    private EntityHandler entityHandler; // current EntityHandler
    private List<Integer> depressedKeys = new ArrayList<Integer>();
    private boolean isChatting = false;

    public Game(CommunicationHandler ch, AbstractMap m)
    {
        entityHandler = new EntityHandler();
        this.ch = ch;
        me = null;
        map = m;
        createPlaySpawns(m);
        createEnemySpawns(m);
        rch = new RawCommunicationHandler(ch.getServerIP());

        rch.registerRawListener(this);
        rch.start();
        ch.registerListener(this);
        InputHandler.registerListener(this);
        RepaintHandler.setGame(this);
    }

    public void mouseDragged(int x, int y)
    {
        // TODO mouse dragged needs to turn the player just like
        // mouseMoved did
    }

    public void mouseMoved(int x, int y)
    {
        // send to server and let server deal with it?
        if (me == null)
        {
            return;
        }
        int x1 = me.getCenterX();
        int y1 = me.getCenterY();
        int angle = (int) Math.toDegrees(Math.atan2(y - y1, x - x1)) + 90;

        me.setDirectionFacing(angle);
        rch.send(angle);
        GameWindow.fireRepaintRequired();
    }

    public void mousePressed(int x, int y, int button)
    {
        // TODO start firing
    }

    public void mouseReleased(int x, int y, int button)
    {
        // TODO stop firing
    }

    public void keyPressed(int keyCode)
    {
        //System.out.println("key " + keyCode + " pressed");
        if (!isChatting)
        {
            if (me == null)
            {
                return;
            }

            if (keyCode == Keys.CHAT && !isChatting)
            {
                isChatting = true;
                return;
            } else if (!depressedKeys.contains(keyCode))
            {
                depressedKeys.add(keyCode);
            } else
            // key already pressed, don't need to do anything!
            {
                return;
            }
            int deg = calcMeDeg();
            ClientMovingMessage c = new ClientMovingMessage(Player.INITIAL_SPEED, deg);
            ch.send(c);
        } else
        {
            
            if (keyCode == KeyEvent.VK_ENTER)
            {
                ChatDrawing.drawChat(ChatDrawing.getTextChat());
                ChatDrawing.clearChat();
            }
            else
            {
                //handled in other keyPressed method
            }
            
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        if (isChatting && !e.isActionKey())
        {
            ChatDrawing.addLetter(e.getKeyChar());       
        }
    }

    private int calcMeDeg()
    {
        int numPressed = depressedKeys.size();
        if (numPressed == 0)
        {
            return 0;
        }
        // TODO send diagonal directions!
        // deg is measured clockwise from x-axis. don't ask me why. -joe
        // keys currently override each other. we need to add the
        // y-resultant to the x-resultant
        int deg = 0;
        if (depressedKeys.contains(Keys.LEFT))
        {
            deg = 180;
        }
        if (depressedKeys.contains(Keys.DOWN))
        {
            deg = 90;
        }
        if (depressedKeys.contains(Keys.UP))
        {
            deg = -90;
        }
        return deg;
    }

    public void keyReleased(int keyCode)
    {
        System.out.println("key " + keyCode + " released");
        int speed = Player.INITIAL_SPEED;
        if (depressedKeys.contains(keyCode))
        {
            depressedKeys.remove((Integer) keyCode);
        }
        if (depressedKeys.isEmpty()) // stop moving
        {
            speed = 0;
        }
        int deg = calcMeDeg();
        ClientMovingMessage c = new ClientMovingMessage(speed, deg);
        ch.send(c);
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
        if (m instanceof ChatMessage)
        {
            ChatMessage cm = (ChatMessage) m;
            ChatDrawing.drawChat(cm.getFrom() + ": " + cm.getText());
        } else if (m instanceof AddMeMessage)
        {
            AddMeMessage amm = (AddMeMessage) m;
            me = (Player) amm.getEntity();
            entityHandler.addEntity(me);
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
        new GameWindow(map);
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
                e.setDirectionFacing(Integer.valueOf(entVals[3]));
            }
        }
        if (GameWindow.createImage() != null)
        {
            // GameWindow is visible and running
            GameWindow.fireRepaintRequired();
        }
        
    }

    /**
     * Creates random Points within play area for players to spawn at
     * @param map Current map
     * @return An ArrayList of random Point objects within the map's play area where players spawn
     */
    public void createPlaySpawns(AbstractMap map)
    {
        ArrayList<Point> spawns = new ArrayList<Point>();
        for(int i = 0; i < Server.MAX_PLAYERS; i++) //Would be better if we accessed the exact number of players instead
        {
            int x = (int)map.getPlayArea().getX() + (int)(Math.random() * map.getPlayArea().getWidth());
            int y = (int)map.getPlayArea().getY() + (int)(Math.random() * map.getPlayArea().getHeight());
            spawns.add(new Point(x,y));
        }
        map.setPlaySpawns(spawns);
    }

    /**
     * Makes enemies spawn at the edge of the game window
     * @return ArrayList of Points where enemies spawn at the edge of the game window
     */
    public void createEnemySpawns(AbstractMap map)
    {
        ArrayList<Point> enemySpawns = new ArrayList<Point>();
        for(int i = 0; i < GameWindow.GAME_HEIGHT; i+= 5)
        {
            enemySpawns.add(new Point(0,i));
            enemySpawns.add(new Point(GameWindow.GAME_WIDTH,i));
        }
        for(int i = 0; i < GameWindow.GAME_WIDTH; i+= 5)
        {
            enemySpawns.add(new Point(i,0));
            enemySpawns.add(new Point(i, GameWindow.GAME_HEIGHT));
        }
        map.setEnemySpawns(enemySpawns);

    }
}
