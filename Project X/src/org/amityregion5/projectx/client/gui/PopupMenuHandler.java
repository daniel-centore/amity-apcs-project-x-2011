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
package org.amityregion5.projectx.client.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.gui.input.MouseInput;
import org.amityregion5.projectx.common.communication.messages.RequestEntityAddMessage;
import org.amityregion5.projectx.common.entities.EntityConstants;

/**
 * Note: To add more field items, just modify {@link RequestEntityAddMessage}. It has everything.
 * 
 * @author Daniel Centore
 * 
 */
public class PopupMenuHandler extends MouseAdapter implements ActionListener {

    private static String lastAdded = null;
    
    public static final int GRID_SIZE = 40;
    
    public static final String REPEAT = "Repeat add ";

    private int popupX; // where the popup came
    private int popupY;
    private JPopupMenu popup;
    private CommunicationHandler ch;

    public PopupMenuHandler(CommunicationHandler ch)
    {
        this.ch = ch;
        init();
    }
    
    private void init()
    {
        popup = new JPopupMenu();
        JMenu subMenu;
        JMenuItem menuItem;

        if (lastAdded != null)
        {
            menuItem = new JMenuItem(REPEAT + lastAdded);
            menuItem.addActionListener(this);
            popup.add(menuItem);
        }
        
        // Field Item sub-menu
        subMenu = new JMenu("Add Field Item");

        for (String s : EntityConstants.fieldItems)
        {
            menuItem = new JMenuItem(s);
            menuItem.addActionListener(this);
            subMenu.add(menuItem);
        }
        // End field item sub menu

        popup.add(subMenu);
    }

    public void mousePressed(MouseEvent e)
    {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e)
    {
        if (e.isPopupTrigger())
        {
            Point p = MouseInput.fix(e);
            popupX = p.x;
            popupY = p.y;
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    public static Point roundToGrid(int x, int y)
    {
        int roundedX = (x / GRID_SIZE + Math.round((x % GRID_SIZE) / GRID_SIZE)) * GRID_SIZE;
        int roundedY = (y / GRID_SIZE + Math.round((y % GRID_SIZE) / GRID_SIZE)) * GRID_SIZE;
        
        return new Point(roundedX, roundedY);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        Point p = roundToGrid(popupX, popupY);
        int roundedX = p.x;
        int roundedY = p.y;
        
        if (s.startsWith(REPEAT))
            s = lastAdded;
        else
            lastAdded = s;
        
        ch.send(new RequestEntityAddMessage(s, roundedX, roundedY));
        
        init();
    }
}
