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
package org.amityregion5.projectx.client.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.DatagramListener;

/**
 * A class for listening for multicast packets.
 * 
 * NOTE: Make sure you start() it!
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class MulticastCommunicationHandler extends Thread {

    private boolean keepListening = true; // should we be listening for more servers?
    private ArrayList<DatagramListener> dgListeners = new ArrayList<DatagramListener>(); // things listening for multicast replies
    private MulticastSocket sock; // the socket we listen on

    @Override
    public void run()
    {
        try
        {
            sock = new MulticastSocket(Constants.UDPORT);
            InetAddress group = InetAddress.getByName(Constants.UDPGROUP);
            sock.joinGroup(group);
            DatagramPacket packet;
            while (keepListening)
            {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                firePacketReceived(packet);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(MulticastCommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            keepListening = false;
        }
    }

    private void firePacketReceived(DatagramPacket pack) // helper
    {
        for (DatagramListener dgl : dgListeners)
        {
            dgl.handle(pack);
        }
    }

    /**
     * Registers a listener for multicast stuff
     * @param listener The DatagramListener
     */
    public void registerListener(DatagramListener listener)
    {
        dgListeners.add(listener);
    }

    /**
     * Un-registers a listener
     * @param listener The DatagramListener
     */
    public void removeListener(DatagramListener listener)
    {
        dgListeners.remove(listener);
    }
}
