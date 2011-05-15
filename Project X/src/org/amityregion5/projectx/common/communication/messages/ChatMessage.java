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
package org.amityregion5.projectx.common.communication.messages;

import java.util.ArrayList;

/**
 * A chat message.
 *
 * @author Joe Stein
 */
public class ChatMessage extends TextualMessage {
    public enum Type
    {
        PUBLIC,
        TEAM,
        PRIVATE;
    }

    private String usernameFrom;
    private ArrayList<String> destUsers = new ArrayList<String>();
    private Type type; // type of message (see inner enum Type)

    public ChatMessage(String cont, String from)
    {
        super(cont);
        usernameFrom = from;
    }

    public ChatMessage(String cont, Type t, String from)
    {
        super(cont);
        type = t;
        usernameFrom = from;
    }

    public String getFrom()
    {
        return usernameFrom;
    }

    public ArrayList<String> getDestUsers()
    {
        return destUsers;
    }

    public Type getType()
    {
        return type;
    }


    
}