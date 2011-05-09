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

import java.io.IOException;
import java.net.Socket;

import hrml.common.Connection;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public class Server extends Connection
{
    public Server()
        throws IOException
    {
        super();
    }
    public Server(Socket _)
        throws IOException
    {
        super(_);
    }
    public Server(String addr,int port)
        throws IOException
    {
        super(new Socket(addr,port));
    }
}
