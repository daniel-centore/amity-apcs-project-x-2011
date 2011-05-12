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
package org.amityregion5.projectx.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.amityregion5.projectx.common.communication.Message;

/**
 * Represents a client in the game
 * 
 * @author Daniel Centore
 *
 */
public class Client extends Thread {

    private Socket sock;

    public Client(Socket sock)
    {
        this.sock = sock;
    }

    public void run()
    {
        try
        {
            ObjectInputStream inObject = new ObjectInputStream(sock.getInputStream());

            boolean quit = false;

            while (!quit)
            {
                Message m = (Message) inObject.readObject();
                
                // ie if (m instanceof EntityMovedMessage) { ... }
            }
            
            inObject.close();
            sock.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void send(Message m)
    {
        try
        {
            ObjectOutputStream outObjects = new ObjectOutputStream(sock.getOutputStream());
            outObjects.writeObject(m);

            outObjects.flush();
        } catch (IOException e)
        {
            // This happens sometimes. I forget when though.
            e.printStackTrace();
        }
    }

}
