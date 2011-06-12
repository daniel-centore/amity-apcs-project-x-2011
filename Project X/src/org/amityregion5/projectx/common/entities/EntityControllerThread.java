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
package org.amityregion5.projectx.common.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.common.entities.items.field.Block;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.tools.CollisionDetection;

/**
 * A thread that will move all Entities by need.
 * 
 * @author Joe Stein
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class EntityControllerThread extends Thread {

    private boolean keepRunning = true; // keep moving entities
    private AbstractMap map; // used for collision detection
    private boolean alive = true; // used to make sure game over isn't sent 2x
    private EntityList entities;

    public EntityControllerThread(AbstractMap m)
    {
        map = m;
        entities = new EntityList();
    }

    @Override
    public void run()
    {
        while (keepRunning)
        {
            final List<Entity> toRemove = new ArrayList<Entity>();
            for (final Entity e : entities)
            {
                double r = e.getMoveSpeed();
                double offsetX = 0;
                double offsetY = 0;
                if (r > 0)
                {
                    double theta = e.getDirectionMoving();

                    offsetX = r * Math.cos(Math.toRadians(theta));
                    offsetY = r * Math.sin(Math.toRadians(theta));
                    double newX = offsetX + e.getX();
                    double newY = offsetY + e.getY();

                    boolean collision = false;
                    for (Entity q : entities)
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

                    for (Entity q : entities)
                    {
                        if (q instanceof Block && q != e)
                        {
                            if (CollisionDetection.hasCollision(e, (int) offsetX, (int) offsetY, q))
                            {
                                if (e instanceof SuicideBomber)
                                {
                                    ((Block) q).damage(((SuicideBomber) e).getCurrWeapon().getDamage());
                                    if (((Damageable) q).killed())
                                        toRemove.add(q);
                                    else
                                        q.requestUpdate();
                                    toRemove.add(e);

                                    // TODO: explosion sound
                                } else
                                {
                                    Damageable dam = (Damageable) q;

                                    dam.damage(en.getCurrWeapon().getDamage());

                                    if (dam.killed())
                                        toRemove.add(q);
                                    else
                                        q.requestUpdate();
                                }
                            }
                        }
                    }

                    if (CollisionDetection.hasCollision(en, 0, 0, map.getArea()))
                    {
                        if (en instanceof SuicideBomber)
                        {
                            SuicideBomber sb = (SuicideBomber) en;
                            map.getArea().damage(sb.getCurrWeapon().getDamage()); // attack the area specifically
                            toRemove.add(sb);

                            // TODO: explosion sound
                        } else
                        {
                            // System.out.println("COLLISION WITH: "+en);
                            int relY = (int) map.getPlayArea().getCenterY() - e.getCenterY();
                            int relX = (int) map.getPlayArea().getCenterX() - e.getCenterX();
                            int dir = (int) Math.toDegrees(Math.atan2(relY, relX));
                            e.setDirectionMoving(dir);
                            e.setDirectionFacing(dir);
                            en.stop();
                            map.getArea().damage(en.getCurrWeapon().getDamage());
                            if (map.getArea().killed() && alive)
                            {
                                // game over!
                                // TODO: fix game ending (make it server side somehow)
                                
                                // alive = false;
                                // gameController.getServer().endGame();
                                // gameController.kill();
                                // keepRunning = false;
                                // kill();
                                // System.out.println("Game over");
                            }
                        }
                    }
                }
            }

            for (Entity ent : toRemove)
                entities.requestRemove(ent);

            // rawServer.sendAggregateEntityUpdateMessage();
            try
            {
                Thread.sleep(EntityConstants.MOVE_UPDATE_TIME);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(EntityControllerThread.class.getName()).log(Level.SEVERE, null, ex);
                keepRunning = false;
            }
        }
    }

    public void addEntity(Entity e)
    {
        entities.add(e);
    }

    /**
     * @return A list of current entities
     */
    public EntityList getEntities()
    {
        return entities;
    }

    public void removeEntity(Entity e)
    {
        entities.requestRemove(e);
    }

    /**
     * Kills the mover thread :-(
     */
    public void kill()
    {
        keepRunning = false;
    }
}
