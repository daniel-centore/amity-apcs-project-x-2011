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

package org.amityregion5.projectx.client.sound;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.amityregion5.projectx.common.tools.Sound;

public class SoundManager extends Thread {

    /**
     * Starts looping a sound with the given frame length.
     * @param s the sound to play
     * @param endLoopPoint the number of frames to play before looping
     */
    public static void loopSound(final Sound s, final int rate)
    {
        //System.out.println(s.clip.getFrameLength());
        //System.out.println(s.clip.getMicrosecondLength());
        
        
//        if (rate < 1)
//        {
//            s.clip.setLoopPoints(0,-1);
//        } else {
//            double fps = s.clip.getFrameLength() / s.clip.getMicrosecondLength() / 1000.0;
//            int endFrame = (int) (fps * rate);
//            endFrame = Math.min(endFrame, s.clip.getFrameLength());
//            s.clip.setLoopPoints(0, endFrame);
//        }
//        s.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopSound(Sound s)
    {
//        s.clip.stop();
    }

    public static int getMilliSoundLength(Sound s)
    {
        return -1;
//        return (int) (s.clip.getMicrosecondLength() / 1000);
    }

    public static void playOnce(Sound s)
    {
//        s.clip.start();
    }

    public static void main(String[] args)
    {
        SoundManager.loopSound(Sound.PISTOL_SHOT, 3);
    }
}