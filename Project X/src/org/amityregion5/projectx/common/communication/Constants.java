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
package org.amityregion5.projectx.common.communication;

/**
 * Constants for use in communication.
 * 
 * @author Daniel Centore
 * @author Michael Zuo
 */
public class Constants {
    
    //TCP/IP Stuff
    public static final int PORT = 27182;
    public static final int RAW_PORT = 27183;
    public static final int TIMEOUT = 3000;
    
    //Multicast Stuff
    public static final int UDPORT = 4567;
    public static final String UDPGROUP = "230.0.0.1";
    public static final int MULTICAST_INTERVAL = 2000;
    
    
}
