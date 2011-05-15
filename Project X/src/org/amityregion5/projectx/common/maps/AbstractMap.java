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

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import org.amityregion5.projectx.client.gui.Gui;
import org.amityregion5.projectx.common.entities.Entity;

/**
 * An abstract map
 * 
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public abstract class AbstractMap {

    private ArrayList<Entity> entities; // list of entities on the map
    private Image bgImage; // the image of the actual scenery/map/whatever

    /**
     * @return Map entities
     */
    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

    /**
     * Adds an entity to the map
     * 
     * @param e
     */
    public synchronized void addEntity(Entity e)
    {
        entities.add(e);
    }

    /**
     * Removes an entity from the map
     * 
     * @param e
     */
    public synchronized void removeEntity(Entity e)
    {
        entities.remove(e);
    }

    /**
     * @return The background image (or null for default background)
     */
    public Image getBackground()
    {
        return bgImage;
    }
}
