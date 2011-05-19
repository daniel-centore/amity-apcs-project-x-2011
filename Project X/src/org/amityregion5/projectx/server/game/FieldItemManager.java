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
package org.amityregion5.projectx.server.game;

import java.util.ArrayList;

import org.amityregion5.projectx.common.entities.items.field.FieldItem;

/**
 * Class documentation.
 * 
 * @author Jenny Liu
 * @author Mike DiBuduo
 */
public class FieldItemManager {

    private ArrayList<FieldItem> fieldItems;
    
    private Thread managerThread = new Thread() {

        public void run()
        {
        }
    };
}
