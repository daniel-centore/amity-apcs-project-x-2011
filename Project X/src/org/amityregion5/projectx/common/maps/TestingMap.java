/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 *
 * This program is free software: you can redistribute it and/or
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
package org.amityregion5.projectx.common.maps;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.field.Area;
import org.amityregion5.projectx.common.entities.items.field.Wall;

import org.amityregion5.projectx.common.tools.ImageHandler;
import org.amityregion5.projectx.server.Server;

/**
 * Testing map
 * 
 * @author Daniel Centore
 * @author Michael Wenke
 * @author Joseph Stein
 * 
 */
public class TestingMap extends AbstractMap {

    private Image image;
    // I did some math, and this spawn area should be a 200x200 square in the center of a 1024x768 window
    final private Rectangle DEFAULT_PLAY_SPAWN = new Rectangle(412, 284, 200, 200);

    private final List<Entity> entities;

    public TestingMap()
    {

        setPlayArea(DEFAULT_PLAY_SPAWN);
        setPlaySpawns(createPlaySpawns());
        setEnemySpawns(createEnemySpawns());

        entities = new ArrayList<Entity>();

        Area a = new Area(0, 0);
        int x = GameWindow.GAME_WIDTH / 2 - a.getWidth() / 2;
        int y = GameWindow.GAME_HEIGHT / 2 - a.getHeight() / 2;
        
        a.setX(x);
        a.setY(y);

        entities.add(a);
        entities.add(new Wall(a));

        try
        {
            image = ImageHandler.loadImage("maps/TestMap");
        } catch (RuntimeException ex)
        {
            Logger.getLogger(TestingMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return random points within the playing area where the players spawn
     */
    private ArrayList<Point> createPlaySpawns()
    {
        ArrayList<Point> spawns = new ArrayList<Point>();
        for(int i = 0; i < Server.MAX_PLAYERS; i++) //Would be better if we accessed the exact number of players instead
        {
            int x = (int)getPlayArea().getX() + (int)(Math.random() * getPlayArea().getWidth());
            int y = (int)getPlayArea().getY() + (int)(Math.random() * getPlayArea().getHeight());
            spawns.add(new Point(x,y));
        }
        return spawns;
    }

    /**
     *
     * @return all points on the edge of the window where enemies can spawn (not sorted)
     */
    private ArrayList<Point> createEnemySpawns()
    {
        ArrayList<Point> enemySpawns = new ArrayList<Point>();
        for(int i = 0; i < GameWindow.GAME_HEIGHT; i++)
        {
            enemySpawns.add(new Point(0,i));
            enemySpawns.add(new Point(GameWindow.GAME_WIDTH,i));
        }
        for(int i = 0; i < GameWindow.GAME_WIDTH; i++)
        {
            enemySpawns.add(new Point(i,0));
            enemySpawns.add(new Point(i, GameWindow.GAME_HEIGHT));
        }
        return enemySpawns;

    }

    @Override
    public Image getBackground()
    {
        return image;
    }

    @Override
    public List<Entity> getEntities()
    {
        return entities;
    }
}
