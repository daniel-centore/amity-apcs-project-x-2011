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
package org.amityregion5.projectx.server;

import java.util.Scanner;
import org.amityregion5.projectx.common.communication.messages.AnnounceMessage;

/**
 * A class that reads commands from the command line, interprets them, and
 * tells the server what to do.
 *
 * @author Joe Stein
 */
public class CommandServerController extends Thread implements ServerController {
    private static final long serialVersionUID = 1L;

    private Scanner scan;
    private Server server;
    private boolean keepReading = true;

    public CommandServerController(Server serv)
    {
        scan = new Scanner(System.in);
        server = serv;
        this.start();
    }

    @Override
    public void run()
    {
        while(keepReading)
        {
            System.out.print(":");
            String cmd = scan.nextLine();
            String[] args = cmd.split(" ");
            args[0] = args[0].toLowerCase(); // lowercase for interpretation
            if(args[0].equals(Command.HALT.getString()))
            {
                server.kill();
                keepReading = false;
                System.out.println("Goodbye!");
                System.exit(0);
            }
            else if(args[0].equals(Command.ANNOUNCE.getString()))
            {
                if(args.length > 1)
                {
                    String msg = "";
                    // implode the rest of the args to make the message string
                    for(int i = 1;i < args.length;i++)
                    {
                        msg += args[i] + " ";
                    }
                    AnnounceMessage m = new AnnounceMessage(msg);
                    server.relayMessage(m);
                }
                else
                {
                    System.out.println("Usage: announce <message>");
                }

            }
            else if(args[0].equals(Command.HELP.getString()))
            {
                System.out.println("Commands:");
                System.out.println("\thelp: prints this message");
                System.out.println("\thalt: stops the server");
                System.out.println("\tannounce <message>: sends "
                        + "an announcement to clients");
                System.out.println("\tsetaccepting <true/false>: "
                        + "starts/stops accepting new clients");
            } else if (args[0].equals(Command.SET_LISTEN.getString()))
            {
                boolean lis = Boolean.parseBoolean(args[1]);
                server.setListening(lis);
                System.out.print("Server is now ");
                System.out.print(lis ? "" : "not ");
                System.out.println("accepting new clients");
            }
            else
            {
                // unknown command
                System.out.println("Unknown command. Type 'help' for help.");
            }
        }
    }

    public void clientJoined(String username)
    {
        System.out.println(username + "\" joined");
        System.out.print(":");
    }

    public void clientLeft(String username)
    {
        System.out.println(username + "\" left");
        System.out.print(":");
    }

    public void clientConnected(String ip)
    {
        System.out.println("Client connected from " + ip);
        System.out.print(":");
    }

    public void chatted(String username, String chat)
    {
        System.out.println(username + ": " + chat);
        System.out.print(":");
    }

    public enum Command {

        HALT("halt"),
        HELP ("help"),
        ANNOUNCE("announce"),
        SET_LISTEN ("setaccepting");
        private String string;

        Command(String str)
        {
            string = str;
        }

        public String getString()
        {
            return string;
        }
    }
}
