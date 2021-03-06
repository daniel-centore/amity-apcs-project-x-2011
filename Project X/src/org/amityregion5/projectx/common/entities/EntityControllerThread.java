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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.common.entities.items.field.Block;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.tools.CollisionDetection;
import org.amityregion5.projectx.server.Server;

/**
 * A thread that will move all Entities by need.
 * 
 * @author Joe Stein
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class EntityControllerThread extends Thread {

    private volatile boolean keepRunning = true; // keep moving entities
    private AbstractMap map; // used for collision detection
    private EntityList entities;
    private boolean isOnServer; // are we running on the server
    private Server server;

    public EntityControllerThread(AbstractMap m, boolean server)
    {
        this.isOnServer = server;
        map = m;
        entities = new EntityList();
    }

    public EntityControllerThread(AbstractMap m, Server server)
    {
        this(m, true);

        this.server = server;
    }

    @Override
    public void run()
    {
        while(keepRunning)
        {
            for(final Entity e : entities)
            {
                double r = e.getMoveSpeed();

                if(r > 0 || e instanceof Enemy)
                {
                    double theta = e.getDirectionMoving();

                    double offsetX = r * Math.cos(Math.toRadians(theta));
                    double offsetY = r * Math.sin(Math.toRadians(theta));
                    double newX = offsetX + e.getX();
                    double newY = offsetY + e.getY();

                    boolean collision = false;
                    for(Entity q : entities)
                    {
                        if(q instanceof Block && q != e)
                            if(CollisionDetection.hasCollision(e, (int) offsetX, (int) offsetY, q))
                            {
                                collision = true;

                                if(e instanceof Enemy)
                                {
                                    processEnemyCollision((Enemy) e, (Block) q);
                                }
                            }
                    }

                    if(e instanceof PlayerEntity)
                    {
                        Rectangle thb = e.getHitBox();
                        thb.setLocation((int) newX, (int) newY);

                        // AN, player base
                        if(map.getPlayArea().contains(thb))
                        {
                            e.setX(newX);
                            e.setY(newY);
                        }
                    }
                    else if(!collision)
                    {
                        e.setX(newX);
                        e.setY(newY);
                    }
                }

                if(e instanceof Enemy && CollisionDetection.hasCollision(e, 0, 0, map.getArea()))
                {
                    Enemy en = (Enemy) e;
                    if(en instanceof SuicideBomber)
                    {
                        SuicideBomber sb = (SuicideBomber) en;
                        map.getArea().damage(sb.getCurrWeapon().getDamage()); // attack the area specifically
                        removeEntity(sb);
                    }
                    else
                    {
                        int relY = (int) map.getPlayArea().getCenterY() - e.getCenterY();
                        int relX = (int) map.getPlayArea().getCenterX() - e.getCenterX();
                        int dir = (int) Math.toDegrees(Math.atan2(relY, relX));
                        e.setDirectionMoving(dir);
                        e.setDirectionFacing(dir);
                        en.stop();
                        map.getArea().damage(en.getCurrWeapon().getDamage());
                    }
                    if(map.getArea().killed() && keepRunning && isOnServer)
                    {
                        // game over!
                        keepRunning = false;
                        server.endGame();
                    }
                }
            }
            try
            {
                Thread.sleep(EntityConstants.MOVE_UPDATE_TIME);
            }
            catch(InterruptedException ex)
            {
                Logger.getLogger(EntityControllerThread.class.getName()).log(Level.SEVERE, null, ex);
                keepRunning = false;
            }
        }
    }

    private void processEnemyCollision(Enemy e, Block q) // helper
    {
        if(e instanceof SuicideBomber)
        {
            q.damage(((SuicideBomber) e).getCurrWeapon().getDamage());
            if(q.killed())
                removeEntity(q);
            else
                q.requestUpdate();

            removeEntity(e);
        }
        else
        {
            Damageable dam = (Damageable) q;

            dam.damage(e.getCurrWeapon().getDamage());

            if(dam.killed())
                removeEntity(q);
            else
                q.requestUpdate();
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
        if(!isOnServer)
            return;

        entities.requestRemove(e);
    }

    public void reallyRemoveEntity(Long l)
    {
        entities.reallyRemoveEntity(l);
    }

    /**
     * Gets an entity from this EntityHandler's list.
     * 
     * @param uniqueId the unique id of the entity to get
     * @return the entity, or null if unique id was not matched
     */
    public Entity getEntity(long uniqueId)
    {
        return entities.getEntity(uniqueId);
    }

    /**
     * Kills the mover thread :-(
     */
    public void kill()
    {
        keepRunning = false;
    }
}
