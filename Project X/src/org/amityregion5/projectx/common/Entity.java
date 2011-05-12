/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.amityregion5.projectx.common;

import java.awt.Point;

/**
 *
 * @author joe
 */
public class Entity
{
   private int direction;
   private Point location;
   private int moveSpeed;

   private String name;

   public void test()
   {
      throw new RuntimeException("Something in it");
   }

   public int getDirection()
   {
      return direction;
   }

   public void setDirection(int direction)
   {
      this.direction = direction;
   }

   public Point getLocation()
   {
      return location;
   }

   public void setLocation(Point location)
   {
      this.location = location;
   }

   public int getMoveSpeed()
   {
      return moveSpeed;
   }

   public void setMoveSpeed(int moveSpeed)
   {
      this.moveSpeed = moveSpeed;
   }
}
