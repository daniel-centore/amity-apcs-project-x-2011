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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.communication.RawCommunicationHandler;
import org.amityregion5.projectx.client.gui.ChatDrawing;
import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.client.gui.PopupMenuHandler;
import org.amityregion5.projectx.client.gui.RepaintHandler;
import org.amityregion5.projectx.client.gui.ServerChooserWindow;
import org.amityregion5.projectx.client.gui.input.InputHandler;
import org.amityregion5.projectx.client.gui.input.Keys;
import org.amityregion5.projectx.client.handlers.EntityHandler;
import org.amityregion5.projectx.client.sound.SoundManager;
import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.RawListener;
import org.amityregion5.projectx.common.communication.messages.*;
import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.items.field.Block;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * The umbrella logistics class for the client-side game.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 * @author Mike DiBuduo
 * @author Mike Wenke
 * @author Cam Simpson
 */
public class Game implements GameInputListener, MessageListener, RawListener, FocusListener {

    private CommunicationHandler communicationHandler; // current CommunicationHandler
    private RawCommunicationHandler rch; // current raw communications handler
    private AbstractMap map; // current AbstractMap
    private PlayerEntity me; // current Player (null at initialization!)
    private EntityHandler entityHandler; // current EntityHandler
    private List<Integer> depressedKeys = new ArrayList<Integer>();
    private DirectionalUpdateThread dUpThread;
    private int lastMouseX; // last mouse coordinates, so we can update direction as moving
    private int lastMouseY;
    private String username; // current username

    /**
     * Creates a game
     * @param ch CommunicationHandler for communications
     * @param m Current AbstractMap
     * @param username Current username
     */
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

