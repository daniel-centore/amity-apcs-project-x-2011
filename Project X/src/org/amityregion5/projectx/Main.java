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
package org.amityregion5.projectx;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.amityregion5.projectx.client.gui.SplashScreen;
import org.amityregion5.projectx.client.preferences.PreferenceManager;
import org.amityregion5.projectx.client.sound.SoundManager;

/**
 * The project's umbrella main. Will be the entry point of the jar.
 *
 * @author Joe Stein
 * @author Daniel Centore
 */
public class Main
{
    public static final int SPLASH_TIME = 2000;

    public static void main(String[] args)
    {
        SoundManager.preload();
        
        if (args.length > 0)
        {
            org.amityregion5.projectx.server.Main.main(args);
            return;
        } else
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, null, ex);
            }
            SplashScreen s = null;

            if (PreferenceManager.checkSplashScreen())
            {
                s = new SplashScreen();
                try
                {
                    Thread.sleep(SPLASH_TIME);
                } catch (InterruptedException e)
                {
                    // ignore
                }
                s.dispose();
            }

            if (s != null)
                s.dispose();

            new MainWindow();
        }
    }
}
