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

/**
 * A status update from the server to clients.
 * 
 * @author Joe Stein
 * @author Daniel Centore
 */
public class StatusUpdateMessage extends TextualMessage {

    private static final long serialVersionUID = 1L;

    private Type type; // the type of status message

    public enum Type
    {
        WAITING, STARTING, END_GAME;
    }

    /**
     * Creates a StatusUpdateMessage without a status
     * 
     * @param type Type of message
     */
    public StatusUpdateMessage(Type type)
    {
        this(null, type);
    }

    /**
     * Creates a StatusUpdateMessage
     * 
     * @param status The status to show in the Lobby
     * @param type Type of message
     */
    public StatusUpdateMessage(String status, Type type)
    {
        super(status);
        this.type = type;
    }

    /**
     * @return The type of message (see enum Type)
     */
    public Type getType()
    {
        return type;
    }
}
