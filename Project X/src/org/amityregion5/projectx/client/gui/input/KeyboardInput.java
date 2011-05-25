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
 */
public class KeyboardInput implements KeyListener {

    private volatile List<Integer> depressed = new ArrayList<Integer>(); // keys currently pressed down

    /**
     * Initializes the KeyHandler run thread
     */
    public KeyboardInput()
    {
        /*
        new Thread() {
            @Override
            public void run()
            {
                while (true)
                {
                    for (int i = 0; i < depressed.size(); i++)
                        InputHandler.keyPressed(depressed.get(i));

                    try
                    {
                        Thread.sleep(10);
                    } catch (InterruptedException e)
                    {
                    }
                }
            }
        }.start(); */
    }

    public void keyTyped(KeyEvent e)
    {
        // keyPressed is preferable
    }

    public void keyPressed(KeyEvent e)
    {
//        if (!depressed.contains(e.getKeyCode()))
//            depressed.add(e.getKeyCode());
        InputHandler.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e)
    {
//        depressed.remove((Integer) e.getKeyCode()); // cast so we're removing object, not index
        InputHandler.keyReleased(e.getKeyCode());
    }

}
