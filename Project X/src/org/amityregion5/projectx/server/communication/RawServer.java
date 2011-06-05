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

import org.amityregion5.projectx.server.Server;

/**
 * The RawServer which looks for new RawClients. Muahahah!
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class RawServer extends Thread {

    private static ArrayList<RawClient> rawClients; // THe list of clients
    private boolean keepRunning = true; // Should we keep looking?
    private static ServerSocket rawSock; // Socket to search upon
    private Server server; // the main server

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
            System.err.println("No raw clients! WTF!");

        for (RawClient out : rawClients)
        {
            out.send(s + "\n");
        }
    }

}
