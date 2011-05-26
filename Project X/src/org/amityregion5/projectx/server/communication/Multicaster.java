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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.communication.Constants;

/**
 * Multicasts this server's name so players on the intranet can connect easily.
 * 
 * @author Joe Stein
 */
public class Multicaster extends Thread {

    private boolean keepBroadcasting = true; // should we keep broadcasting?
    private String name; // the name to broadcast
    private MulticastSocket sock; // the socket to broadcast on

    /**
     * Creates a Multicaster
     * @param name The name of the server to broadcast
     */
    public Multicaster(String name)
    {
        this.name = name;
        try
        {
            sock = new MulticastSocket();
        } catch (IOException ex)
        {
            Logger.getLogger(Multicaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Starts multicasting (if not already doing so).
     */
    public void startMulticasting()
    {
        if (!keepBroadcasting)
        {
            keepBroadcasting = true;
            start();
        }
    }

    /**
     * Stops multicasting.
     */
    public void stopMulticasting()
    {
        keepBroadcasting = false;

    }

    @Override
    public void run()
    {
        InetAddress group = null;
        DatagramPacket packet = null;
        byte[] buf = name.getBytes();
        try
        {
            group = InetAddress.getByName(Constants.UDPGROUP);
            packet = new DatagramPacket(buf, buf.length, group, Constants.UDPORT);
        }
        catch(UnknownHostException ex)
        {
            Logger.getLogger(Multicaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (keepBroadcasting)
        {
            try
            {
                sock.send(packet);

                sleep(Constants.MULTICAST_INTERVAL);
            } catch (InterruptedException ex)
            {
                keepBroadcasting = false;
                ex.printStackTrace();
            } catch (IOException e)
            {
                keepBroadcasting = false;
                e.printStackTrace();
            }
        }
    }
}
