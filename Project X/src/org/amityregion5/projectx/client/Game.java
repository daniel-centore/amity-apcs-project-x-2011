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

import java.util.ArrayList;
import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.gui.ChatDrawing;
import org.amityregion5.projectx.client.gui.input.InputHandler;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.MePlayer;
import org.amityregion5.projectx.common.maps.AbstractMap;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class Game implements GameInputListener, MessageListener {
    
    private MePlayer me;
    private CommunicationHandler ch;
    private AbstractMap map;
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    public Game(CommunicationHandler ch, AbstractMap m)
    {
        this.ch = ch;
        InputHandler.registerListener(this);
    }


    public void mouseDragged(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(int x, int y)
    {
        int x1 = me.getX();
        int y1 = me.getY();
        int angle = (int) Math.atan2(y - y1, x - x1);
        me.setDirectionFacing(angle);
    }

    public void mousePressed(int x, int y, int button)
    {
        // TODO start firing
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(int x, int y, int button)
    {
        // TODO stop firing
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyPressed(int keyCode)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyReleased(int keyCode)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
        }
    }

    public void tellSocketClosed()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

}