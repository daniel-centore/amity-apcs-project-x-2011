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
 * Tells the clients that a player changed their weapon
 *
 * @author Daniel Centore
 */
public class UpdateWeaponMessage extends Message {
    
    private static final long serialVersionUID = 499L;
    
    private int weapon;
    private long playerID;

    /**
     * 
     * @param wep Weapon to set it to
     * @param playerID The player's uniqueID
     */
    public UpdateWeaponMessage(int wep, long playerID)
    {
        weapon = wep;
        this.playerID = playerID;
    }

    public void setWeapon(int weapon)
    {
        this.weapon = weapon;
    }

    public int getWeapon()
    {
        return weapon;
    }

    public long getPlayerID()
    {
        return playerID;
    }
    
}
