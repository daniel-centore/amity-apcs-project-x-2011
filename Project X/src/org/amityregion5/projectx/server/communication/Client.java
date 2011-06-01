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
package org.amityregion5.projectx.server.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.communication.messages.ActivePlayersMessage;
import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.communication.messages.AnnounceMessage;
import org.amityregion5.projectx.common.communication.messages.BlockingMessage;
import org.amityregion5.projectx.common.communication.messages.BooleanReplyMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.ClientPositionMessage;
import org.amityregion5.projectx.common.communication.messages.ClientMovingMessage;
import org.amityregion5.projectx.common.communication.messages.EntityMovedMessage;
import org.amityregion5.projectx.common.communication.messages.FiringMessage;
import org.amityregion5.projectx.common.communication.messages.IntroduceMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.communication.messages.NotifyMessage;
import org.amityregion5.projectx.common.communication.messages.ReadyMessage;
import org.amityregion5.projectx.common.communication.messages.RemoveEntityMessage;
import org.amityregion5.projectx.common.communication.messages.RequestEntityAddMessage;
import org.amityregion5.projectx.common.communication.messages.TextualMessage;
import org.amityregion5.projectx.common.entities.characters.Player;
import org.amityregion5.projectx.server.Server;

/**
 * Represents a client in the game
 * 
 * @author Daniel Centore
 * @author Joe Stein
 * @author Mike DiBuduo
 */
public class Client extends Thread {

    private Socket sock; // socket
    private Server server; // the server to which this Client belongs
    private String username; // the client's username
    private ObjectOutputStream outObjects; // what we write to
    private ObjectInputStream inObjects; // what we read from
    private boolean quit = false; // should we quit?
    private boolean waiting = true; // are we waiting on this client?
    private Player player; // client's player (once we make it!)
    private RawClient raw; // client's raw client (once created)
    private ShotThread shotThread; // helps this client with shooting

    /**
     * Creates a client
     * 
     * @param sock Socket for communications
     */
    public Client(Socket sock, Server server)
    {
        this.server = server;
        this.sock = sock;
        player = null;
        raw = null;

        try
        {
            outObjects = new ObjectOutputStream(sock.getOutputStream());
            inObjects = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        shotThread = new ShotThread(player,server);
    }

    @Override
    public void run()
    {
        try
        {
            while (!quit)
            {
                final Message m = (Message) inObjects.readObject();

                new Thread() {

                    @Override
                    public void run()
                    {
                        Client.this.processMessage(m);
                    }
                }.start();

            }

            inObjects.close();
            sock.close();

        } catch (EOFException eof)
        {
            disconnected();
        } catch (SocketException se)
        {
            disconnected();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void disconnected() // helper
    {
        System.out.println("Client disconnected");
        // remove this client from the server list
        if (username != null) // this client gave us its username
        {
            server.removeClient(username); // take it off the server's list
            server.relayMessage(new AnnounceMessage(username + " has disconnected."));
            server.relayMessage(new RemoveEntityMessage(player));
        }
    }

    /**
     * @return IP address of this client
     */
    public String getIP()
    {
        return sock.getInetAddress().getHostAddress();
    }

    /**
     * Sends a message
     * 
     * @param m Message to send
     */
    public void send(Message m)
    {
        try
        {
            outObjects.writeObject(m);

            outObjects.flush();
        } catch (IOException e)
        {
            // This happens sometimes. I forget when though.
            // e.printStackTrace();
            kill();
        }
    }

    private void processMessage(Message m) // helper
    {
        if (m instanceof BlockingMessage)
        {
            Message contained = ((BlockingMessage) m).getMessage();

            if (contained instanceof IntroduceMessage)
            {
                IntroduceMessage im = (IntroduceMessage) contained;

                if (!server.hasClient(im.getText()))
                {
                    // If there is no client
                    username = im.getText();
                    server.relayMessage(im);
                    ActivePlayersMessage q = server.getPlayersUpdate();
                    server.addClient(username, this);

                    sendReply((BlockingMessage) m, q);
                } else
                {
                    sendReply((BlockingMessage) m, new BooleanReplyMessage(false));
                }
            }
        } else if (m instanceof TextualMessage)
        {
            TextualMessage tm = (TextualMessage) m;
            if (tm instanceof ChatMessage)
            {
                server.relayMessage(tm);
            }
        } else if (m instanceof ReadyMessage)
        {
            ReadyMessage rm = (ReadyMessage) m;
            if (rm.isAffirmative())
            {
                waiting = false;
                server.decrementWaiting();
            } else
            {
                waiting = true;
                server.incrementWaiting();
            }
        } else if (m instanceof NotifyMessage)
        {
            NotifyMessage nm = (NotifyMessage) m;

            switch (nm.getWhat())
            {
            case LOBBY_READY:
                server.incrementWaiting();
                break;
            }
        } else if (m instanceof ClientPositionMessage)
        {
            ClientPositionMessage cmm = (ClientPositionMessage) m;
            player.incrementX(cmm.getOffSetX());
            player.incrementY(cmm.getOffSetY());
            EntityMovedMessage emm = new EntityMovedMessage(player);
            server.relayMessage(emm);
        } else if (m instanceof ClientMovingMessage)
        {
            ClientMovingMessage cmm = (ClientMovingMessage) m;
            player.setMoveSpeed(cmm.getSpeed());
            player.setDirectionMoving(cmm.getDir());
            // just update speed and direction, EntityMoverThread will take
            // care of the rest
        } else if (m instanceof FiringMessage)
        {
            FiringMessage fm = (FiringMessage) m;
            // TODO do stuff when the client starts or stops firing
            synchronized(shotThread)
            {
                if (fm.getFireStart()) // starting firing
                {
                    if (shotThread.getState() != Thread.State.NEW)
                    {
                        shotThread.setShooting(true);
                    } else
                    {
                        shotThread.start();
                    }
                    //server.relayMessage(new FiredMessage(player.getUniqueID()));
                } else
                {
                    // shotThread will wait by itself after we set setShooting
                    // to false
                    shotThread.setShooting(false);
                }
            }
        } else if (m instanceof RequestEntityAddMessage)
        {
            RequestEntityAddMessage ream = (RequestEntityAddMessage) m;
            
            server.relayMessage(new AddEntityMessage(ream.getNewInstance()));
        }
    }

    // helper
    private void sendReply(BlockingMessage original, Message reply)
    {
        send(new BlockingMessage(original, reply));
    }

    /**
     * @return The client's username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Rudely kills the connection
     */
    public void kill()
    {
        try
        {
            quit = true;
            sock.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Are we waiting for the client to press "ready?"
     * 
     * @return True if so; false otherwise
     */
    public boolean isWaiting()
    {
        return waiting;
    }

    /**
     * Sets the client's Player
     * 
     * @param p Player to set it to
     */
    public void setPlayer(Player p)
    {
        player = p;
        shotThread.setPlayer(player);
    }

    /**
     * @return The Client's player
     */
    public Player getPlayer()
    {
        return player;
    }

    public void setRaw(RawClient raw)
    {
        this.raw = raw;
    }

    public RawClient getRaw()
    {
        return raw;
    }
}
