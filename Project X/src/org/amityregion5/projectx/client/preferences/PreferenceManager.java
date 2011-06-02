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
package org.amityregion5.projectx.client.preferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Manages and retrieves persistent user preferences.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class PreferenceManager {
    public static final String USERNAME = "username";
    public static final String SPLASH_SHOWN = "splash";
    public static final String KEY_CSV = "keycsv";

    public static final long SPLASH_PERIOD = 60 * 60 * 24; //one day.

    private static ArrayList<PrefListener> listeners = new ArrayList<PrefListener>();
    private static Preferences prefs;

    static
    {
        // gets the Project X preferences
        prefs = Preferences.userRoot().node("org/amityregion5/projectx");
    }

    /**
     * @return this user's preferred username, or null if the user has not set a preferred username.
     */
    public static String getUsername()
    {
        return prefs.get(USERNAME, null);
    }

    /**
     * Sets the user's default username
     * 
     * @param username Username to set
     */
    public static void setUsername(String username)
    {
        prefs.put(USERNAME, username);
        flush();
        for (PrefListener pl : listeners)
        {
            pl.usernameChanged(username);
        }
    }

    private static void flush()
    {
        try
        {
            prefs.flush();
        } catch (BackingStoreException ex)
        {
            Logger.getLogger(PreferenceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Registers a PrefListener.
     * 
     * @param listener the listener to register
     */
    public static void registerListener(PrefListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a PrefListener.
     * 
     * @param listener the listener to remove
     */
    public static void removeListener(PrefListener listener)
    {
        listeners.remove(listener);
    }

    public static String getKeys()
    {
        return prefs.get(KEY_CSV, "w,a,s,d,t");
    }

    public static boolean checkSplashScreen() {
        long last = Long.parseLong(prefs.get(SPLASH_SHOWN, "0"));
        long time = (new Date()).getTime();
        prefs.put(SPLASH_SHOWN, ""+ time);
        return time - last > SPLASH_PERIOD;
    }
}
