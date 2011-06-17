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

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.amityregion5.projectx.client.gui.GameWindow;

/**
 * Controls mouse inputs. They should be handled in InputHandler.
 * 
 * @author Daniel Centore
 * @author Mike Wenke
 */
public class MouseInput implements MouseMotionListener, MouseListener, MouseWheelListener {

    public void mouseDragged(MouseEvent e)
    {
        Point p = fix(e);
        InputHandler.mouseDragged(p.x, p.y);
    }

    public void mouseMoved(MouseEvent e)
    {
        Point p = fix(e);
        InputHandler.mouseMoved(p.x, p.y);
    }

    public void mouseClicked(MouseEvent e)
    {
        // mouseReleased is preferable
    }

    public void mousePressed(MouseEvent e)
    {
        Point p = fix(e);
        InputHandler.mousePressed(p.x, p.y, e.getButton());
    }

    public void mouseReleased(MouseEvent e)
    {
        Point p = fix(e);
        InputHandler.mouseReleased(p.x, p.y, e.getButton());
    }

    public void mouseEntered(MouseEvent e)
    {
        // useless for now
    }

    public void mouseExited(MouseEvent e)
    {
        // useless for now
    }

    /**
     * Fixes the coordinates based on screen size
     * 
     * @param e The MouseEvent we pull coordinates from
     * @return A Point inside GAME_WIDTH and GAME_HEIGHT
     */
    public static Point fix(MouseEvent e)
    {
        return fix(e.getX(), e.getY());
    }

    /**
     * Fixes the coordinates based on screen size
     * 
     * @param x X and Y coordinates to fix
     * @param y
     * @return a Point inside GAME_WIDTH and GAME_HEIGHT
     */
    public static Point fix(int x, int y)
    {
        if (GameWindow.getCurrentHeight() == 0)
            return new Point(0, 0);
        int width = GameWindow.getCurrentWidth();
        int height = GameWindow.getCurrentHeight();

        int offsetX = GameWindow.getInstance().getXOffset();
        int offsetY = GameWindow.getInstance().getYOffset();

        width -= offsetX * 2;
        height -= offsetY * 2;

        x -= offsetX; // adjusts based on how we scaled
        y -= offsetY;

        int newX = GameWindow.GAME_WIDTH * x / width;
        int newY = GameWindow.GAME_HEIGHT * y / height;

        return new Point(newX, newY);
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
        InputHandler.mouseScrolled(e);
    }

}
