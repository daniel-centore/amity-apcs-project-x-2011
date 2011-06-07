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
package org.amityregion5.projectx.common.entities;

/**
 * Any entity that can be damaged and destroyed.
 *
 * @author Michael Wenke
 */
public interface Damageable {

    /**
     * Deals damage to the entity
     * @param damage Damage to apply
     * @return the amount of damage applied
     */
    public int damage(int damage);

    /**
     * Heals the enemy
     * @param health Amount to heal
     */
    public void heal(int health);

    /**
     * Check if the entity is dead
     */
    public boolean killed();

    /**
     * Returns the current HP of this entity.
     * @return the current HP of this entity
     */
    public int getHp();

    /**
     * Returns the maximum HP of this entity, i.e. the HP with which the entity
     * started.
     * @return the max HP of this entity
     */
    public int getMaxHp();

    /**
     * Sets the HP of this entity.
     * @param hp the number of HP this entity will have
     */
    public void setHp(int hp);
}
