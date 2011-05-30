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
package org.amityregion5.projectx.server.controllers;

/**
 * A minimal ServerController. Does not accept input, but can be used as
 * a ServerController.
 *
 * @author Joe Stein
 */
public class DefaultServerController implements ServerController{
    private static final long serialVersionUID = 1L;

    public void clientJoined(String username)
    {
        System.out.println(username + "\" joined");
    }

    public void clientLeft(String username)
    {
        System.out.println(username + "\" left");
    }

    public void clientConnected(String ip)
    {
        System.out.println("Client connected from " + ip);
    }

    public void chatted(String username, String chat)
    {
        System.out.println(username + ": " + chat);
    }

}
