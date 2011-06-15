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
package org.amityregion5.projectx.client.main;

import javax.swing.UIManager;

import org.amityregion5.projectx.client.communication.MulticastCommunicationHandler;
import org.amityregion5.projectx.client.gui.ServerChooserWindow;
import org.amityregion5.projectx.client.gui.UsernameWindow;
import org.amityregion5.projectx.client.preferences.PreferenceManager;
import org.amityregion5.projectx.client.sound.SoundManager;

/**
 * Main class for loading the Client
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class Main {

    public static final int SPLASH_TIME = 2000; // how long to show the splash screen (ms)
    
    public static void load()
    {
        main(new String[0]);
    }

    public static void main(String[] args)
    {
        SoundManager.preload();
        try
        {
            // default look and feel for prettiness :D
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
            // ignore
        }

        if (PreferenceManager.getUsername() == null)
        {
            // user has never used Project X before!

            new UsernameWindow(null, true, true);
        }

        final ServerChooserWindow chooser = new ServerChooserWindow();

        MulticastCommunicationHandler mch = new MulticastCommunicationHandler();
        mch.registerListener(chooser);
        mch.start();

        chooser.setVisible(true);
    }
}
