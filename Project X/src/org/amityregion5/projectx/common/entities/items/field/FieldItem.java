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
 * @author Mike DiBuduo
 */
package org.amityregion5.projectx.common.entities.items.field;

import org.amityregion5.projectx.common.entities.items.Item;

/**
 * Items that are placed on or pass through field.
 *
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public abstract class FieldItem extends Item {

    public FieldItem(int x, int y)
    {
        super(x, y);
    }
}
