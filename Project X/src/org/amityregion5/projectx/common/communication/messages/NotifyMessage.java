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
 * Sends a simple notification so we're not making new classes for every one
 * 
 * @author Daniel Centore
 * 
 */
public class NotifyMessage extends Message {

    private static final long serialVersionUID = 1L;

    public enum Type
    {
        LOBBY_READY,
    }

    private Type what; // what kind of notify message it is

    /**
     * Creates a notify window
     * 
     * @param what ID to set it to
     */
    public NotifyMessage(Type what)
    {
        this.what = what;
    }

    /**
     * @return ID of the notification
     */
    public Type getWhat()
    {
        return what;
    }

    /**
     * Sets the ID of the notification
     * 
     * @param what What to set it to
     */
    public void setWhat(Type what)
    {
        this.what = what;
    }

}
