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
package org.amityregion5.projectx;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.amityregion5.projectx.client.communication.CommunicationHandler;
import org.amityregion5.projectx.client.gui.LobbyWindow;
import org.amityregion5.projectx.client.preferences.PreferenceManager;
import org.amityregion5.projectx.common.communication.messages.ActivePlayersMessage;
import org.amityregion5.projectx.common.communication.messages.BooleanReplyMessage;
import org.amityregion5.projectx.common.communication.messages.IntroduceMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.controllers.DefaultServerController;
import org.amityregion5.projectx.server.controllers.ServerController;

/**
 * Class documentation.
 *
 * @author Joe Stein
 */
public class Main {
    public static void main(String[] args)
    {
        Server s = new Server("test server");
        ServerController sc = new DefaultServerController();
        s.setController(sc);
        if (PreferenceManager.getUsername() == null)
        {
            PreferenceManager.setUsername("test user");
        }
        final String user = PreferenceManager.getUsername();
        try
        {
            Thread.sleep(500);
            CommunicationHandler ch = new CommunicationHandler("localhost");
            boolean joined = false;
            while (!joined)
            {
                Message reply = ch.requestReply(new IntroduceMessage(user));
                // ActivePlayerUpdate message serves as an affirmative here.
                if (reply instanceof BooleanReplyMessage)
                {
                    System.err.println("username already in use");
                    System.exit(-1);
                } else if (reply instanceof ActivePlayersMessage)
                {
                    ActivePlayersMessage apm = (ActivePlayersMessage) reply;
                    joined = true;
                    new LobbyWindow(ch, apm.getPlayers(), user);
                }
            }
            Thread.sleep(100);
            s.startGame();
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }        catch(IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
