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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * All sounds are here :-)
 */
public enum Sound {

    PISTOL_SHOT("resources/sounds/65_Pistol_Shot.mp3",false),
    NULL_SOUND(null,false),
    BG_1("resources/sounds/17_Death_Grip.mp3",true),
    LASER("resources/sounds/laser_shot.mp3",true),
    EXPLOSION("resources/sounds/65_Pistol_Shot.mp3",false);
    
    private Player player;
    private File file;
    private boolean continuous;

    private Sound(String file, boolean cont)
    {
        if (file == null)
        {
            this.file = null;
        } else
        {
            this.file = new File(file);
        }
        player = null;
        continuous = cont;
    }

    public boolean isContinuous()
    {
        return continuous;
    }

    public void reset()
    {
        if (file == null)
            return;
        
        try
        {
            if (player != null)
                player.close();
            
            player = new Player(new FileInputStream(file));
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(JavaLayerException e)
        {
            e.printStackTrace();
        }
    }
    
    public Player getPlayer()
    {
        return player;
    }

}
