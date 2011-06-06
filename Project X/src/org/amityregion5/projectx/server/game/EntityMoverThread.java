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

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.entities.Damageable;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.common.entities.items.field.Block;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.server.communication.RawServer;
import org.amityregion5.projectx.server.tools.CollisionDetection;

/**
 * A thread that will move all Entities by need. TODO handles collision detection
 * 
 * @author Joe Stein
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class EntityMoverThread extends Thread {
    private boolean keepRunning = true; // keep moving entities
    private GameController gameController; // used for getting entities
    private RawServer rawServer; // used for communications e.g. loc updates
    private AbstractMap map; // used for collision detection
    private boolean alive = true; // used to make sure game over isn't sent 2x

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
            List<Entity> toRemove = new ArrayList<Entity>();
            synchronized (gameController)
            {
                for (Entity e : gameController.getEntities())
                {
                    double r = e.getMoveSpeed();
                    if (r > 0)
                    {
                        double theta = e.getDirectionMoving();

                        double offsetX = r * Math.cos(Math.toRadians(theta));
                        double offsetY = r * Math.sin(Math.toRadians(theta));
                        double newX = offsetX + e.getX();
                        double newY = offsetY + e.getY();

                        boolean collision = false;
                        for (Entity q : gameController.getEntities())
                        {
                            if (q instanceof Block && q != e)
                                if (CollisionDetection.hasCollision(e, (int) offsetX, (int) offsetY, q))
                                {
                                    collision = true;
                                    break;
                                }
                        }

                        if (e instanceof PlayerEntity)
                        {
                            Rectangle thb = e.getHitBox();
                            thb.setLocation((int) newX, (int) newY);

                            // AN, player base
                            if (map.getPlayArea().contains(thb))
                            {
                                e.setX(newX);
                                e.setY(newY);
                            }
                        } else if (!collision)
                        {
                            e.setX(newX);
                            e.setY(newY);
                        }
                    }
                    if (e instanceof Enemy)
                    {
                        Enemy en = (Enemy) e;
                        
                        Point center = map.getCenter();
                        // makes enemy face center of map and move towards it
                        en.setDirectionFacing((int) en.getDirectionTowards(center));
                        en.setDirectionMoving((int) en.getDirectionTowards(center));

                        for (Entity q : gameController.getEntities())
                        {
                            if (q instanceof Block && q != e)
                                if (CollisionDetection.hasCollision(e, 0, 0, q))
                                {
                                    Damageable dam = (Damageable) q;
                                    dam.damage(en.getCurrWeapon().getDamage());

                                    if (dam.killed())
                                    {
                                        toRemove.add(q);
                                    } else
                                        q.requestUpdate();
                                }
                        }

                        if (CollisionDetection.hasCollision(en, 0, 0, map.getArea()))
                        {
                            if (en instanceof SuicideBomber)
                            {
                                SuicideBomber sb = (SuicideBomber) en;
                                map.getArea().damage(sb.getDamage());
                                toRemove.add(sb);
                            } else
                            {
                                int relY = (int) map.getPlayArea().getCenterY() - e.getCenterY();
                                int relX = (int) map.getPlayArea().getCenterX() - e.getCenterX();
                                int dir = (int) Math.toDegrees(Math.atan2(relY, relX));
                                e.setDirectionMoving(dir);
                                e.setDirectionFacing(dir);
                                en.stop();
                                map.getArea().damage(en.getCurrWeapon().getDamage());
                                if (map.getArea().killed() && alive)
                                {
                                    alive = false;
                                    gameController.getServer().kill();
                                    gameController.kill();
                                    keepRunning = false;
//                                    rawServer.kill();
                                    kill();
                                    System.out.println("Ended game");
                                }
                            }
                        }
                    }
                }
            }

            for (Entity ent : toRemove)
                gameController.removeEntity(ent);

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
            buf.append(gameController.getMap().getArea().getHp());
            buf.append(";");
            for (Entity e : gameController.getEntities())
            {
                if (e.updateCheck())
                {
                    buf.append(e.getUniqueID());
                    buf.append(",");
                    buf.append((int) e.getX());
                    buf.append(",");
                    buf.append((int) e.getY());
                    buf.append(",");
                    buf.append(e.getDirectionFacing());
                    buf.append(",");
                    buf.append(e.getHp());
                    if (e instanceof CharacterEntity)
                    {
                        buf.append(",");
                        buf.append(((CharacterEntity) e).getCurrWeapon());
                    }
                    buf.append(";");
                }
            }
        }

        if (buf.length() > 0)
        {
            buf.deleteCharAt(buf.length() - 1);
            rawServer.send(buf.toString());
        }
    }

    /**
     * Kills the mover thread :-(
     */
    public void kill()
    {
        keepRunning = false;
    }
}
