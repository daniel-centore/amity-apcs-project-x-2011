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
package org.amityregion5.projectx.client.tools;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Handles the outline of images.
 * NOTE: Only works (perfectly) for convex sprites. Others should work relatively okay though.
 * 
 * @author Daniel Centore
 *
 */
public class PixelOutline
{
    private boolean[][] outline;

    public PixelOutline(BufferedImage img)
    {
        outline = new boolean[img.getWidth()][img.getHeight()];

        //upper left to bottom left
        for(int i = 0; i < img.getHeight(); i++)
        {
            for(int j = 0; j < img.getWidth(); j++)
            {
                if(!isTransparent(img.getRGB(j, i)))
                {
                    add(new Point(j, i));
                    j = Integer.MAX_VALUE-1;
                }
            }
        }

        //upper right to bottom right
        for(int i = img.getHeight()-1; i >= 0; i--)
        {
            for(int j = img.getWidth()-1; j >= 0; j--)
            {
                if(!isTransparent(img.getRGB(j, i)))
                {
                    add(new Point(j, i));
                    j = -1;
                }
            }
        }

        //upper left to upper right
        for(int i = 0; i < img.getWidth(); i++)
        {
            for(int j = 0; j < img.getHeight(); j++)
            {
                if(!isTransparent(img.getRGB(i, j)))
                {
                    add(new Point(i, j));
                    j = Integer.MAX_VALUE-1;
                }
            }
        }

        //bottom left to bottom right
        for(int i = img.getWidth()-1; i >= 0; i--)
        {
            for(int j = img.getHeight()-1; j >= 0; j--)
            {
                //              System.out.println(i+" "+j);

                if(!isTransparent(img.getRGB(i, j)))
                {
                    add(new Point(i, j));
                    j = -1;
                }
            }
        }

    }

    public boolean[][] getPoints()
    {
        return outline;
    }

    public void add(Point p)
    {
        outline[p.x][p.y] = true;
    }

    private static boolean isTransparent(int color)
    {
        return (((color >> 24) & 0xff) <= CollisionDetector.OPACITY_THRESHOLD);
    }

}