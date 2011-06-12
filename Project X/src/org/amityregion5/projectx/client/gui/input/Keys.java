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
import java.util.ArrayList;
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
    
    public static int[] UP      = { KeyEvent.VK_UP, KeyEvent.VK_W };
    public static int[] DOWN    = { KeyEvent.VK_DOWN, KeyEvent.VK_S };
    public static int[] LEFT    = { KeyEvent.VK_LEFT, KeyEvent.VK_A };
    public static int[] RIGHT   = { KeyEvent.VK_RIGHT, KeyEvent.VK_D };
    
    public static int[] CHAT    = { KeyEvent.VK_T, KeyEvent.VK_QUOTE };
    public static int[] GRID    = { KeyEvent.VK_G };
    public static int[] FIRE    = { KeyEvent.VK_SPACE };
    public static int[] HEAL    = { KeyEvent.VK_B };

    public static int[] UPGRADE_WEAPON = { KeyEvent.VK_V };
    
    public static int[] BLOCK  = { KeyEvent.VK_F1 };
    public static int[] FENCE  = { KeyEvent.VK_F2 };
    public static int[] WALL   = { KeyEvent.VK_F3 };
    
    public static int[] LEADERBOARD = { KeyEvent.VK_U };
    
    public static int[] CHANGE_WEAPON_1 = { KeyEvent.VK_COMMA };
    public static int[] CHANGE_WEAPON_2 = { KeyEvent.VK_PERIOD };
    
    public static int[] RELOAD = { KeyEvent.VK_R };

    public static void refreshKeyPrefs()
    {
        String csv = PreferenceManager.getKeys();
        String[] keyses = csv.split(";");
        UP              = parseKeyList(keyses[ 0]);
        LEFT            = parseKeyList(keyses[ 1]);
        DOWN            = parseKeyList(keyses[ 2]);
        RIGHT           = parseKeyList(keyses[ 3]);
        CHAT            = parseKeyList(keyses[ 4]);
        GRID            = parseKeyList(keyses[ 5]);
        FIRE            = parseKeyList(keyses[ 6]);
        HEAL            = parseKeyList(keyses[ 7]);
        UPGRADE_WEAPON  = parseKeyList(keyses[ 8]);
        BLOCK           = parseKeyList(keyses[ 9]);
        FENCE           = parseKeyList(keyses[10]);
        WALL            = parseKeyList(keyses[11]);
        LEADERBOARD     = parseKeyList(keyses[12]);
        CHANGE_WEAPON_1 = parseKeyList(keyses[13]);
        CHANGE_WEAPON_2 = parseKeyList(keyses[14]);
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
