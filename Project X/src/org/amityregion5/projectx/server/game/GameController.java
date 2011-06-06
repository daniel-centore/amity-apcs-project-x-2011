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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.AddMeMessage;
import org.amityregion5.projectx.common.communication.messages.AddWeaponMessage;
import org.amityregion5.projectx.common.communication.messages.RemoveEntityMessage;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.CharacterEntity;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.items.held.Pistol;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.maps.TestingMap;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.communication.Client;
import org.amityregion5.projectx.server.game.enemies.EnemyManager;

/**
 * Handles the game running.
 * 
 * @author Daniel Centore
 * @author Michael Wenke
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class GameController {
    
    private static GameController instance;

    private List<PlayerEntity> players; // List of current Players (do we even need this..?)
    private Collection<Client> clients; // List of current Clients
    private List<Entity> entities; // List of current Entities
    private EntityMoverThread entityMoverThread; // will be in charge of moving entities
    private Server server; // Our server

    // TODO: Send the map to the client, which will then use it!
    private AbstractMap map; // Our map
    private final EnemyManager enemyManager;

    /**
     * Creates and initializes the game controlling
     * 
     * @param server The Server we are based from
     */
    public GameController(Server server)
    {
        map = new TestingMap();
        this.server = server;
        players = new ArrayList<PlayerEntity>();
        clients = server.getClients().values();
        entities = new ArrayList<Entity>();

        Random r = new Random();
        for (Client c : clients)
        {
            PlayerEntity p = new PlayerEntity(0, 0);
            int spawnY = (int) (map.getPlayArea().getY() + r.nextInt((int) map.getPlayArea().getHeight() - p.getHeight()));
            int spawnX = (int) (map.getPlayArea().getX() + r.nextInt((int) map.getPlayArea().getWidth() - p.getWidth()));
            p.setLocation(new Point2D.Double(spawnX, spawnY));

            players.add(p);

            entities.add(p);
            c.setPlayer(p);
            c.send(new AddMeMessage(p));
        }

        for (Client c : clients)
        {
            for (PlayerEntity p : players)
            {
                c.send(new AddEntityMessage(p));
            }
        }

        for (PlayerEntity p : players)
        {
            addWeapon(p, new Pistol());
        }

        entityMoverThread = new EntityMoverThread(this, server.getRawServer(), map);
        entityMoverThread.start();
        enemyManager = new EnemyManager(this, getEnemySpawns());
        enemyManager.startSpawning();
        
        instance = this;
    }

    /**
     * Adds an entity and notifies the client to add it as well
     * @param e Entity to add
     */
    public void addEntity(Entity e)
    {
        synchronized (this)
        {
            entities.add(e);
        }

        server.relayMessage(new AddEntityMessage(e));
    }

    /**
     * @return A list of current entities
     */
    public List<Entity> getEntities()
    {
        return entities;
    }

    /**
     * @return Current Sevrer
     */
    public Server getServer()
    {
        return server;
    }

    /**
     * @return All running Clients
     */
    public Collection<Client> getClients()
    {
        return clients;
    }

    /**
     * Adds a weapon to the given server-side character and relays a message to update the client-side characters.
     * 
     * @param c the character to which to add the weapon
     * @param w the weapon to add
     */
    public final void addWeapon(CharacterEntity c, Weapon w)
    {
        c.addWeapon(w);
        server.relayMessage(new AddWeaponMessage(c.getUniqueID(), w));
    }

    /**
     * This assumes the default game window dimensions and that enemies spawn at edge of screen.
     * 
     * @return enemy spawn points
     */
    public ArrayList<Point> getEnemySpawns()
    {
        ArrayList<Point> spawns = new ArrayList<Point>();
        final int MARGIN = 20;
        if (map instanceof TestingMap)
        {
            for (int i = -MARGIN; i < GameWindow.GAME_HEIGHT + MARGIN; i += 10)
            {
                spawns.add(new Point(-MARGIN, i));
                spawns.add(new Point(GameWindow.GAME_WIDTH + MARGIN, i += 10));
            }
            for (int i = -MARGIN; i < GameWindow.GAME_WIDTH + MARGIN; i += 10)
            {
                spawns.add(new Point(i, -MARGIN));
                spawns.add(new Point(i, GameWindow.GAME_HEIGHT + MARGIN));
            }
        }

        return spawns;
    }

    /**
     * Tells this controller that the player has fired.
     * 
     * @param player the player that fired
     */
    public void playerFired(PlayerEntity player)
    {
        int direction = player.getDirectionFacing();
        Weapon wep = player.getCurrWeapon();
        
        int range = wep.getRange();
        
        int x2 = (int) (Math.cos(Math.toRadians(direction)) * range) + player.getCenterX();
        int y2 = (int) (Math.sin(Math.toRadians(direction)) * range) + player.getCenterY();
        
        Line2D.Double line = new Line2D.Double(player.getCenterX(), player.getCenterY(), x2, y2);

        List<Entity> toRemove = new ArrayList<Entity>();
        synchronized (this)
        {
            double closest = Double.MAX_VALUE;
            Enemy closestEn = null;
            for (Entity e : entities)
            {
                if (e instanceof Enemy && line.intersects(e.getHitBox()))
                {
                    double dist = e.getLocation().distance(new Point(player.getCenterX(), player.getCenterY()));
                    if (dist < closest)
                    {
                        closestEn = (Enemy) e;
                    }
                }
            }
            if (closestEn != null)
            {
                closestEn.damage(player.getCurrWeapon().getDamage());
                closestEn.requestUpdate();
                if (closestEn.killed())
                {
                    toRemove.add(closestEn);
                    server.relayMessage(new RemoveEntityMessage(closestEn));
                }
            }

            entities.removeAll(toRemove);
        }

    }

    /**
     * @return The current map
     */
    public AbstractMap getMap()
    {
        return map;
    }

    /**
     * Removes an entity from the array and notifies the clients to remove it as well
     * @param e Entity to remove
     */
    public void removeEntity(Entity e)
    {
        synchronized (this)
        {
            entities.remove(e);
        }
        getServer().relayMessage(new RemoveEntityMessage(e));
    }

    public static GameController getInstance()
    {
        return instance;
    }
}
