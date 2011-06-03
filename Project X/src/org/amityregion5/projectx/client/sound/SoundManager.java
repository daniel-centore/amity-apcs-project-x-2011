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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class that handles playing sounds client-side.
 *
 * @author Joe Stein
 */
public class SoundManager {

    public static enum Sound
    {
        PISTOL_SHOT("resources/sounds/wav/pistol_shot_short.wav"),
        BG_1("resources/sounds/wav/bg_02.wav");
        
        private Clip clip;
        Sound(String file)
        {
            AudioInputStream ais = null;
            try
            {
                File sf = new File(file);
                ais = AudioSystem.getAudioInputStream(sf.toURI().toURL());
                clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,
                    AudioSystem.getAudioFileFormat(sf).getFormat()));
                clip.open(ais);
            }
            catch(LineUnavailableException ex)
            {
                Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
            }            catch(UnsupportedAudioFileException ex)
            {
                Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(IOException ex)
            {
                Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                try
                {
                    ais.close();
                }
                catch(IOException ex)
                {
                    Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Starts looping a sound with the given frame length.
     * @param s the sound to play
     * @param endLoopPoint the number of frames to play before looping
     */
    public static void playSound(Sound s, int endLoopPoint)
    {
        System.out.println(s.clip.getFrameLength());
        System.out.println(s.clip.getMicrosecondLength());
        endLoopPoint = Math.min(endLoopPoint, s.clip.getFrameLength());
        s.clip.setLoopPoints(0, endLoopPoint);
        s.clip.loop(Clip.LOOP_CONTINUOUSLY);
        s.clip.start();
    }

    public static void stopSound(Sound s)
    {
        s.clip.stop();
    }

    public static int getMilliSoundLength(Sound s)
    {
        return (int) (s.clip.getMicrosecondLength() / 1000);
    }

    public static void main(String[] args)
    {
        /*new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    SoundManager.playSound(SoundManager.Sound.PISTOL_SHOT,Integer.MAX_VALUE);
                    Thread.sleep(SoundManager.getMilliSoundLength(Sound.PISTOL_SHOT));
                }
                // TODO make the sound play
                catch(InterruptedException ex)
                {
                    Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                //SoundManager.stopSound(SoundManager.Sound.PISTOL_SHOT);
            }
            // TODO make the sound play
        }.start();*/
        SoundManager.playSound(Sound.BG_1, -1);
    }
}
