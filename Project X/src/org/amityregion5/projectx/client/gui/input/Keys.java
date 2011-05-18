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
import org.amityregion5.projectx.common.preferences.PreferenceManager;

/**
 * A class with key codes.
 *
 * @author Joe Stein
 */
public class Keys {
    // Note: key CSV's are in the format:
    // <up>,<left>,<down>,<right>
    public static int UP_KEY = KeyEvent.VK_UP;
    public static int DOWN_KEY = KeyEvent.VK_DOWN;
    public static int LEFT_KEY = KeyEvent.VK_LEFT;
    public static int RIGHT_KEY = KeyEvent.VK_RIGHT;

    public static void updateKeys()
    {
        String csv = PreferenceManager.getKeys();
        String[] vals = csv.split(",");
        UP_KEY = Integer.parseInt(vals[0]);
        LEFT_KEY = Integer.parseInt(vals[1]);
        DOWN_KEY = Integer.parseInt(vals[2]);
        RIGHT_KEY = Integer.parseInt(vals[3]);

    }
}