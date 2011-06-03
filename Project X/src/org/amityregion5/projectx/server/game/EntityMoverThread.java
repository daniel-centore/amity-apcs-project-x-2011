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

import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.server.communication.RawServer;

/**
 * A thread that will move all Entities by need. TODO handles collision detection
 * 
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class EntityMoverThread extends Thread {
    private boolean keepRunning = true; // keep moving entities
    private GameController gameController; // used for getting entities
    private RawServer rawServer; // used for communications e.g. loc updates
    private AbstractMap map; // used for collision detection

    public EntityMoverThread(GameController gc, RawServer rs, AbstractMap m)
    {
        gameController = gc;
        rawServer = rs;
        map = m;
    }

    @Override
    public void run()
    {
        while (keepRunning)
        {
            synchronized (gameController)
            {
                for (Entity e : gameController.getEntities())
                {
                    double r = e.getMoveSpeed();
                    if (r > 0)
                    {
                        // TODO make sure this Entity doesn't collide with
                        // anything.
                        double theta = e.getDirectionMoving();
                        double newX = r * Math.cos(Math.toRadians(theta)) + e.getX();
                        double newY = r * Math.sin(Math.toRadians(theta)) + e.getY();
                        if (e instanceof Player)
                        {
                            // FIXME this may take too much time/memory
                            Rectangle thb = e.getHitBox();
                            thb.setLocation((int) newX, (int) newY);
                            // TODO if the play area doesn't contain the new
                            // hit box (thb), move the player to the edge of the
                            // play area. Keep in mind the player's direction.
                            if (map.getPlayArea().contains(thb))
                            {
                                e.setX(newX);
                                e.setY(newY);
                            }
                        } else
                        {
                            e.setX(newX);
                            e.setY(newY);
                        }
                    }
                    if (e instanceof Enemy)
                    {
                        Enemy en = (Enemy) e;
                        if (en.getHitBox().intersects(map.getPlayArea()) || en.hasHit())
                        {
                            int relY = (int) map.getPlayArea().getCenterY() - e.getCenterY();
                            int relX = (int) map.getPlayArea().getCenterX() - e.getCenterX();
                            int dir = (int) Math.toDegrees(Math.atan2(relY, relX));
                            e.setDirectionMoving(dir);
                            e.setDirectionFacing(dir);
                            System.out.println("enemy hit center");
                            en.stop();
                            en.hit();
                            map.getArea().damage(en.getWeapon(en.getCurrWeapon()).getDamage());
                            if(map.getArea().killed())
                            {
                                JOptionPane.showMessageDialog(null, "The enemies have taken over!", "Game Over", JOptionPane.OK_OPTION);
                                gameController.getServer().kill();
                                keepRunning = false;
                                rawServer.kill();
                                kill();
                            }
                        }
                    }
                }
            }
//            System.out.println("sent");
            sendAggregateUpdateMessage();
            try
            {
                Thread.sleep(EntityConstants.MOVE_UPDATE_TIME);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(EntityMoverThread.class.getName()).log(Level.SEVERE, null, ex);
                keepRunning = false;
            }
        }
    }

    private void sendAggregateUpdateMessage()
    {
        StringBuilder buf = new StringBuilder();

        synchronized (gameController)
        {
            buf.append("-1,");
            buf.append((double) gameController.getMap().getArea().getHp());
            for (Entity e : gameController.getEntities())
            {
                if (e.updateCheck())
                {
                    buf.append(e.getUniqueID());
                    buf.append(",");
                    buf.append(e.getX());
                    buf.append(",");
                    buf.append(e.getY());
                    buf.append(",");
                    buf.append(e.getDirectionFacing());
                    buf.append(",");
                    buf.append(e.getHp());
                    buf.append(";");
                }
            }
        }

        if (buf.length() > 0)
        {
            // trim last semicolon
            buf.deleteCharAt(buf.length() - 1);
            rawServer.send(buf.toString());
        }
    }

    public void kill()
    {
        keepRunning = false;
    }
}
