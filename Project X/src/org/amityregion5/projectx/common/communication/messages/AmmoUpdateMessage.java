/**
 * Copyright (c) 2011 Amity AP CS A Students of 2010-2011.
 *
 * ex: set filetype=java expandtab tabstop=4 shiftwidth=4 :
 * * This program is free software: you can redistribute it and/or
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
package org.amityregion5.projectx.common.communication.messages;

/**
 * Indicates an update (usu. addition) in ammo.
 *
 * @author Joe Stein
 */
public class AmmoUpdateMessage extends Message {
    private static final long serialVersionUID = 603L;

    private long id;
    private int wepId;
    private int ammo;

    /**
     * Creates a new ammo update message.
     * @param uniqueID the unique id of the PLAYER (not the weapon!)
     * @param ammo the amount of ammo the weapon now has
     */
    public AmmoUpdateMessage(long uniqueID, int wep, int ammo)
    {
        id = uniqueID;
        wepId = wep;
        this.ammo = ammo;
    }

    public long getID()
    {
        return id;
    }

    public int getWepID()
    {
        return wepId;
    }

    public int getAmmo()
    {
        return ammo;
    }

}
