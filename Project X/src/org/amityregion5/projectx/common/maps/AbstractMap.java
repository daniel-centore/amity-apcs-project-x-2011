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
import org.amityregion5.projectx.common.entities.Entity;


/**
 * An abstract map.
 * 
 * @author Mike DiBuduo
 * @author Daniel Centore
 * @author Michael Wenke
 */
public abstract class AbstractMap {

    private Image bgImage; // the image of the actual scenery/map/whatever
    private Rectangle playArea;
    private ArrayList<Point> playSpawn;

    /**
     * @return A list of all permanent entities (ie Area)
     */
    public abstract List<Entity> getEntities();
    
    /**
     * @return The background image (or null for default background)
     */

    public Image getBackground()
    {
        return bgImage;
    }
    
    public void setPlayArea(Rectangle rect)
    {
        playArea = rect;
    }
    
    public Rectangle getplayArea()
    {
        return playArea;
    }
    
    public void setPlaySpawn()
    {
        
    }
    
    public ArrayList<Point> getPlaySpawn()
    {
        return playSpawn;
    }

}
