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
package org.amityregion5.projectx.common.entities.items.held;

import org.amityregion5.projectx.common.tools.Sound;

public class Pistol extends Gun {

    private static final long serialVersionUID = 1L;
    
    public Pistol()
    {
        super(600, -1, -1, 4, -1, -1, 7);
    }

    @Override
    public Sound getSound()
    {
        return Sound.PISTOL_SHOT;
    }
    
    @Override
    public String getDefaultImage()
    {
        return "sprites/Pistol";
    }

    @Override
    public String getName()
    {
        return "Magnum";
    }

}
