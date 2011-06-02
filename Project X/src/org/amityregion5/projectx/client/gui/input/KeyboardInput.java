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
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls keyboard inputs. They should be handled in InputHandler.
 *
 * @author Michael Wenke
 * @author Daniel Centore
 * @author Joe Stein
 */
public class KeyboardInput implements KeyListener {

    private volatile List<Integer> depressed = new ArrayList<Integer>(); // keys currently pressed down

    public void keyTyped(KeyEvent e)
    {
        // keyPressed is preferable
        e.consume();
    }

    public void keyPressed(KeyEvent e)
    {
        if (!depressed.contains((Integer) e.getKeyCode()))
        {
            depressed.add(e.getKeyCode());
            InputHandler.keyPressed(e);
        }
        e.consume();
    }

    public void keyReleased(KeyEvent e)
    {
        if (depressed.contains((Integer) e.getKeyCode()))
        {
            InputHandler.keyReleased(e.getKeyCode());
            depressed.remove((Integer) e.getKeyCode());
        }
        e.consume();
    }

}
