/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 *
 * This program is free software: you can redistribute it and/or
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.Message;

/**
 * Handles messages from, and sends messages to, the server.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class CommunicationHandler extends Thread {

    public static final int TIMEOUT_SECS = 6;

    private String serverIP;
    private Socket socket = null;
    private boolean keepReading = true;
    private ArrayList<MessageListener> listeners = new ArrayList<MessageListener>();

    public CommunicationHandler(String serverIP)
    {
        this.serverIP = serverIP;
        this.start();

        int i = 0;
        while (socket == null)
        {
            if (i >= TIMEOUT_SECS)
            {
                System.out.println("Server timeout [" + TIMEOUT_SECS + " secs] exceeded! Terminating...");
                System.exit(-1);
            }

            i++;

            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
            }
        }
    }

    @Override
    public void run()
    {
        try
        {
            socket = new Socket(serverIP, Constants.PORT);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

        try
        {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            while (keepReading)
            {
                Message m = (Message) input.readObject();
                handle(m);
            }

        } catch (IOException e1)
        {
            e1.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void handle(Message m)
    {
        for (MessageListener mh : listeners)
        {
            mh.handle(m);
        }
    }

    public void registerListener(MessageListener mh)
    {
        listeners.add(mh);
    }

    public void removeListener(MessageListener mh)
    {
        listeners.remove(mh);
    }

    public void send(Message m)
    {
        // TODO send a Message to the server.

        try
        {
            ObjectOutputStream outObjects = new ObjectOutputStream(socket.getOutputStream());
            outObjects.writeObject(m);

            outObjects.flush();
        } catch (IOException e)
        {
            // This happens sometimes. I forget when though.
            e.printStackTrace();
        }
    }

    public String getServerIP()
    {
        return serverIP;
    }

    public Socket getSocket()
    {
        return socket;
    }

}
