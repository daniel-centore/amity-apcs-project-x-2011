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
 **/
package org.amityregion5.projectx.common.entities.items.field;

import org.amityregion5.projectx.common.entities.items.DamageDealing;

/**
 *Not sure what kind of traps we're going to have yet.
 * Traps could deal direct damage, damage over time, slow down enemies, etc.
 *
 * @author Michael Wenke
 */
public class Trap extends FieldItem implements DamageDealing{
    private static final long serialVersionUID = 1L;
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
