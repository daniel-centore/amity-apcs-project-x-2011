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
package org.amityregion5.projectx.client.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.BlockingMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.communication.messages.NullMessage;

/**
 * Handles messages from, and sends messages to, the server.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class CommunicationHandler extends Thread {

    private static CommunicationHandler instance = null; // the latest instance
    private String serverIP; // IP to work with
    private Socket socket = null; // socket we create
    private boolean keepReading = true; // should we be reading from the server?
    private volatile ArrayList<MessageListener> listeners = new ArrayList<MessageListener>(); // listens for messages
    private volatile List<ReplyWaiting> replies = new ArrayList<ReplyWaiting>(); // list of places to check for replies
    private ObjectOutputStream outObjects; // what we write to
    private ObjectInputStream inObjects; // what we read from

    /**
     * Creates and initializes communications with a server
     * 
     * @param serverIP The server to connect to
     * @throws IOException If the server rejects our communication on Constants.PORT
     */
    public CommunicationHandler(String serverIP) throws IOException, SocketTimeoutException
    {
        if (instance != null) // can happen if we leave after bad username
            instance.kill();

        instance = this;
        this.serverIP = serverIP;

        socket = new Socket();
        socket.connect(new InetSocketAddress(serverIP, Constants.PORT), Constants.TIMEOUT);

        outObjects = new ObjectOutputStream(socket.getOutputStream());

        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            inObjects = new ObjectInputStream(socket.getInputStream());

            while (keepReading)
            {
                Message m = (Message) inObjects.readObject();
                handle(m);
            }

        } catch (IOException e1)
        {
            // let it die, and let the listeners' tellSocketClosed() method
            // handle what to do on death
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
            die();
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Handles received messages (should not need further modification - just add a listener)
     * 
     * @param m The message to handler
     */
    private void handle(Message m)
    {
        if (m instanceof BlockingMessage)
        {
            BlockingMessage q = (BlockingMessage) m;

            for (ReplyWaiting bm : replies)
            {
                if (bm.message == q.getMessageNumber())
                {
                    bm.setReply(q.getMessage());
                    synchronized (bm.thread)
                    {
                        bm.thread.notify();
                    }
                    return;
                }
            }

            throw new RuntimeException("Got BlockingMessage that didn't need reply?");
        }

        synchronized (this)
        {
            for (int i = 0; i < listeners.size(); i++)
                listeners.get(i).handle(m);
        }
    }

    /**
     * Kills the communications!
     */
    public void kill()
    {
        try
        {
            inObjects.close();
            outObjects.close();
            socket.close();
        } catch (IOException e)
        {
            // e.printStackTrace();
        } catch (NullPointerException e)
        {
        }
    }

    /**
     * To be used if the connection dies or is closed.
     */
    private synchronized void die()
    {
        for (MessageListener mh : listeners)
        {
            mh.tellSocketClosed();
        }
    }

    /**
     * Registers a listener for communications
     * 
     * @param mh The MessageListener to register
     */
    public synchronized void registerListener(MessageListener mh)
    {
        if (!listeners.contains(mh))
            listeners.add(mh);
    }

    /**
     * Removes a listener
     * 
     * @param mh MessageListener to remove
     */
    public synchronized void removeListener(MessageListener mh)
    {
        listeners.remove(mh);
    }

    /**
     * Sends a message to the server
     * 
     * @param m The Message to send
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
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the server. The difference here is that
     * this method throws an IOException, while send(Message m) doesn't.
     * @param m the Message to send
     */
    public void sendVolatile(Message m) throws IOException
    {
        outObjects.writeObject(m);
        outObjects.flush();
    }

    /**
     * Gets a reply from the Server.
     * 
     * @param m Message to send
     * @return The reply from the server
     */
    public Message requestReply(Message m)
    {
        BlockingMessage bm = new BlockingMessage(m);

        final ReplyWaiting wait;
        replies.add(wait = new ReplyWaiting(Thread.currentThread(), bm.getMessageNumber()));
        try
        {
            sendVolatile(bm);
        } catch (IOException ex)
        {
            Logger.getLogger(CommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            wait.setReply(new NullMessage());
            synchronized (wait.thread)
            {
                wait.thread.notify();
            }
        }

        synchronized (Thread.currentThread())
        {
            while (true)
            {
                try
                {
                    Thread.currentThread().wait();
                } catch (InterruptedException e)
                {
                }

                if (wait.getReply() != null)
                    return wait.getReply();
            }
        }

    }

    /**
     * @return The IP address we are connected to
     */
    public String getServerIP()
    {
        return serverIP;
    }

    /**
     * @return The Socket to the server
     */
    public Socket getSocket()
    {
        return socket;
    }

    /**
     * @return The most recent instance of CommunicationHandler (or null)
     */
    public static CommunicationHandler getInstance()
    {
        return instance;
    }

    /**
     * Handles replies that we wait for
     * 
     * @author Daniel Centore
     * 
     */
    private class ReplyWaiting {

        private final Thread thread; // The thread that is (im)patiently waiting for us
        private int message; // The number reply we will get
        private Message reply; // The reply (gets filled in eventually)

        /**
         * Creates a marker that we are waiting for a reply
         * 
         * @param thread The Thread it is on
         * @param message The number message to wait for
         */
        private ReplyWaiting(Thread thread, int message)
        {
            reply = null;
            this.thread = thread;
            this.message = message;
        }

        /**
         * @return Returns the reply to this message
         */
        public Message getReply()
        {
            return reply;
        }

        /**
         * @param reply Sets the reply for this message
         */
        public void setReply(Message reply)
        {
            this.reply = reply;
        }
    }
}
