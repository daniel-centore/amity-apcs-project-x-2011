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

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.entities.Entity;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class RawServer extends Thread {

    private static ArrayList<DataOutputStream> rawClients;
    private boolean keepRunning = true;
    private ServerSocket rawSock;

    public RawServer(int port)
    {
        rawClients = new ArrayList<DataOutputStream>();
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
                rawClients.add(new DataOutputStream(s.getOutputStream()));
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

    public static void send(byte[] bytes)
    {
        for (DataOutputStream out : rawClients)
        {
            try
            {
                out.write(bytes);
                out.flush();
            } catch (IOException ex)
            {
                Logger.getLogger(RawServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void send(Entity e)
    {
        for (DataOutputStream out : rawClients)
        {
            // TODO write the entity's location and unique id as a byte array
        }
    }
}