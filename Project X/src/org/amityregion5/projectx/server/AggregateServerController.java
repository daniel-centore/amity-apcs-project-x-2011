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
package org.amityregion5.projectx.server;

import java.util.ArrayList;

import org.amityregion5.projectx.server.gui.GUIServerController;

/**
 * Class documentation.
 *
 * @author Michael Zuo
 */
public class AggregateServerController
    extends ArrayList<ServerController>
    implements ServerController
{
    public AggregateServerController(Server s) {
        add(new CommandServerController(s));
        add(new GUIServerController(s));
    }
    public AggregateServerController(ServerController... ctls) {
        for (ServerController ctl : ctls)
            add(ctl);
    }

    public void clientJoined(String username) {
        for (ServerController ctl : this)
            ctl.clientJoined(username);
    }

    public void clientLeft(String username) {
        for (ServerController ctl : this)
            ctl.clientLeft(username);
    }

    public void clientConnected(String ip) {
        for (ServerController ctl : this)
            ctl.clientConnected(ip);
    }

    public void chatted(String username, String chat) {
        for (ServerController ctl : this)
            ctl.chatted(username,chat);
    }
}
