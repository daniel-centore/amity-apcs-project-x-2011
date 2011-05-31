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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.communication.messages.RemoveEntityMessage;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.server.Server;

/**
 * A raw client. Currently configured to read single ints (which are direction facing) from the client.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class RawClient extends Thread {
    private Socket sock;
    private boolean keepRunning = true;
    private Server server;
    private PrintWriter out;
    private DataInputStream in;
    private Player player;

    public RawClient(Socket s, Server serv)
    {
        server = serv;
        sock = s;

        try
        {
            out = new PrintWriter(s.getOutputStream());
            in = new DataInputStream(sock.getInputStream());
        } catch (IOException ex)
        {
            Logger.getLogger(RawClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String user = null;
        
        try
        {
            user = in.readUTF();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        for (Client c : server.getClients().values())
        {
            if (c.getIP().equals(sock.getInetAddress().getHostAddress()) && c.getUsername().equals(user))
            {
                while (c.getPlayer() == null)
                    System.out.println("NULL!!"); // TODO: very temporary!!!!

                player = c.getPlayer();
                c.setRaw(this);
                return;
            }
        }

        throw new RuntimeException("CLIENT NOT ESTABLISHED BEFORE RAWCLIENT!");
    }

    @Override
    public void run()
    {
        while (keepRunning)
        {
            try
            {
                int dir = in.readInt();
                // System.out.println(dir);
                this.handle(dir);
            } catch (IOException ex)
            {
                server.relayMessage(new RemoveEntityMessage(player));
                Logger.getLogger(RawClient.class.getName()).log(Level.SEVERE, null, ex);
                kill();
            }
        }
    }

    public void kill()
    {
        keepRunning = false;
    }

    private void handle(int dir)
    {
        if (player == null)
        {
            System.err.println("null player!");
            return;
        }
        player.setDirectionFacing(dir);
    }

    public void send(String string)
    {
        out.print(string);
        out.flush();
    }
}