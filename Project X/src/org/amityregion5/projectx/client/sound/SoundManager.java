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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * A class that handles playing sounds client-side.
 *
 * @author Joe Stein
 */
public class SoundManager {

    public static void main(String[] args)
    {
        try
        {
        InputStream in = new FileInputStream(new File(
                "resources/sounds/wav/pistol_shot_short.wav"));

// Create an AudioStream object from the input stream.
        final AudioStream as = new AudioStream(in);

// Use the static class member "player" from class AudioPlayer to play
// clip.
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    System.out.println("shoot");
                    AudioPlayer.player.start(as);
                        try
                        {
                            Thread.sleep(200);
                        }
                        catch(InterruptedException ex)
                        {
                            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
            }
        }.start();
        
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
