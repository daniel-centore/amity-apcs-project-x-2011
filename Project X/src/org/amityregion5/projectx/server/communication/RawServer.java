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
package org.amityregion5.projectx.server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.game.GameController;

/**
 * The RawServer which looks for new RawClients. Muahahah!
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class RawServer extends Thread {

    private ArrayList<RawClient> rawClients; // THe list of clients
    private boolean keepRunning = true; // Should we keep looking?
    private ServerSocket rawSock; // Socket to search upon
    private Server server; // the main server
    private GameController gameController;

    /**
     * Creates a RawServer
     * @param port Port to look on
     * @param serv Regular server
     */
    public RawServer(int port, Server serv)
    {
        rawClients = new ArrayList<RawClient>();
        server = serv;
        try
        {
            rawSock = new ServerSocket(port);
        } catch (IOException ex)
        {
            Logger.getLogger(RawServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run()
    {
        while (keepRunning)
        {
            try
            {
                Socket s = rawSock.accept();
                RawClient rc = new RawClient(s, server);
                rc.start();
                rawClients.add(rc);
            } catch (IOException ex)
            {
                Logger.getLogger(RawServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Stops looking for connections
     */
    public void kill()
    {
        keepRunning = false;
    }

    /**
     * Writes a string to the raw output. String format should be: "entityUniqueId,x,y,dir". *Please* keep x, y, and dir to int values.
     * 
     * @param s the CSV string to send
     */
    public void send(String s)
    {
        s.trim();

        if (rawClients.isEmpty())
            System.err.println("No raw clients!");

        for (RawClient out : rawClients)
        {
            out.send(s + "\n");
        }
    }

    public void send(char start, String s)
    {
        send(start + s);
    }

    private void sendAggregateEntityUpdateMessage()
    {
        Iterator<Entity> itr = gameController.getEntities().getRemovalIterator();

        StringBuilder died = new StringBuilder();
        died.append(Constants.DIED_PREF);
        while (itr.hasNext())
        {
            Entity e = itr.next();
            died.append(e.getUniqueID());
            died.append(",");
            itr.remove();
            gameController.getEntities().reallyRemove(e);
        }

        if (died.length() > 0)
        {
            died.deleteCharAt(died.length() - 1);
            send(died.toString());
        }

        StringBuilder buf = new StringBuilder();
        buf.append(Constants.MOVE_PREF);
        buf.append(gameController.getTimeController().getTimeElapsed());
        buf.append(";");
        buf.append("-1,");
        buf.append(gameController.getMap().getArea().getHp());
        buf.append(";");

        for (Entity e : gameController.getEntities())
        {
            if (e.updateCheck())
            {
                buf.append(e.getUniqueID());
                buf.append(",");
                buf.append(Math.round(e.getX()));
                buf.append(",");
                buf.append(Math.round(e.getY()));
                buf.append(",");
                buf.append(e.getDirectionFacing());
                buf.append(",");
                buf.append(e.getDirectionMoving());
                buf.append(",");
                buf.append(e.getHp());
                buf.append(",");
                buf.append(e.getMoveSpeed());
                buf.append(";");
            }
        }

        if (buf.length() > 0)
        {
            buf.deleteCharAt(buf.length() - 1);
            send(buf.toString());
        }
    }

    public void setGameController(GameController gameController)
    {
        this.gameController = gameController;
        
        new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    sendAggregateEntityUpdateMessage();
                    
                    try
                    {
                        Thread.sleep(EntityConstants.MOVE_UPDATE_TIME);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public GameController getGameController()
    {
        return gameController;
    }

}
