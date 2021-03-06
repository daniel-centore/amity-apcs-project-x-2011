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
import java.awt.event.MouseEvent;
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
import org.amityregion5.projectx.client.gui.LobbyWindow;
import org.amityregion5.projectx.client.gui.PopupMenuHandler;
import org.amityregion5.projectx.client.gui.RepaintHandler;
import org.amityregion5.projectx.client.gui.ServerChooserWindow;
import org.amityregion5.projectx.client.gui.StatBarDrawing;
import org.amityregion5.projectx.client.gui.input.InputHandler;
import org.amityregion5.projectx.client.gui.input.Keys;
import org.amityregion5.projectx.client.sound.SoundManager;
import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.RawListener;
import org.amityregion5.projectx.common.communication.messages.*;
import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.EntityControllerThread;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.items.Upgradeable;
import org.amityregion5.projectx.common.entities.items.held.ProjectileWeapon;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.tools.TimeController;

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
    // private EntityHandler entityHandler; // current EntityHandler
    private EntityControllerThread controllerThread;
    private List<Integer> depressedKeys = new ArrayList<Integer>();
    private DirectionalUpdateThread dUpThread;
    private int lastMouseX; // last mouse coordinates, so we can update direction as moving
    private int lastMouseY;
    private String username; // current username
    private boolean gameOver = false;
    private boolean dead = false; // used for graceful exiting
    private int countdown; // used for wave countdowns
    private TimeController timeController;

    /**
     * Creates a game
     * @param ch CommunicationHandler for communications
     * @param m Current AbstractMap
     * @param username Current username
     */
    public Game(CommunicationHandler ch, AbstractMap m, String username)
    {
        RepaintHandler.reset();
        timeController = new TimeController();
        this.username = username;
        // entityHandler = new EntityHandler();
        controllerThread = new EntityControllerThread(m, false);
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

        // controllerThread.start();
    }

    public void mouseDragged(int x, int y)
    {
        mouseMoved(x, y);
    }

    public void mouseMoved(int x, int y)
    {
        if(gameOver)
            return;

        lastMouseX = x;
        lastMouseY = y;

        if(me == null || me.getImage() == null)
            return;

        if(me.getHitBox().contains(x, y))
            return;

        int cx = me.getCenterX();
        int cy = me.getCenterY();

        if((cx - x) * (cx - x) + (cy - y) * (cy - y) < 3600)
        {
            int angle = (int) Math.toDegrees(Math.atan2(y - cy, x - cx));
            me.setDirectionFacing(angle);
        }
        else
        {
            int x1;
            int y1;

            if(me.getCurrWeapon() instanceof ProjectileWeapon
                    && ((ProjectileWeapon) me.getCurrWeapon()).getWeaponTip() != null)
            {
                x1 = (int) ((ProjectileWeapon) me.getCurrWeapon()).getWeaponTip().getX();
                y1 = (int) ((ProjectileWeapon) me.getCurrWeapon()).getWeaponTip().getY();
            }
            else
            {
                x1 = cx;
                y1 = cy;
            }

            int angle = (int) Math.toDegrees(Math.atan2(y - y1, x - x1));
            me.setDirectionFacing(angle);
        }

        GameWindow.fireRepaintRequired();
    }

    public void mousePressed(int x, int y, int button)
    {
        if(gameOver)
        {
            new Thread() {

                @Override
                public void run()
                {
                    Game.this.destroy();

                    StatBarDrawing.reset();
                    ChatDrawing.reset();
                    GameWindow.closeWindow();

                    boolean joined = false;
                    while(!joined)
                    {
                        Message reply = communicationHandler.requestReply(new IntroduceMessage(username));
                        // ActivePlayerUpdate message serves as an affirmative here.
                        if(reply instanceof ActivePlayersMessage)
                        {
                            ActivePlayersMessage apm = (ActivePlayersMessage) reply;
                            joined = true;
                            new LobbyWindow(communicationHandler, apm.getPlayers(), username);
                        }
                        else if(reply instanceof DisconnectRequestMessage)
                        {
                            JOptionPane.showMessageDialog(null, ((DisconnectRequestMessage) reply).getReason(),
                                    "Disconnected", JOptionPane.ERROR_MESSAGE);
                            joined = true; // get us out of the loop
                        }
                        else if(reply instanceof Message)
                        {
                            // generic message means error
                            JOptionPane.showMessageDialog(null, "I/O error. Check logs for details.",
                                    "Disconnected", JOptionPane.ERROR_MESSAGE);
                            joined = true;
                        }
                    }
                    InputHandler.removeListener(Game.this);
                }
            }.start();
        }
        else if(button == MouseEvent.BUTTON1)
        {
            getCommunicationHandler().send(new FiringMessage(true));
        }
    }

    public void mouseReleased(int x, int y, int button)
    {
        if(gameOver)
            return;

        if(button == MouseEvent.BUTTON1)
            getCommunicationHandler().send(new FiringMessage(false));
    }

    public void keyPressed(KeyEvent e)
    {
        if(gameOver)
            return; // disable key input after game ends

        int keyCode = e.getKeyCode();

        if(ChatDrawing.isChatting() && !(e.isActionKey() || e.getKeyCode() == KeyEvent.VK_SHIFT
                || e.getKeyCode() == KeyEvent.VK_ALT || e.getKeyCode() == KeyEvent.VK_ENTER
                || e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_CONTROL
                || e.getKeyCode() == KeyEvent.VK_ESCAPE))
        {
            ChatDrawing.addLetter(e.getKeyChar());
        }
        else if(ChatDrawing.isChatting())
        {
            if(keyCode == KeyEvent.VK_ENTER)
            {

                String s = ChatDrawing.getTextChat().trim();
                if(s.length() <= 0)
                {
                    ChatDrawing.clearChat();
                    return;
                }
                ChatMessage cm = new ChatMessage(ChatDrawing.getTextChat(), username);
                ChatDrawing.clearChat();
                getCommunicationHandler().send(cm);
            }
            else if(keyCode == KeyEvent.VK_BACK_SPACE)
            {
                ChatDrawing.backspace();
            }
            else if(keyCode == KeyEvent.VK_ESCAPE)
            {
                ChatDrawing.clearChat();
            }
        }
        else
        // not chatting
        {
            if(Keys.isKey(Keys.CHAT, keyCode))
            {
                ChatDrawing.setChatting(true);
                return;
            }
            else if(Keys.isKey(Keys.GRID, keyCode))
            {
                RepaintHandler.switchShowingGrid();
                return;
            }
            else if(Keys.isKey(Keys.BLOCK, keyCode))
            {
                Point p = PopupMenuHandler.roundToGrid(lastMouseX, lastMouseY);

                communicationHandler.send(new RequestEntityAddMessage(EntityConstants.BLOCK, p.x, p.y));
            }
            else if(Keys.isKey(Keys.FENCE, keyCode))
            {
                Point p = PopupMenuHandler.roundToGrid(lastMouseX, lastMouseY);

                communicationHandler.send(new RequestEntityAddMessage(EntityConstants.FENCE, p.x, p.y));
            }
            else if(Keys.isKey(Keys.WALL, keyCode))
            {
                Point p = PopupMenuHandler.roundToGrid(lastMouseX, lastMouseY);

                communicationHandler.send(new RequestEntityAddMessage(EntityConstants.WALL, p.x, p.y));
            }
            else if(Keys.isKey(Keys.FIRE, keyCode))
            {
                getCommunicationHandler().send(new FiringMessage(true));
            }
            else if(Keys.isKey(Keys.HEAL, keyCode))
            {
                communicationHandler.send(new RequestHealMessage());
            }
            else if(Keys.isKey(Keys.CHANGE_WEAPON_1, keyCode))
            {
                communicationHandler.send(new ChangedWeaponMessage(-1));
            }
            else if(Keys.isKey(Keys.CHANGE_WEAPON_2, keyCode))
            {
                communicationHandler.send(new ChangedWeaponMessage(1));
            }
            else if(Keys.isKey(Keys.UPGRADE_WEAPON, keyCode))
            {
                communicationHandler.send(new RequestUpgradeMessage(me.getCurrWeapon().getUniqueID()));
                return;
            }
            else if(Keys.isKey(Keys.LEADERBOARD, keyCode))
            {
                RepaintHandler.switchLeaderBoard();
                return;
            }
            else if(Keys.isKey(Keys.RELOAD, keyCode))
            {
                communicationHandler.send(new ReloadMessage());
            }
            else if(Keys.isKey(Keys.BUY_AMMO, keyCode))
            {
                communicationHandler.send(new BuyAmmoMessage());
            }
            else if(Keys.isKey(Keys.SONG_1, keyCode))
            {
                SoundManager.play(SoundManager.SONG_1);
            }
            else if(Keys.isKey(Keys.SONG_2, keyCode))
            {
                SoundManager.play(SoundManager.SONG_2);
            }
            else if(Keys.isKey(Keys.SONG_3, keyCode))
            {
                SoundManager.play(SoundManager.SONG_3);
            }
            else if(Keys.isKey(Keys.SONG_4, keyCode))
            {
                SoundManager.play(SoundManager.SONG_4);
            }
            else if(Keys.isKey(Keys.STOP, keyCode))
            {
                SoundManager.stopMusic();
            }
            else if(Keys.isKey(Keys.BUY_SIGHT, keyCode))
            {
                communicationHandler.send(new SightMessage());
            }
            if(me == null)
            {
                return;
            }
            if(!depressedKeys.contains(keyCode))
            {
                depressedKeys.add(keyCode);
            }
            else
            // key already pressed, don't need to do anything!
            {
                return;
            }
            int deg = calcMeDeg();
            if(deg == Integer.MIN_VALUE)
            {
                return;
            }
            me.setMoveSpeed(PlayerEntity.INITIAL_SPEED);
            me.setDirectionMoving(deg);
            ClientMovingMessage c = new ClientMovingMessage(PlayerEntity.INITIAL_SPEED, deg);
            getCommunicationHandler().send(c);
        }
    }

    // /**
    // * @return The Entity Model
    // */
    // public EntityHandler getEntityHandler()
    // {
    // return entityHandler;
    // }
    private int calcMeDeg() // calculates the direction the entity should be moving
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

        if(lr == 0 && ud == 0)
            deg = Integer.MIN_VALUE;

        return deg;
    }

    public void keyReleased(int keyCode)
    {
        if(gameOver)
            return; // disable key input after game is over

        int speed = PlayerEntity.INITIAL_SPEED;

        while(depressedKeys.contains(keyCode))
        {
            depressedKeys.remove((Integer) keyCode);
        }
        if(depressedKeys.isEmpty()) // stop moving
        {
            speed = 0;
        }
        int deg = calcMeDeg();
        if(deg == Integer.MIN_VALUE)
        {
            speed = 0;
            if(me != null)
                deg = me.getDirectionMoving();
        }
        ClientMovingMessage c = new ClientMovingMessage(speed, deg);
        getCommunicationHandler().send(c);

        if(Keys.isKey(Keys.FIRE, keyCode))
        {
            getCommunicationHandler().send(new FiringMessage(false));
        }
    }

    /**
     * @return Current map
     */
    public AbstractMap getMap()
    {
        return map;
    }

    @Override
    public void handle(final Message m)
    {
        if(m instanceof AnnounceMessage)
        {
            AnnounceMessage am = (AnnounceMessage) m;
            ChatDrawing.drawChat(am.getText());
        }
        else if(m instanceof ChatMessage)
        {
            ChatMessage cm = (ChatMessage) m;
            ChatDrawing.drawChat(cm.getFrom() + ": " + cm.getText());
        }
        else if(m instanceof AddMeMessage)
        {
            AddMeMessage amm = (AddMeMessage) m;
            me = (PlayerEntity) amm.getEntity();
            me.updateWeaponImages();
            // entityHandler.addEntity(me);
            controllerThread.addEntity(me);

            dUpThread.start(); // start directional update thread
        }
        else if(m instanceof AddEntityMessage)
        {
            new Thread() {

                @Override
                public void run()
                {
                    AddEntityMessage aem = (AddEntityMessage) m;
                    Entity e = aem.getEntity();
                    if(e instanceof CharacterEntity)
                        ((CharacterEntity) e).updateWeaponImages();

                    // entityHandler.addEntity(e);
                    controllerThread.addEntity(e);
                }
            }.start();
            
        }
        else if(m instanceof AddWeaponMessage)
        {
            AddWeaponMessage awm = (AddWeaponMessage) m;
            // awm.getWeapon().setImage(ImageHandler.loadImage(awm.getWeapon().getDefaultImage()));
            try
            {
                // ((CharacterEntity) entityHandler.getEntity(awm.getID())).addWeapon(awm.getWeapon());
                ((CharacterEntity) controllerThread.getEntity(awm.getID())).addWeapon(awm.getWeapon());
                me.updateWeaponImages();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                // what's the usual error?
            }
        }
        else if(m instanceof StatusUpdateMessage)
        {
            StatusUpdateMessage sum = (StatusUpdateMessage) m;
            if(sum.getType() == StatusUpdateMessage.Type.END_GAME)
            {
                controllerThread.kill();
                gameOver = true;
                communicationHandler.send(new GoodbyeMessage(username));
                JOptionPane.showMessageDialog(GameWindow.getInstance(), "The enemies have taken over!", "Game Over", JOptionPane.OK_OPTION);
                rch.kill();
                communicationHandler.removeListener(this);
                RepaintHandler.endGame();
                GameWindow.fireRepaintRequired();
            }
        }
        else if(m instanceof CashMessage)
        {
            CashMessage cm = (CashMessage) m;
            // ((PlayerEntity) entityHandler.getEntity(cm.getID())).setCash(cm.getAmount());
            ((PlayerEntity) controllerThread.getEntity(cm.getID())).setCash(cm.getAmount());
        }
        else if(m instanceof PointMessage)
        {
            PointMessage pm = (PointMessage) m;
            // ((PlayerEntity) entityHandler.getEntity(pm.getID())).setPoints(pm.getAmount());
            if(controllerThread.getEntity(pm.getID()) != null)
                ((PlayerEntity) controllerThread.getEntity(pm.getID())).setPoints(pm.getAmount());
        }
        else if(m instanceof WeaponUpgradedMessage)
        {
            if(me.getCurrWeapon().equals(((WeaponUpgradedMessage) m).getID()))
                ((Upgradeable) me.getCurrWeapon()).upgrade();
        }
        else if(m instanceof UpdateWeaponMessage)
        {
            int wep = ((UpdateWeaponMessage) m).getWeapon();

            // ((CharacterEntity) entityHandler.getEntity(((UpdateWeaponMessage) m).getPlayerID())).setCurrWeapon(wep);
            ((CharacterEntity) controllerThread.getEntity(((UpdateWeaponMessage) m).getPlayerID())).setCurrWeapon(wep);
        }
        else if(m instanceof WaveMessage)
        {
            StatBarDrawing.setWaveNumber(((WaveMessage) m).getNumber());
            // this.startCountdown(((WaveMessage) m).getDelayMillis() / 1000);
        }
        else if(m instanceof DisconnectRequestMessage)
        {
            JOptionPane.showMessageDialog(null, ((DisconnectRequestMessage) m).getReason(), "Disconnected", JOptionPane.OK_OPTION);
            dead = true;
            this.destroy();
            new ServerChooserWindow();
        }
        else if(m instanceof AmmoUpdateMessage)
        {
            AmmoUpdateMessage aum = (AmmoUpdateMessage) m;
            ((PlayerEntity) controllerThread.getEntity(aum.getID())).getWeapon(aum.getWepID()).setAmmo(aum.getAmmo());
        }
        else if(m instanceof ReloadMessage)
        {
            if(me.getCurrWeapon() instanceof ProjectileWeapon)
                ((ProjectileWeapon) me.getCurrWeapon()).reload();
        }
        else if(m instanceof ReloadingMessage)
        {
            me.setReloading(true);
            new Thread() {

                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(((ReloadingMessage) m).getMs());
                    }
                    catch(InterruptedException ex)
                    {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    finally
                    {
                        me.setReloading(false);
                    }
                }
            }.start();
        }
        else if(m instanceof SightMessage)
        {
            ((ProjectileWeapon) me.getCurrWeapon()).getSight();
        }
        else
        {
            System.err.println("Unknown message type encountered. " + "Please make sure you have the latest game version!");
        }
    }

    public boolean isPlayerReloading()
    {
        return me.isReloading();
    }

    public void tellSocketClosed()
    {
        if(!dead)
        {
            JOptionPane.showMessageDialog(null, "Server has closed. You have been disconnected", "Disconnected", JOptionPane.OK_OPTION);
            this.destroy();
            new ServerChooserWindow();
        }
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
        // return entityHandler.getEntities();
        return controllerThread.getEntities();
    }

    public void initWindow()
    {
        new GameWindow(map, this);
    }

    @Override
    public void handle(char prefix, String str)
    {
        try
        {
            if(prefix == Constants.FIRE_PREF)
            {
                // PlayerEntity p = (PlayerEntity) entityHandler.getEntity(Long.valueOf(str));
                PlayerEntity p = (PlayerEntity) controllerThread.getEntity(Long.valueOf(str));
                ((ProjectileWeapon) p.getCurrWeapon()).fire();
                if(!p.getFired())
                {
                    p.setFired(true);
                }
                GameWindow.fireRepaintRequired();
                return;
            }

            if(prefix == Constants.DIED_PREF)
            {
                String[] s = str.split(",");

                for(String k : s)
                    controllerThread.reallyRemoveEntity(Long.valueOf(k));
                // entityHandler.removeEntity(Long.valueOf(k));
            }

            if(prefix != Constants.MOVE_PREF)
                return;

            // System.out.println(str);
            String[] entStrs = str.split(";");
            //        long time = Long.valueOf(entStrs[0]);

            for(int i = 1;i < entStrs.length;i++)
            {
                String[] entVals = entStrs[i].split(",");
                long id = Long.valueOf(entVals[0]);

                if(id == -1) // the base
                {
                    map.getArea().setHp(Integer.valueOf(entVals[1]));
                }
                else
                {
                    Entity e = controllerThread.getEntity(id);
                    if(e == null)
                    {
                        System.err.println("NULL ENTITY ID: [" + id + "]!");
                        break;
                    }

                    int x = Integer.valueOf(entVals[1]);
                    int y = Integer.valueOf(entVals[2]);
                    int facing = Integer.valueOf(entVals[3]);
                    int moving = Integer.valueOf(entVals[4]);
                    int hp = Integer.valueOf(entVals[5]);
                    double speed = Double.valueOf(entVals[6]);

                    e.setX(x);
                    e.setY(y);

                    if(hp > Byte.MIN_VALUE)
                    {
                        ((Damageable) e).setHp(hp);
                    }

                    if(e != me)
                        e.setDirectionFacing(facing);

                    e.setDirectionMoving(moving);
                    e.setMoveSpeed(speed);
                }
            }

            if(GameWindow.getInstance() != null)
            {
                GameWindow.fireRepaintRequired();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            // but keep going!
        }

    }

    public void mouseScrolled(MouseWheelEvent e)
    {
        communicationHandler.send(new ChangedWeaponMessage(e.getWheelRotation()));
    }

    /**
     * Destroys the game.
     */
    private void destroy()
    {
        rch.removeRawListener(this);
        communicationHandler.removeListener(this);
        dUpThread.kill();
        rch.kill();
        rch = null;
        GameWindow.closeWindow();
    }

    /**
     * (Rudely) kills the game.
     */
    private void kill()
    {
        destroy();
        communicationHandler.kill();
    }

    /**
     * @return All the players in the game
     */
    public ArrayList<PlayerEntity> getPlayers()
    {
        ArrayList<PlayerEntity> toReturn = new ArrayList<PlayerEntity>();
        // for (Entity e : entityHandler.getEntities())
        for(Entity e : controllerThread.getEntities())
        {
            if(e instanceof PlayerEntity)
            {
                toReturn.add((PlayerEntity) e);
            }
        }
        return toReturn;
    }

    private void startCountdown(int i)
    {
        countdown = i;
        new Countdown(i).start();
    }

    public int getCountdown()
    {
        return countdown;
    }

    private class DirectionalUpdateThread extends Thread {

        private boolean keepRunning = true;

        /**
         * me must exist before this thread is started!
         */
        @Override
        public void run()
        {
            while(keepRunning)
            {
                try
                {
                    rch.send(me.getDirectionFacing());
                    Thread.sleep(EntityConstants.DIR_UPDATE_TIME);
                }
                catch(Exception ex)
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
        if(me != null)
            me.setMoveSpeed(0);
    }

    /**
     * @return Current {@link CommunicationHandler}
     */
    public CommunicationHandler getCommunicationHandler()
    {
        return communicationHandler;
    }

    private class Countdown extends Thread {

        private int from;

        public Countdown(int i)
        {
            from = i;
        }

        @Override
        public void run()
        {
            for(countdown = from;countdown >= 0;countdown--)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
