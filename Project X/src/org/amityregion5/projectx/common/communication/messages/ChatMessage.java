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

import java.util.ArrayList;

/**
 * A chat message.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 * 
 */
public class ChatMessage extends TextualMessage {

    private static final long serialVersionUID = 1L;

    /**
     * The type of chat message this is
     * 
     * @author Joe Stein
     */
    public enum Type
    {
        PUBLIC, TEAM, PRIVATE;
    }

    private String usernameFrom; // who the message is from
    private String destUser = null; // the user to send it to (if private)
    private Type type; // type of message (see inner enum Type)

    /**
     * Creates a chat message
     * 
     * @param cont The message
     * @param from Who it's from
     */
    public ChatMessage(String cont, String from)
    {
        super(cont);
        type = Type.PUBLIC;
        usernameFrom = from;
    }

    /**
     * Creates a chat message
     * 
     * @param cont The message
     * @param t The visibility (see enum type)
     * @param from Who it's from
     */
    public ChatMessage(String cont, Type t, String from)
    {
        super(cont);
        type = t;
        usernameFrom = from;
    }

    /**
     * @return The person it's from
     */
    public String getFrom()
    {
        return usernameFrom;
    }

    /**
     * @return The users it's destined for
     */
    public String getDestUser()
    {
        return destUser;
    }

    /**
     * @return Type of message it is (see enum type)
     */
    public Type getType()
    {
        return type;
    }
}
