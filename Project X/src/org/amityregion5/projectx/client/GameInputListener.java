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
package org.amityregion5.projectx.client;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Listens for game events
 *
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public interface GameInputListener
{
    public void mouseDragged(int x, int y);
    public void mouseMoved(int x, int y);
    public void mousePressed(int x, int y, int button);
    public void mouseReleased(int x, int y, int button);
    public void keyReleased(int keyCode); // only when key is released
    public void mouseScrolled(MouseWheelEvent e);
    public void keyPressed(KeyEvent e);
}
