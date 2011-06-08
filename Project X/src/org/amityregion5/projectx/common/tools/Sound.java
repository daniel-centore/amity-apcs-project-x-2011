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
package org.amityregion5.projectx.common.tools;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.amityregion5.projectx.client.sound.SoundManager;

/**
 * All sounds are here :-)
 */
public enum Sound implements Serializable {

    PISTOL_SHOT("resources/sounds/pistol_shot.wav"),
    BG_1("resources/sounds/death_grip.wav"),
    NULL_SOUND(null);
    private Clip clip;

    public Clip getClip()
    {
        return clip;
    }

    Sound(String file)
    {
        if(file == null)
            return;

        AudioInputStream ais = null;
        try
        {
            File sf = new File(file);
            ais = AudioSystem.getAudioInputStream(sf);
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,
                    AudioSystem.getAudioFileFormat(sf).getFormat()));
            clip.open(ais);
        }
        catch(LineUnavailableException ex)
        {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(UnsupportedAudioFileException ex)
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
