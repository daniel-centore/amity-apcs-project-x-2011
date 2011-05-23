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

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.gui.ChatDrawing;
import org.amityregion5.projectx.client.gui.input.InputHandler;
import org.amityregion5.projectx.client.gui.input.Keys;
import org.amityregion5.projectx.client.handlers.EntityHandler;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.AddMeMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.ClientMovedMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Class documentation.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class Game implements GameInputListener, MessageListener {

    private CommunicationHandler ch; // current CommunicationHandler
    private AbstractMap map; // current AbstractMap
    private Player me; // current Player (null at initialization!)
    private EntityHandler entityHandler; // current EntityHandler

    public Game(CommunicationHandler ch, AbstractMap m)
    {
        entityHandler = new EntityHandler();
        this.ch = ch;
        me = null;

        ch.registerListener(this);
        InputHandler.registerListener(this);
    }

    public void mouseDragged(int x, int y)
    {
    }

    public void mouseMoved(int x, int y)
    {
        // send to server and let server deal with it
        if (me == null)
        {
            return;
        }
        int x1 = me.getX();
        int y1 = me.getY();
        int angle = (int) Math.atan2(y - y1, x - x1);
        me.setDirectionFacing(angle);
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
        if (me == null)
        {
            return;
        }
        ClientMovedMessage c = null;
        double k = me.getMoveSpeed();

        if (keyCode == Keys.UP)
        {
            c = new ClientMovedMessage(0, -k);
        } else if (keyCode == Keys.DOWN)
        {
            c = new ClientMovedMessage(0, k);
        } else if (keyCode == Keys.LEFT)
        {
            c = new ClientMovedMessage(-k, 0);
        } else if (keyCode == Keys.RIGHT)
        {
            c = new ClientMovedMessage(k, 0);
        }
        ch.send(c);
    }

    public void keyReleased(int keyCode)
    {
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
}
