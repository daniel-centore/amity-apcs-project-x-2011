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
import java.net.ServerSocket;

import org.amityregion5.projectx.common.communication.Constants;

/**
 * Accepts incoming connections and makes Clients for them
 * 
 * @author Daniel Centore
 */
public class Server {

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try
        {
            serverSocket = new ServerSocket(Constants.PORT);
        }
        catch (IOException e)
        {
            // usually means a server is already running
            e.printStackTrace();
            System.exit(-1);
        }

        while (listening)
            new Client(serverSocket.accept()).start();

        serverSocket.close();
    }

}
