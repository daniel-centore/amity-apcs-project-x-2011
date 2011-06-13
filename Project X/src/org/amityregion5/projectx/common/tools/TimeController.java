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
package org.amityregion5.projectx.common.tools;

/**
 * Controls game timing so we can sync up events server and client side
 * then do some verification shit
 * 
 * @author Daniel Centore
 *
 */
public class TimeController {
    
    private long ms; // time this controller was initialized
    
    /**
     * Creates a {@link TimeController} based on the current time
     */
    public TimeController()
    {
        ms = System.currentTimeMillis();
    }
    
    /**
     * @return The amount of time that has elapsed since this controller was initialized
     */
    public long getTimeElapsed()
    {
        return System.currentTimeMillis() - ms;
    }
    
    /**
     * Finds the amount of time that has elapsed since ms
     * @param ms A very nice time
     * @return The difference
     */
    public long getDifference(long ms)
    {
        return Math.abs(ms - getTimeElapsed());
    }
    
}
