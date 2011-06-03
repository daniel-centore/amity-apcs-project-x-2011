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

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;

/**
 * A class that handles playing sounds client-side.
 *
 * @author Joe Stein
 */
public class SoundManager {
    /**
    public static void main(String[] args)
    {
        try
        {
            Line line = AudioSystem.getLine(new DataLine.Info(Clip.class,
                    AudioFormat.Encoding.PCM_UNSIGNED));
            AudioClip sound = Applet.newAudioClip(
                    new URL("./resources/sounds/pistol_shot_short.ogg"));
            sound.play();
        }
        catch(MalformedURLException ex)
        {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     **/
}