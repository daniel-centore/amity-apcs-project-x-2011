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
 * Constants used for Entities.
 * 
 * @author Mike DiBuduo
 */
public class EntityConstants {

    public final int NORTH = 0;
    public final int EAST = 90;
    public final int SOUTH = 180;
    public final int WEST = 270;
    public final int NORTH_EAST = 45;
    public final int SOUTH_EAST = 135;
    public final int SOUTH_WEST = 225;
    public final int NORTH_WEST = 315;
    /**
     * Number of milliseconds between moves. Move speed is number of linear
     * pixels per this number of milliseconds.
     */
    public static final int MOVE_UPDATE_TIME = 50;
    public static final int DIR_UPDATE_TIME = 50;
}
