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

import org.amityregion5.projectx.common.entities.items.held.Weapon;

/**
 * A message to notify the client that a weapon has been added to the Entity
 * with the given uniqueID.
 *
 * @author Joe Stein
 * @author Daniel Centore
 */
public class AddWeaponMessage extends Message {
    
    private static final long serialVersionUID = 1L;
    
    private Weapon weapon;
    private long uniqueID; // the id of the person whose weapon to add
//    private String wepSpriteLoc;

    /**
     * 
     * @param id Player's unique ID
     * @param w Weapon to add
     */
    public AddWeaponMessage(long id, Weapon w)
    {
        uniqueID = id;
        weapon = w;
//        wepSpriteLoc = w.getDefaultImage();
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    public long getID()
    {
        return uniqueID;
    }

//    public String getSpriteLoc()
//    {
//        return wepSpriteLoc;
//    }

}
