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
package org.amityregion5.projectx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.messages.AnnounceMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.server.communication.Multicaster;

/**
 * Accepts incoming connections and makes Clients for them
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class Server {

    private String name; // the text name of the server
    private boolean listening = true;
    private ServerSocket servSock;
    private Multicaster multicaster; // for multicasting IP and String

    /*
     * A HashMap of the connected Clients to this Server.
     * Will not include clients until they have sent an IntroduceMessage
     * see Client
     */
    private HashMap<String, Client> clients = new HashMap<String, Client>();

    public Server(String name)
    {
        this.name = name;
        try
        {
            System.out.println("Creating server socket...");
            servSock = new ServerSocket(Constants.PORT);
            System.out.println("Starting to listen on port " + Constants.PORT);
            startListening();
            System.out.println("Creating multicaster...");
            multicaster = new Multicaster(name);
            multicaster.setDaemon(true);
            System.out.println("Starting multicaster...");
            multicaster.start();
           
        }
        catch(IOException e)
        {
            // usually means a server is already running
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void addClient(String username, Client c)
    {
        clients.put(username, c);
    }

    private void startListening()
    {
        new Thread(new ClientNetListener()).start();
    }

    protected void kill()
    {
        listening = false;
        for (Client client : clients.values())
        {
            client.send(new AnnounceMessage("Server shutting down!"));
        }
    }

    public boolean hasClient(String text)
    {
        return clients.containsKey(text);
    }

    public void setListening(boolean listening)
    {
        this.listening = listening;
    }

    public boolean isListening()
    {
        return listening;
    }

    public void relayChat(ChatMessage m)
    {
        for (Client client : clients.values())
        {
            client.send(m);
        }
    }

    private class ClientNetListener implements Runnable {

        public void run()
        {
            try
            {
                while(listening)
                {
                    System.out.println("Waiting for clients...");
                    new Client(servSock.accept()).start();
                    System.out.println("Accepted new client.");
                }
            }
            catch(IOException e)
            {
                listening = false;
            }
        }
    }
}
