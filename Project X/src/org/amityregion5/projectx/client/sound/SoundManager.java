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


import javazoom.jl.decoder.JavaLayerException;
import org.amityregion5.projectx.common.tools.Sound;

/**
 * A class that handles playing sounds client-side.
 * 
 * @author Joe Stein
 * @author Cam Simpson
 * @author Daniel Centore
 */
public class SoundManager extends Thread {

    public static boolean PLAY = false;
    public static boolean BACKGROUND = false;

    /**
     * Loops a sound
     * @param s Sound to loop
     */
    public static void playLoop(final Sound s)
    {
        if(!PLAY)
            return;

        new Thread() {

            @Override
            public void run()
            {
                while(true)
                {
                    s.reset();

                    try
                    {
                        if(s.getPlayer() != null)
                            s.getPlayer().play();
                    }
                    catch(JavaLayerException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    public static void stopSound(Sound s)
    {
        s.getPlayer().close();
    }

    /**
     * Plays a sound once
     * @param s Sound to play
     */
    public static void playOnce(final Sound s)
    {
        if(!PLAY)
            return;
        new Thread() {

            @Override
            public void run()
            {
                try
                {
                    s.reset();
                    if(s.getPlayer() != null)
                        s.getPlayer().play();
                }
                catch(JavaLayerException e)
                {
                    e.printStackTrace();
                }

                s.reset();
            }
        }.start();

    }

    /**
     * Kludge to load a sound but not play it so we speed up sound loading later :-)
     */
    public static void preload()
    {
        Sound.BG_1.reset();
    }

}
