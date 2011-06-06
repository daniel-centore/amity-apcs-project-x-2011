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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.client.gui.GameWindow;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.field.Area;
import org.amityregion5.projectx.common.entities.items.field.Wall;
import org.amityregion5.projectx.common.tools.ImageHandler;

/**
 * Testing map
 * 
 * @author Daniel Centore
 * @author Michael Wenke
 * @author Joseph Stein
 * 
 */
public class TestingMap extends AbstractMap
{

    private Image image;
    private Area area;
    private final List<Entity> entities;

    public TestingMap()
    {
        entities = new ArrayList<Entity>();

        Area a = new Area(0, 0);
        area = a;
        int x = GameWindow.GAME_WIDTH / 2 - a.getWidth() / 2;
        int y = GameWindow.GAME_HEIGHT / 2 - a.getHeight() / 2;

        a.setX(x);
        a.setY(y);

        setPlayArea(new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight()));


        entities.add(a);
//        entities.add(wall = new Wall(a));

        try {
            image = ImageHandler.loadImage("maps/TestMap");
        } catch (RuntimeException ex) {
            Logger.getLogger(TestingMap.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Point getCenter()
    {

        return new Point(GameWindow.GAME_WIDTH / 2, GameWindow.GAME_HEIGHT / 2);
    }

    @Override
    public Area getArea()
    {
        return area;
    }

    
    
}
