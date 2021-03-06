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
package org.amityregion5.projectx.common.communication.messages;

import java.util.List;
import org.amityregion5.projectx.common.communication.User;

/**
 * Has a list of active players on the server
 * 
 * @author Daniel Centore
 * 
 */
public class ActivePlayersMessage extends Message {

    private static final long serialVersionUID = 145L;

    private List<User> players; // player list

    /**
     * Creates an ActivePlayersMessage
     * @param players List of players
     */
    public ActivePlayersMessage(List<User> players)
    {
        this.players = players;
    }

    /**
     * Gets the active players on the server in the form of a List of Strings.
     * 
     * @return the active players on the server in a List<String>
     */
    public List<User> getPlayers()
    {
        return players;
    }

    /**
     * Sets the list of players
     * @param players The list
     */
    public void setPlayers(List<User> players)
    {
        this.players = players;
    }

}
