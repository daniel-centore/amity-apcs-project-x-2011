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
import java.net.Socket;
import java.net.UnknownHostException;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.Message;

public class CommunicationHandler extends Thread {

    private String server;

    public CommunicationHandler(String server)
    {
        this.server = server;
    }

    public void run()
    {
        Socket socket = null;

        try
        {
            socket = new Socket(server, Constants.PORT);
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

            boolean quit = false;

            while (!quit)
            {
                Message m = (Message) input.readObject();

                // ie if (m instanceof EntityMovedMessage) { ... }
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

}
