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
package org.amityregion5.projectx.common.communication;

import java.io.Serializable;

/**
 * Used in the LobbyWindow. Basically wraps a String and adds a nice
 * boolean for ready states.
 *
 * @author Joe Stein
 */
public class User implements Serializable {
    private static final long serialVersionUID = 123L;
    
    private String username;
    private boolean ready;

    public User(String u)
    {
        username = u;
        ready = false;
    }

    public User(String u, boolean r)
    {
        username = u;
        ready = r;
    }

    public void setReady(boolean r)
    {
        ready = r;
    }

    public boolean isReady()
    {
        return ready;
    }

    @Override
    public String toString()
    {
        return username;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof User && ((User) o).username.equals(this.username))
        {
            return true;
        }
        else if(o instanceof String)
        {
            return this.username.equals((String) o);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 37 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }
}
