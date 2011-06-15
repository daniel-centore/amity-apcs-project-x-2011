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
package org.amityregion5.projectx.client.sound;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.amityregion5.projectx.client.GameInputListener;

/**
 * A class that handles playing sounds client-side.
 * 
 * @author Daniel Centore
 */
public class SoundManager extends Thread {

    public static boolean BACKGROUND = false;
    private static volatile Player player = null;

    private static File SONG_1 = new File("resources/sounds/Song1");
    private static File SONG_2 = new File("resources/sounds/Song2");
    private static File SONG_3 = new File("resources/sounds/Song3");
    private static File SONG_4 = new File("resources/sounds/Song4");

    public static void play(int song)
    {
    }

    private static void playMe(final File f)
    {
        if(player != null)
            player.close();

        new Thread() {

            public void run()
            {
                try
                {
                    player = new Player(new FileInputStream(f));
                    player.play();
                }
                catch(JavaLayerException ex)
                {
                    ex.printStackTrace();
                }
                catch(FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
