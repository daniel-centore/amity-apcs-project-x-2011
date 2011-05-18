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
package org.amityregion5.projectx.preferences;

import java.util.ArrayList;
import java.util.prefs.Preferences;

/**
 * Manages and retrieves persistent user preferences.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class PreferenceManager
{

    private static ArrayList<PrefListener> listeners = new ArrayList<PrefListener>();
    public static final String USERNAME = "username";
    private static Preferences prefs;

    static
    {
        // gets the Project X preferences
        prefs = Preferences.userRoot().node("org/amityregion5/projectx");
    }

    /**
     * @return this user's preferred username, or null if the user has not
     * set a preferred username.
     */
    public static String getUsername()
    {
        return prefs.get(USERNAME, null);
    }

    /**
     * Sets the user's default username
     * @param username Username to set
     */
    public static void setUsername(String username)
    {
        prefs.put(USERNAME, username);
        for (PrefListener pl : listeners)
        {
            pl.usernameChanged(username);
        }
    }

    /**
     * Registers a PrefListener.
     * @param listener the listener to register
     */
    public static void registerListener(PrefListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a PrefListener.
     * @param listener the listener to remove
     */
    public static void removeListener(PrefListener listener)
    {
        listeners.remove(listener);
    }
}
