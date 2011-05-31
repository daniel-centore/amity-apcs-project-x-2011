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
package org.amityregion5.projectx.client.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import org.amityregion5.projectx.client.GameInputListener;

/**
 * Handles inputs as they come in
 * 
 * @author Michael Wenke
 * @author Daniel Centore
 * @author Cameron Simpson
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class InputHandler {
    private static ArrayList<GameInputListener> gils = new ArrayList<GameInputListener>();


    /**
     * Registers the given GameInputListener with the input handler
     * @param gil the GameInputListener to register
     */
    public static void registerListener(GameInputListener gil)
    {
        gils.add(gil);
    }

    /**
     * Unregisters the given GameInputListener.
     * @param gil the GameInputListener to unregister
     */
    public static void removeListener(GameInputListener gil)
    {
        gils.remove(gil);
    }

    public static void mouseDragged(int x, int y)
    {
        for(GameInputListener g : gils)
            g.mouseDragged(x, y);
    }

    public static void mouseMoved(int x, int y)
    {
        for(GameInputListener g : gils)
            g.mouseMoved(x, y);
    }

    public static void mousePressed(int x, int y, int button)
    {
        for(GameInputListener g : gils)
            g.mousePressed(x, y, button);
    }

    public static void mouseReleased(int x, int y, int button)
    {
        for(GameInputListener g : gils)
            g.mouseReleased(x, y, button);
    }

    public static void keyReleased(int keyCode) // only when key is released
    {
        for(GameInputListener g : gils)
            g.keyReleased(keyCode);
    }
    
    public static void keyPressed(KeyEvent e)
    {
        for (GameInputListener g : gils)
            g.keyPressed(e);
    }

    public static void mouseScrolled(MouseWheelEvent e)
    {
        for (GameInputListener g : gils)
            g.mouseScrolled(e);
    }

}
