/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
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
 *
 * @author Joe Stein
 */
package org.amityregion5.projectx.common;

import java.awt.Point;

/**
 * Object that can exist on the map.
 */
public abstract class Entity
{

   private int direction;
   private Point location;
   private int moveSpeed;

   /**
    * Gets this Entity's direction.
    * @return direction
    */
   public int getDirection()
   {
      return direction;
   }

   public Point getLocation()
   {
      return location;
   }

   public int getMoveSpeed()
   {
      return moveSpeed;
   }

   public void setLocation(Point location)
   {
      this.location = location;
   }

   public void setMoveSpeed(int moveSpeed)
   {
      this.moveSpeed = moveSpeed;
   }

   public void setDirection(int direction)
   {
      this.direction = direction;
   }
}
