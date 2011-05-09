/**
 * Copyright (c) 2011 res.
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
package hrml.client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public class Server
{
    public final Socket from;
    public final InputStream recv;
    public final Scanner scan;
    public final PrintStream send;

    public Server()
        throws IOException
    {
        this(null,-1);
    }
    public Server(String addr,int port)
        throws IOException
    {
        from = new Socket(addr,port);
        recv = new DataInputStream(from.getInputStream());
        scan = new Scanner(recv);
        send = new PrintStream(from.getOutputStream());
    }
}
