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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.User;
import org.amityregion5.projectx.common.communication.messages.ActivePlayersMessage;
import org.amityregion5.projectx.common.communication.messages.AnnounceMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.GoodbyeMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.communication.messages.StatusUpdateMessage;
import org.amityregion5.projectx.common.entities.characters.PlayerEntity;
import org.amityregion5.projectx.server.communication.Client;
import org.amityregion5.projectx.server.communication.Multicaster;
import org.amityregion5.projectx.server.communication.RawServer;
import org.amityregion5.projectx.server.controllers.ServerController;
import org.amityregion5.projectx.server.game.GameController;

/**
 * Accepts incoming connections and makes Clients for them
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class Server {

    public static final int MIN_PLAYERS = 1; // minimum number of players to have a game
    public static final int MAX_PLAYERS = 8; // maximum number of players to allow to connect
    private String name; // the text name of the server
    private boolean listening = true; // should we be listening for clients?
    private ServerSocket servSock; // Server Socket
    private RawServer rawServ; // Server socket for raw connections
    private Multicaster multicaster; // for multicasting IP and String
    private ServerController controller; // controls the server
    private int waiting; // how many people we are waiting for
    private GameController gameController; // Current game controller
    /**
     * A HashMap of the connected Clients to this Server.
     * Will not include clients until they have sent an IntroduceMessage
     * see Client
     */
    private HashMap<String, Client> clients = new HashMap<String, Client>();

    /**
     * Creates a new server.
     * 
     * @param name the name of this server
     */
    public Server(String name)
    {
        System.out.println("Starting server...");
        this.name = name;
        gameController = null;
        try
        {
            servSock = new ServerSocket(Constants.PORT);
            startListening();
            multicaster = new Multicaster(name);
            multicaster.setDaemon(true);
            multicaster.start();
            rawServ = new RawServer(Constants.RAW_PORT, this);
            rawServ.start();
            System.out.println("Server successfully started! Yipee!");
            System.out.println("-----------------------------------------");
        }
        catch(IOException e)
        {
            System.err.println("A server is already running on this computer!");
            System.exit(1);
        }
    }

    /**
     * Sets this server's controller.
     * 
     * @param sc the ServerController that will control this server
     */
    public void setController(ServerController sc)
    {
        controller = sc;
    }

    /**
     * Adds this client to this server.
     * 
     * @param username the username of this client
     * @param c the Client object that handles connections to this client
     */
    public synchronized void addClient(String username, Client c)
    {
        System.out.println("adding client");
        if(clients.size() == MAX_PLAYERS)
        {
            c.disconnect("This game is full.", true);
        }

        if(controller != null)
        {
            controller.clientJoined(username);
        }
        clients.put(username, c);
        c.setWaiting(true);
    }

    /**
     * Removes the client from this server, and notifies other clients that this client has left.
     * 
     * @param username the username of the client to remove
     */
    public synchronized void removeClient(String username)
    {
        controller.clientLeft(username);
        Client c = clients.remove(username);
        if(c != null && c.isWaiting())
        {
            waiting--; // only if we were waiting on them should we count ir
        }
        relayMessage(new GoodbyeMessage(username));
        this.updateWaitingStatus();
    }

    private void updateWaitingStatus() // updates all clients' waiting status
    {
        this.relayMessage(new StatusUpdateMessage("Waiting for " + waiting + " people...", StatusUpdateMessage.Type.WAITING));
    }

    private void startListening() // starts listening for clients
    {
        new Thread(new ClientNetListener()).start();
    }

    /**
     * Halts the server.
     */
    public synchronized void kill()
    {
        if(listening)
            listening = false;
        for(Client client : clients.values())
        {
            client.send(new AnnounceMessage("Server shutting down now!"));
            client.send(new StatusUpdateMessage(
                    StatusUpdateMessage.Type.CLOSING));
            client.kill();
        }
    }

    /**
     * Resets the game. Currently used after game end.
     * Tells clients that the game has ended, starts accepting clients,
     * start multicasting again, and kills the game controller.
     */
    public synchronized void endGame()
    {
        waiting = 0;
        for(Client client : clients.values())
        {
            client.send(new AnnounceMessage("Game Over!"));
            client.send(new StatusUpdateMessage(
                    StatusUpdateMessage.Type.END_GAME));
            client.getShotThread().kill();
        }
        
        multicaster = new Multicaster(name);
        multicaster.setDaemon(true);
        multicaster.start();
        setListening(true);
        gameController.kill();
        gameController = null;
    }

    /**
     * Checks to see if this server already has a client by the given username.
     * 
     * @param text the username to check
     * @return whether or not
     */
    public boolean hasClient(String text)
    {
        return clients.containsKey(text);
    }

    /**
     * Tells the server whether or not to listen for client
     * Also turns multicasting on or off according to listening.
     * Note: setting to true does not actually start the listening thread
     *
     * @param listening True if it should; false otherwise
     */
    public void setListening(boolean listening)
    {
        this.listening = listening;
//        multicaster.setMulticasting(listening);
    }

    /**
     * @return Are we listening for clients?
     */
    public boolean isListening()
    {
        return listening;
    }

    /**
     * Sends a message to all active clients
     * 
     * @param m Message to send
     */
    public synchronized void relayMessage(Message m)
    {
        // hook for the controller
        if(m instanceof ChatMessage)
        {
            ChatMessage cm = (ChatMessage) m;
            controller.chatted(cm.getFrom(), cm.getText());
        }
        else if(m instanceof AnnounceMessage)
        {
            AnnounceMessage am = (AnnounceMessage) m;
            controller.chatted("[SERVER] ", am.getText());
        }
        // relay to clients
        for(Client client : clients.values())
        {
            client.send(m);
        }
    }

    /**
     * @return A Message of all active players
     */
    public ActivePlayersMessage getPlayersUpdate()
    {
        List<User> users = new ArrayList<User>();

        synchronized(this)
        {
            for(Client c : clients.values())
            {
                users.add(new User(c.getUsername(), !c.isWaiting()));
            }
        }

        return new ActivePlayersMessage(users);
    }

    /**
     * @param c the client to exclude
     * @return a Message of all active players excluding the specified client
     */
    public ActivePlayersMessage getPlayersUpdate(Client cl)
    {
        List<User> users = new ArrayList<User>();

        synchronized(this)
        {
            for(Client c : clients.values())
            {
                if (c != cl)
                    users.add(new User(c.getUsername(), !c.isWaiting()));
            }
        }

        return new ActivePlayersMessage(users);
    }

    /**
     * Checks if we already have a client with a certain IP
     * 
     * @param ip IP to check
     * @return True if we do; false otherwise
     */
    public synchronized boolean hasClientWithIP(String ip)
    {
        for(Client client : clients.values())
        {
            if(client.getIP().equals(ip))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Gets the address of the server (pointless)
     */
    public InetAddress getInetAddress()
    {
        try
        {
            return InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            return servSock.getInetAddress();
        }
    }

    /**
     * @return The port the server is running on
     */
    public int getPort()
    {
        return servSock.getLocalPort();
    }

    /**
     * @return The name of the server
     */
    public String getName()
    {
        return name;
    }

    /**
     * Tells the server we are waiting on one more person
     */
    public void incrementWaiting()
    {
        waiting++;
        this.updateWaitingStatus();
    }

    /**
     * Tells the server we are waiting for one less person
     */
    public void decrementWaiting()
    {
        waiting--;

        if(waiting == 0 && clients.size() >= MIN_PLAYERS)
        {
            startGame();
        }
        else
        {
            this.updateWaitingStatus();
        }
    }

    /**
     * Notifies all clients that it's time to begin!
     */
    public void startGame()
    {
        waiting = 0;
        setListening(false);
        relayMessage(new StatusUpdateMessage(StatusUpdateMessage.Type.STARTING));
        gameController = new GameController(this);

    }

    /**
     * @return A HashMap of connected clients
     */
    public HashMap<String, Client> getClients()
    {
        return clients;
    }

    /**
     * @return The current Raw Server
     */
    public RawServer getRawServer()
    {
        return rawServ;
    }

    /**
     * Notifies the server that the given player has fired. This server
     * will in turn hand off the notification to the game controller
     * for damage handling.
     * @param player the player that fired
     */
    public void playerFired(PlayerEntity player)
    {
        if(gameController != null)
        {
            //relayMessage(new FiredMessage(player.getUniqueID()));
            gameController.playerFired(player);
            rawServ.send(Constants.FIRE_PREF, Long.toString(player.getUniqueID()));
        }
    }

    /**
     * Listens for incoming clients
     */
    private class ClientNetListener implements Runnable {

        public void run()
        {
            try
            {
                while(listening)
                {
                    Client newc = new Client(servSock.accept(), Server.this);
                    if(!listening)
                    {
                        if(clients.isEmpty()) // if nobody is playing, lets have a new game
                            Server.this.setListening(true);
                        else
                            newc.disconnect("The game has already started.", true);
                    }

                    newc.start();
                    if(controller != null)
                    {
                        controller.clientConnected(newc.getIP());
                    }
                }
            }
            catch(IOException e)
            {
                listening = false;
            }
        }
    }

    /**
     * @return The current game controller
     */
    public GameController getGameController()
    {
        return gameController;
    }

    /**
     * Sends a Raw Message
     * @param s Message to send
     */
    public void sendRaw(String s)
    {
        rawServ.send(s);
    }
}
