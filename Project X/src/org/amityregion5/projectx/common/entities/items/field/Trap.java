/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities.items.field;

import org.amityregion5.projectx.common.entities.items.DamageDealing;

/**
 *Not sure what kind of traps we're going to have yet.
 * Traps could deal direct damage, damage over time, slow down enemies, etc.
 *
 * @author Michael Wenke
 */
public class Trap extends FieldItem implements DamageDealing{
   private int damage;
   private boolean active;

   public Trap(int dm)
   {
       damage = dm;
       active = false;
   }

    public int getDamage() {
        return damage;
    }
    
    public boolean isActivated()
    {
        return active;
    }



}
