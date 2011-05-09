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
package hrml.server;

import java.io.IOException;
import java.net.Socket;

import hrml.common.Connection;

/**
 * @author Michael Zuo <sreservoir@gmail.com>
 */
public class Client extends Connection
{
    public Client()
        throws IOException
    {
        super();
    }
    public Client(Socket _)
        throws IOException
    {
        super(_);
    }
}