        // if (SoundManager.BACKGROUND)
        // SoundManager.playLoop(Sound.BG_1);
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
        if (button == 1)
            getCommunicationHandler().send(new FiringMessage(true));
    }

    public void mouseReleased(int x, int y, int button)
    {
        if (button == 1)
            getCommunicationHandler().send(new FiringMessage(false));
    }

    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (Keys.isKey(Keys.LEADERBOARD, keyCode))
        {
            RepaintHandler.switchLeaderBoard();
            return;
        }
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
            } else if (Keys.isKey(Keys.GRID, keyCode))
            {
                RepaintHandler.switchShowingGrid();
                return;
            } else if (Keys.isKey(Keys.BLOCK, keyCode))
            {
                if (me.getCash() > Block.PRICE)
                {
                    Point p = PopupMenuHandler.roundToGrid(lastMouseX, lastMouseY);
                    communicationHandler.send(new RequestEntityAddMessage(EntityConstants.BLOCK, p.x, p.y));
                    me.spendCash(Block.PRICE);
                }
            } else if (Keys.isKey(Keys.FIRE, keyCode))
            {
                getCommunicationHandler().send(new FiringMessage(true));
            } else if (Keys.isKey(Keys.CHANGE_WEAPON_1, keyCode))
            {
                communicationHandler.send(new ChangedWeaponMessage(-1));
            } else if (Keys.isKey(Keys.CHANGE_WEAPON_2, keyCode))
            {
                communicationHandler.send(new ChangedWeaponMessage(1));
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
            ClientMovingMessage c = new ClientMovingMessage(PlayerEntity.INITIAL_SPEED, deg);
            getCommunicationHandler().send(c);
        }
    }

    /**
     * @return The Entity Model
     */
    public EntityHandler getEntityHandler()
    {
        return entityHandler;
    }

    private int calcMeDeg()
    {
        int deg = Integer.MIN_VALUE;

        int left = Keys.numIsKey(Keys.LEFT, depressedKeys);
        int right = Keys.numIsKey(Keys.RIGHT, depressedKeys);
        int up = Keys.numIsKey(Keys.DOWN, depressedKeys);
        int down = Keys.numIsKey(Keys.UP, depressedKeys);

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
        int speed = PlayerEntity.INITIAL_SPEED;

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

        if (Keys.isKey(Keys.FIRE, keyCode))
        {
            getCommunicationHandler().send(new FiringMessage(false));
        }
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
        } else if (m instanceof AddMeMessage)
        {
            AddMeMessage amm = (AddMeMessage) m;
            me = (PlayerEntity) amm.getEntity();
            me.updateWeaponImages();
            entityHandler.addEntity(me);
            dUpThread.start(); // start directional update thread
        } else if (m instanceof AddEntityMessage)
        {
            AddEntityMessage aem = (AddEntityMessage) m;
            Entity e = aem.getEntity();
            if (e instanceof CharacterEntity)
                ((CharacterEntity) e).updateWeaponImages();

            entityHandler.addEntity(e);
        } else if (m instanceof EntityMovedMessage)
        {
            EntityMovedMessage emm = (EntityMovedMessage) m;
            Entity ent = entityHandler.getEntity(emm.getEntityID());
            ent.setLocation(emm.getNewLoc());
            ent.setDirectionMoving(emm.getNewDir());
            GameWindow.fireRepaintRequired();
        } /*else if (m instanceof FiredMessage)
          {
          FiredMessage fm = (FiredMessage) m;
          CharacterEntity e = (CharacterEntity) entityHandler.getEntity(fm.getID());

          e.setFired(true);
          GameWindow.fireRepaintRequired();
          } */else if (m instanceof RemoveEntityMessage)
        {
            RemoveEntityMessage rem = (RemoveEntityMessage) m;
            entityHandler.removeEntity(rem.getPlayer());
            GameWindow.fireRepaintRequired();
        } else if (m instanceof AddWeaponMessage)
        {
            AddWeaponMessage awm = (AddWeaponMessage) m;
            awm.getWeapon().setImage(ImageHandler.loadImage(awm.getWeapon().getDefaultImage()));
            try
            {
                ((CharacterEntity) entityHandler.getEntity(awm.getID())).addWeapon(awm.getWeapon());
                me.updateWeaponImages();
            } catch (Exception e)
            {
                // FIXME: temporary kludge
                // what's the usual error?
            }
        } else if (m instanceof StatusUpdateMessage)
        {
            StatusUpdateMessage sum = (StatusUpdateMessage) m;
            if (sum.getType() == StatusUpdateMessage.Type.END_GAME)
            {
                JOptionPane.showMessageDialog(null, "The enemies have taken over!", "Game Over", JOptionPane.OK_OPTION);
                InputHandler.removeListener(this);
            }
        } else if (m instanceof CashMessage)
        {
            CashMessage cm = (CashMessage) m;
            ((PlayerEntity) entityHandler.getEntity(cm.getID())).setCash(cm.getAmount());
        } else if (m instanceof PointMessage)
        {
            PointMessage pm = (PointMessage) m;
            ((PlayerEntity) entityHandler.getEntity(pm.getID())).setPoints(pm.getAmount());
        } else if (m instanceof PlaySoundMessage)
        {
            // SoundManager.playOnce(((PlaySoundMessage) m).getSound());
        } else if (m instanceof UpdateWeaponMessage)
        {
            int wep = ((UpdateWeaponMessage) m).getWeapon();
            
            ((CharacterEntity) entityHandler.getEntity(((UpdateWeaponMessage) m).getPlayerID())).setCurrWeapon(wep);
//            me.changeWeapon(wep);
        }
    }

    public void tellSocketClosed()
    {
        JOptionPane.showMessageDialog(null, "Server has closed. You have been disconnected", "Disconnected", JOptionPane.OK_OPTION);
        this.destroy();
        new ServerChooserWindow();
    }

    /**
     * @return Current Player
     */
    public PlayerEntity getMe()
    {
        return me;
    }

    /**
     * Sets current player
     * 
     * @param me Player to set it to
     */
    public void setMe(PlayerEntity me)
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
        if (str.startsWith(Constants.FIRE_PREF))
        {
            ((PlayerEntity) entityHandler.getEntity(Long.valueOf(str.substring(1)))).setFired(true);
            // SoundManager.playOnce(me.getCurrWeapon().getSound());
            GameWindow.fireRepaintRequired();
            return;
        }
        String[] entStrs = str.split(";");
        for (int i = 0; i < entStrs.length; i++)
        {
            String[] entVals = entStrs[i].split(",");
            long id = Long.valueOf(entVals[0]);
            if (id == -1)
            {
                // affects the base
                map.getArea().setHp(Integer.valueOf(entVals[1]));
            } else
            {
                Entity e = entityHandler.getEntity(Long.valueOf(entVals[0]));
                if (e != null)
                {
                    e.setX(Double.valueOf(entVals[1]));
                    e.setY(Double.valueOf(entVals[2]));
                    int hp = Integer.valueOf(entVals[4]);
                    if (hp > Byte.MIN_VALUE)
                    {
                        ((Damageable) e).setHp(hp);
                    }

                    if (e == me)
                    {
                        int x1 = me.getCenterX();
                        int y1 = me.getCenterY();
                        int angle = (int) Math.toDegrees(Math.atan2(lastMouseY - y1, lastMouseX - x1));

                        me.setDirectionFacing(angle);
                    } else
                        e.setDirectionFacing(Integer.valueOf(entVals[3]));
                }
            }
        }

        if (GameWindow.getInstance() != null)
        {
            GameWindow.fireRepaintRequired();
        }

    }

    // TODO: There needs to be an alternate way to change weapons, incase we have no scroll wheel!
    public void mouseScrolled(MouseWheelEvent e)
    {
        communicationHandler.send(new ChangedWeaponMessage(e.getWheelRotation()));
        // me.changeWeapon(e.getWheelRotation());
    }

    /**
     * Destroys the game.
     */
    private void destroy()
    {
        dUpThread.kill();
        communicationHandler.kill();
        rch.kill();
        GameWindow.closeWindow();
    }

    public ArrayList<PlayerEntity> getPlayers()
    {
        ArrayList<PlayerEntity> toReturn = new ArrayList<PlayerEntity>();
        for (Entity e : entityHandler.getEntities())
        {
            if (e instanceof PlayerEntity)
            {
                toReturn.add((PlayerEntity) e);
            }
        }
        return toReturn;
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
    }

    @Override
    public void focusGained(FocusEvent arg0)
    {
    }

    @Override
    public void focusLost(FocusEvent arg0)
    {
        depressedKeys.clear();
        if (me != null)
            me.setMoveSpeed(0);
    }

    public CommunicationHandler getCommunicationHandler()
    {
        return communicationHandler;
    }
}
