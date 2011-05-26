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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.server.Server;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class RawServer extends Thread {

    private static ArrayList<RawClient> rawClients;
    private boolean keepRunning = true;
    private static ServerSocket rawSock;
    private Server server; // the main server

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
                RawClient rc = new RawClient(s,server);
                rc.start();
                rawClients.add(rc);
            } catch (IOException ex)
            {
                Logger.getLogger(RawServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void kill()
    {
        keepRunning = false;
    }

    /**
     * Writes a string to the raw output.
     * String format should be:
     * entityUniqueId,x,y,dir
     * Please keep x, y, and dir to int values.
     * Strings should not have trailing newline characters.
     * @param s the CSV string to send
     */
    public void send(String s)
    {
        for (RawClient out : rawClients)
        {
            out.send(s + "\n");
        }
    }
    public void send(Entity e)
    {
        String send = "" + e.getUniqueID();
        send += "," + e.getX();
        send += "," + e.getY();
        send += "," + e.getDirectionFacing();
        for (RawClient out : rawClients)
        {
            out.send(send + "\n");
        }
    }
}