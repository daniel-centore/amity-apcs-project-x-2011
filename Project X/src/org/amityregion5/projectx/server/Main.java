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
package org.amityregion5.projectx.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.amityregion5.projectx.server.gui.ServerNameWindow;

/**
 * Server-side program entry point
 * 
 * @author Jenny Liu
 * @author Joe Stein
 * @author Daniel Centore
 */
public class Main {

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        ArrayList<String> ar = new ArrayList<String>();
        ar.addAll(Arrays.asList(args));
        boolean gui = true;
        String name = null;
        if (ar.contains("--nogui"))
        {
            gui = false;
        }
        int ind = ar.indexOf("--name");
        if (ind > -1 && ind != ar.size() - 1)
        {
            name = ar.get(ind + 1);
            if (name.startsWith("--") || name.startsWith("-"))
            {
                System.err.println("ERROR: You must include a valid server" + " name after the --name tag.");
                System.exit(1);
            }
        }

        if (!gui && name != null)
        {
            Server s = new Server(name);
            CommandServerController csc = new CommandServerController(s);
            s.setController(csc);
        } else if (!gui && name == null)
        {
            System.err.println("ERROR: You must include a valid server name" + " if you do not want to use the GUI.");
        } else if (gui && name != null)
        {
            Server s = new Server(name);
            ServerController gsc = new AggregateServerController(s);
            s.setController(gsc);
        } else
        {
            new ServerNameWindow();
        }
    }
}
