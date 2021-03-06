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

/**
 * Tells the server that a client is ready
 *
 * @author Daniel Centore
 * @author Joe Stein
 */
public class ReadyMessage extends BooleanReplyMessage {

    private static final long serialVersionUID = 219L;
    private String username;

    /**
     * Initalizer
     * @param r True if we are ready; false otherwise
     */
    public ReadyMessage(boolean r)
    {
        super(r);
    }

    public ReadyMessage(boolean r, String username)
    {
        this(r);
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String s)
    {
        username = s;
    }

}
