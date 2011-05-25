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
package org.amityregion5.projectx.server.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;

/**
 * A thread that will move all Entities by need.
 *
 * @author Joe Stein
 */
public class EntityMoverThread extends Thread {
    private boolean keepRunning = true;
    private GameController gameController;

    public EntityMoverThread(GameController gc)
    {
        gameController = gc;
    }
    
    @Override
    public void run()
    {
        while (keepRunning)
        {
            for (Entity e : gameController.getEntities())
            {
                double r = e.getMoveSpeed();
                double theta = e.getDirectionMoving();
                double xOffset = r * Math.cos(Math.toRadians(theta));
                double yOffset = r * Math.sin(Math.toRadians(theta));
                e.setX(e.getX() + xOffset);
                e.setY(e.getY() + yOffset);
            }
            sendAggregateUpdateMessage();
            try
            {
                Thread.sleep(EntityConstants.MOVE_UPDATE_TIME);
            }
            catch(InterruptedException ex)
            {
                Logger.getLogger(EntityMoverThread.class.getName()).log(Level.SEVERE, null, ex);
                keepRunning = false;
            }
        }
    }

    private void sendAggregateUpdateMessage()
    {
        // TODO send a message with all the entities' locations.
        // Since this will be sent many times per second, we will probably
        // want to create an entire new UDP socket on both the client and
        // server so we can minimize lost time (due to serialization, etc.).
        // Sending raw UDP packets would also be better because UDP is
        // faster than TCP (sockets don't need to receive a reply before
        // they send the next packet.)
    }

    public void kill()
    {
        keepRunning = false;
    }
}