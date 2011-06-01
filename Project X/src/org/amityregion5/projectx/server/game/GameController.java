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

import org.amityregion5.projectx.common.communication.messages.AddWeaponMessage;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.amityregion5.projectx.client.gui.GameWindow;

import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.AddMeMessage;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.common.entities.items.held.Gun;
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
            p.setHitBox(p.getWidth(), p.getHeight());
            Gun g = new Gun(100, 100, 10, 2, 6, 50);
            players.add(p);
            entities.add(p);
            c.setPlayer(p);

            c.send(new AddMeMessage(p));
            c.send(new AddWeaponMessage(p.getUniqueID(),g));
        }

        for (Client c : clients)
        {
            for (Player p : players)
                c.send(new AddEntityMessage(p));
        }

        enemyManager = new EnemyManager(getEnemySpawns());
        entityMoverThread = new EntityMoverThread(this, server.getRawServer(), map);
        entityMoverThread.start();
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
     * This assumes the default game window dimensions and that enemies spawn at edge of screen.
     * @return enemy spawn points
     */
    private ArrayList<Point> getEnemySpawns()
    {
        ArrayList<Point> spawns = new ArrayList<Point>();
        if(map instanceof TestingMap)
        {
            for(int i = 0; i < GameWindow.GAME_WIDTH; i += 10)
            {
                spawns.add(new Point(0,i));
                spawns.add(new Point(GameWindow.GAME_HEIGHT, i));
            }
            for(int i = 0; i < GameWindow.GAME_HEIGHT; i += 10)
            {
             spawns.add(new Point(i,0));
                spawns.add(new Point(i,GameWindow.GAME_WIDTH));
            }
        }

        return spawns;
    }

}
