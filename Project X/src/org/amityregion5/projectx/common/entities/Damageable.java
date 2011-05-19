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
     */
    public void damage(int damage);

    /**
     * Heals the enemy
     * @param health Amount to heal
     */
    public void heal(int health);

    /**
     * Kills the entity
     */
    public void killed();
}
