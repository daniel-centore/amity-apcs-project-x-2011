/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.items.held;

/**
 *
 * @author Joe Stein
 */
public abstract class Weapon extends HeldItem {

   private int range; // Range (in pixels)
   
   public Weapon(int range)
   {
      this.range = range;
   }

   public int getRange()
   {
      return range;
   }

   public void setRange(int range)
   {
      this.range = range;
   }



}
