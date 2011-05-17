/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.common.entities;

/**
 * Any entity that can be damaged and destroyed.
 *
 * @author Michael Wenke
 */
public interface Damageable {
    public void damage(int damage);
    public void heal(int health);
    public void killed();
}
