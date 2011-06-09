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
package org.amityregion5.projectx.server.communication;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.messages.sound.SoundControlMessage;

import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.tools.Sound;
import org.amityregion5.projectx.server.Server;

/**
 * A thread that helps deal with players shooting. Each client has one.
 *
 * @author Joe Stein
 * @author Cam Simpson
 */
public class ShotThread extends Thread {

    private PlayerEntity player;
    private Server server;
    private boolean keepShooting = true;
    private boolean keepRunning = true;

    public ShotThread(PlayerEntity p, Server s)
    {
        player = p;
        server = s;
        this.setDaemon(true);
    }

    public void setPlayer(PlayerEntity p)
    {
        player = p;
    }

    @Override
    public void run()
    {
        StringBuilder soundstr = new StringBuilder("#");
        soundstr.append(player.getCurrWeapon().getSound().toString());
        soundstr.append(",");
        soundstr.append(SoundControlMessage.Type.ONCE.toString());
        
        while (keepRunning)
        {
            // stop shooting if we're not supposed to shoot
            synchronized (this)
            {
                while (!keepShooting)
                {
                    soundstr.delete(0, soundstr.length());
                    try
                    { 
                        wait();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        keepRunning = false;
                    }
                    soundstr.append(player.getCurrWeapon().getSound().toString());
                    soundstr.append(",");
                    soundstr.append(SoundControlMessage.Type.ONCE.toString());
                }
                
            }
            try
            {
                if (player.fire())
                {
                    server.playerFired(player);
                    // server.relayMessage(new SoundControlMessage(player.getCurrWeapon().getSound(), SoundControlMessage.Type.ONCE));
                    // TODO SOUND fire if sound is not continuous
                    //server.getClients().get(player.getUsername()).s;
                }
                // sleeps in order to get the attack rate right
                Thread.sleep(1000 / player.getCurrWeapon().getAttackRate());
            } catch (InterruptedException ex)
            {
                Logger.getLogger(ShotThread.class.getName()).log(Level.SEVERE, null, ex);
                keepShooting = false;
            }
        }
    }

    /**
     * Sets whether or not this player is shooting.
     * <b>This thread will stop and resume shooting by itself.</b>
     * There is no need to externally notify() or wait() it.
     * @param shooting whether or not the player associated with this ShotThread is shooting
     */
    public void setShooting(boolean shooting)
    {
        synchronized (this)
        {
            if (shooting)
            {
                this.notify();
            }
        }
        
        keepShooting = shooting;
    }
}
