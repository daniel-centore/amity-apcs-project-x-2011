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
import org.amityregion5.projectx.common.entities.characters.Character;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.items.held.Gun;
import org.amityregion5.projectx.common.entities.items.held.Weapon;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.common.maps.TestingMap;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.communication.Client;
import org.amityregion5.projectx.server.game.oldSpawning.EnemyManager;
import org.amityregion5.projectx.server.game.spawning.EnemySpawning;

/**
 * Handles the game running.
 * 
 * @author Daniel Centore
 * @author Michael Wenke
 * @author Joe Stein
 */
public class GameController {

    private List<Player> players; // List of current Players (do we even need this..?)
    private Collection<Client> clients; // List of current Clients
    private List<Entity> entities;
    private EntityMoverThread entityMoverThread; // will be in charge of moving entities
    private Server server;
    private AbstractMap map;
    private EnemyManager enemyManager;

    /**
     * Creates and initializes the game controlling
     * 
     * @param server The Server we are based from
     */
    public GameController(Server server)
    {
        map = new TestingMap();
        this.server = server;
        players = new ArrayList<Player>();
        clients = server.getClients().values();
        entities = new ArrayList<Entity>();

        Random r = new Random();
        for (Client c : clients)
        {
            Player p = new Player(0, 0);
            int spawnY = (int) (map.getPlayArea().getY() + r.nextInt((int) map.getPlayArea().getHeight() - p.getHeight()));
            int spawnX = (int) (map.getPlayArea().getX() + r.nextInt((int) map.getPlayArea().getWidth() - p.getWidth()));
            p.setLocation(new Point2D.Double(spawnX, spawnY));
            // p.setHitBox(p.getWidth(), p.getHeight());

            players.add(p);

            entities.add(p);
            c.setPlayer(p);
            c.send(new AddMeMessage(p));

            // Enemy enemy = new Enemy(200, 500, 500);
            // c.send(new AddEntityMessage(enemy));
            // entities.add(enemy);
        }

        for (Client c : clients)
        {
            for (Player p : players)
            {
                c.send(new AddEntityMessage(p));
            }
        }

        for (Player p : players)
        {
            addWeapon(p, new Gun(100, 100, 10, 20, 6, 50, 10));
        }

        // enemyManager = new EnemyManager(this, getEnemySpawns());
        entityMoverThread = new EntityMoverThread(this, server.getRawServer(), map);
        entityMoverThread.start();
        // enemyManager.startSpawning();

        new EnemySpawning(server, this);
    }

    public void addEntity(Entity e)
    {
        synchronized (this)
        {
            entities.add(e);
        }

        server.relayMessage(new AddEntityMessage(e));
    }

    public List<Entity> getEntities()
    {
        return entities;
    }

    public Server getServer()
    {
        return server;
    }

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
    public final void addWeapon(Character c, Weapon w)
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
        if (map instanceof TestingMap)
        {

            for (int i = 0; i < GameWindow.GAME_WIDTH; i += 10)
            {
                spawns.add(new Point(0, i));
                spawns.add(new Point(GameWindow.GAME_HEIGHT, i += 10));
            }
            for (int i = 0; i < GameWindow.GAME_HEIGHT; i += 10)
            {
                spawns.add(new Point(i, 0));
                spawns.add(new Point(i, GameWindow.GAME_WIDTH));
            }
        }

        return spawns;
    }

    /**
     * Tells this controller that the player has fired.
     * 
     * @param player the player that fired
     */
    public void playerFired(Player player)
    {
        int direction = player.getDirectionFacing();
        int x2 = (int) (Math.cos(Math.toRadians(direction)) * 1500) + player.getCenterX();
        int y2 = (int) (Math.sin(Math.toRadians(direction)) * 1500) + player.getCenterY();
        Line2D.Double line = new Line2D.Double(player.getCenterX(), player.getCenterY(), x2, y2);

        List<Entity> toRemove = new ArrayList<Entity>();
        synchronized (this)
        {
            for (Entity e : entities)
            {
                if (e instanceof Enemy && line.intersects(e.getHitBox()))
                {
                    Enemy en = (Enemy) e;
                    en.damage(player.getWeapon(player.getCurrWeapon()).getDamage());
                    if (en.killed())
                    {
//                        System.out.println("Killed");
                        toRemove.add(en);
                        server.relayMessage(new RemoveEntityMessage(en));
                    }
                }
            }
        }
        
        entities.removeAll(toRemove);
    }
}
