/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.common;

import java.awt.Point;

/**
 *
 * @author Joe Stein
 */
public abstract class Entity
{

   private int direction;
   private Point location;
   private int moveSpeed;

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
