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

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import org.amityregion5.projectx.common.tools.Sound;

public class SoundManager extends Thread {

    /**
     * Starts looping a sound with the given frame length.
     * @param s the sound to play
     * @param endLoopPoint the number of frames to play before looping
     * @param level the volume at which to play the clip. 0 <= level <= 100
     */
    public static void loopSound(final Sound s, final int rate, int level)
    {
        Clip c = s.getClip();
        if (c == null)
            return; // exit if trying to loop null sound
        synchronized (s.getClip())
        {
            s.getClip().notify();
        }
        // make sure level meets 0 <= level <= 100
        level = Math.max(0, level);
        level = Math.min(level, 100);

        // starts sound from beginning
        c.setFramePosition(0);
        if (rate < 1)
        {
            c.setLoopPoints(0,-1);
        } else {
            double fps = c.getFrameLength() / (c.getMicrosecondLength() / 1000.0);
            int endFrame = (int) (fps * (1.0 / rate));
            endFrame = Math.min(endFrame, c.getFrameLength());
            c.setLoopPoints(0, endFrame);

        }

        // sets the volume according to the given level
        FloatControl fc = (FloatControl) c.getControl(FloatControl.Type.VOLUME);
        fc.setValue((level / 100f) * fc.getMaximum());

        
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopSound(Sound s)
    {
        if (s.getClip() == null) return;
        
        FloatControl fc = (FloatControl) s.getClip().getControl(FloatControl.Type.VOLUME);
        fc.setValue(0f);
        //s.getClip().stop();
        //s.getClip().setFramePosition(0);
    }

    public static int getMilliSoundLength(Sound s)
    {
        return (int) (s.getClip().getMicrosecondLength() / 1000);
    }

    public static void playOnce(Sound s)
    {
        if (s.getClip() != null)
            s.getClip().start();
    }

    public static void main(String[] args) throws InterruptedException
    {
        /*
        SoundManager.loopSound(Sound.PISTOL_SHOT, 3,100);
        Thread.sleep(2000);
        SoundManager.stopSound(Sound.PISTOL_SHOT);
        SoundManager.loopSound(Sound.BG_1, -1, 70);
        SoundManager.loopSound(Sound.PISTOL_SHOT,2,100);
        Thread.sleep(3000);
         */

        SoundManager.loopSound(Sound.NULL_SOUND,2,100);
        /*
         SoundManager.loopSound(Sound.PISTOL_SHOT,2,100);
        Thread.sleep(1000);
        SoundManager.stopSound(Sound.PISTOL_SHOT);
        SoundManager.loopSound(Sound.PISTOL_SHOT,1,100);
        Thread.sleep(1000);
        SoundManager.stopSound(Sound.PISTOL_SHOT);
        SoundManager.loopSound(Sound.PISTOL_SHOT,10,100);
        Thread.sleep(1500);
        SoundManager.stopSound(Sound.PISTOL_SHOT); */
    }
}