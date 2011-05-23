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
package org.amityregion5.projectx.server.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.AddMeMessage;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.communication.Client;

/**
 * Handles the game running
 *
 * @author Daniel Centore
 */
public class GameController {

    private List<Player> players;
    private Collection<Client> clients;
    
    public GameController(Server server)
    {
        players = new ArrayList<Player>();
        clients = server.getClients().values();

        for(Client c : clients)
        {
            Player p = new Player();
            players.add(p);
            
            c.send(new AddMeMessage(p));
        }
        
        for(Client c : clients)
        {
            for(Player p : players)
                c.send(new AddEntityMessage(p));
        }
    }

}
