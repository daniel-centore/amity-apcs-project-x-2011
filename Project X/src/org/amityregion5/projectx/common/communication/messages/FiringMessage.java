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
 * A message to indicate if a client has started or stopped firing.
 * Sent from clients to server.
 *
 * @author Joe Stein
 */
public class FiringMessage extends Message {
    private boolean starting;

    public FiringMessage(boolean s)
    {
        starting = s;
    }

    /**
     * Returns whether or not this message indicates a start of firing.
     * @return true if start of firing, false if end of firing
     */
    public boolean getFireStart()
    {
        return starting;
    }
}