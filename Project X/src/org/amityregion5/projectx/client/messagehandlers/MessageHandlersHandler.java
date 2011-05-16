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
package org.amityregion5.projectx.client.messagehandlers;

import org.amityregion5.projectx.client.communication.CommunicationHandler;

/**
 * Registers all of our message handlers!
 * FIXME can't we just register directly with CommunicationHandler?!
 * this is really convoluted ~ joe
 *
 * @author Daniel Centore
 */
public class MessageHandlersHandler {

    public static void registerHandlers()
    {
        CommunicationHandler ch = CommunicationHandler.getInstance();
        
        ch.registerListener(new EntityMovedHandler());
    }

}
