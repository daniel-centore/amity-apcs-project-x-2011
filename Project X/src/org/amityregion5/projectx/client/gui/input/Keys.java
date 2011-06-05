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
package org.amityregion5.projectx.client.gui.input;

import java.awt.event.KeyEvent;
import java.util.List;

import org.amityregion5.projectx.client.preferences.PreferenceManager;

/**
 * A class with key codes.
 * Key CSVs are in the format: up;left;down;right
 *
 * @author Joe Stein
 * @author Mike DiBuduo
 * @author Michael Zuo
 */
public class Keys {
    
    public static int[] UP      = { KeyEvent.VK_K, KeyEvent.VK_UP };
    public static int[] DOWN    = { KeyEvent.VK_J, KeyEvent.VK_DOWN };
    public static int[] LEFT    = { KeyEvent.VK_H, KeyEvent.VK_LEFT };
    public static int[] RIGHT   = { KeyEvent.VK_L, KeyEvent.VK_RIGHT };
    public static int[] CHAT    = { KeyEvent.VK_T, KeyEvent.VK_SEMICOLON };
    public static int[] GRID    = { KeyEvent.VK_G };
    public static int[] FIRE    = { KeyEvent.VK_SPACE };
    
    public static int[] BLOCK  = { KeyEvent.VK_F1 };

    public static void refreshKeyPrefs()
    {
        String csv = PreferenceManager.getKeys();
        String[] keyses = csv.split(";");
        UP      = parseKeyList(keyses[0]);
        LEFT    = parseKeyList(keyses[1]);
        DOWN    = parseKeyList(keyses[2]);
        RIGHT   = parseKeyList(keyses[3]);
        CHAT    = parseKeyList(keyses[4]);
    }

    private static int[] parseKeyList(String keys) // helper
    {
        String[] asList = keys.split(",");
        int[] asInts = new int[asList.length];
        for (int i = 0; i < asList.length; i++)
        {
            asInts[i] = Integer.parseInt(asList[i]);
        }
        return asInts;
    }

    public static boolean isKey(int[] keys, int want)
    {
        for (int each : keys)
        {
            if (want == each)
            {
                return true;
            }
        }

        return false;
    }

    public static int numIsKey(int[] keys, List<Integer> want)
    {
        int ct = 0;

        for (int each : want)
        {
            if (isKey(keys, each))
            {
                ct++;
            }
        }

        return ct;
    }
}
