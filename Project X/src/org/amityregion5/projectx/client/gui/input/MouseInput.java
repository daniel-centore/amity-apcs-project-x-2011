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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.amityregion5.projectx.client.gui.Gui;

/**
 * Handles the mouse inputs
 * 
 * @author Daniel Centore
 * @author Mike Wenke
 */
public class MouseInput implements MouseMotionListener, MouseListener {

    public void mouseDragged(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mouseMoved(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mouseClicked(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mousePressed(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mouseEntered(MouseEvent e)
    {
        Point p = fix(e);
    }

    public void mouseExited(MouseEvent e)
    {
        Point p = fix(e);
    }
    
    /**
     * Fixes the coordinates based on screen size
     * @param e The MouseEvent we pull coordinates from
     * @return A Point inside GAME_WIDTH and GAME_HEIGHT
     */
    public static Point fix(MouseEvent e)
    {
        return fix(e.getX(), e.getY());
    }

    /**
     * Fixes the coordinates based on screen size
     * @param x X and Y coordinates to fix
     * @param y
     * @returnA Point inside GAME_WIDTH and GAME_HEIGHT
     */
    public static Point fix(int x, int y)
    {
        int width = Gui.getCurrentWidth();
        int height = Gui.getCurrentHeight();

        int newX = Gui.GAME_WIDTH * x / width;
        int newY = Gui.GAME_HEIGHT * y / height;

        return new Point(newX, newY);
    }

}