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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.amityregion5.projectx.common.entities.Entity;
import org.amityregion5.projectx.common.entities.items.field.Area;
import org.amityregion5.projectx.common.entities.items.field.Wall;

/**
 * Testing map
 * 
 * @author Daniel Centore
 * @author Joseph Stein
 *
 */
public class TestingMap extends AbstractMap {
    
    private Image image;
    private final List<Entity> entities;
    
    public TestingMap()
    {
        entities = new ArrayList<Entity>();

        entities.add(new Area(0, 0));
        entities.add(new Wall());

        try
        {
            image = ImageIO.read(new File("resources/maps/Map.png"));
        }
        catch(IOException ex)
        {  
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
}
