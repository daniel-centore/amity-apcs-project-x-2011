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
 * Tells the client to remove an entity
 *
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class RemoveEntityMessage extends Message
{
    private static final long serialVersionUID = 575L;
    
    private long id;
    
    public RemoveEntityMessage(long id)
    {
        // TODO: remove references to this
        throw new RuntimeException("NOT SUPPORTED ANYMORE");
//        this.id = id;
    }

    public long getID()
    {
        return id;
    }
    
    
}
