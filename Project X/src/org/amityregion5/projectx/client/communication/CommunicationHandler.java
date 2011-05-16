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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.amityregion5.projectx.common.communication.Constants;
import org.amityregion5.projectx.common.communication.MessageListener;
import org.amityregion5.projectx.common.communication.messages.BlockingMessage;
import org.amityregion5.projectx.common.communication.messages.Message;

/**
 * Handles messages from, and sends messages to, the server.
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class CommunicationHandler extends Thread {

    private String serverIP; // IP to work with
    private Socket socket = null; // socket we create
    private boolean keepReading = true; // should we be reading from the server?
    private ArrayList<MessageListener> listeners = new ArrayList<MessageListener>(); // listens for messages
    private volatile List<ReplyWaiting> replies = new ArrayList<ReplyWaiting>(); // list of places to check for replies

    /**
     * Creates and initializes communications with a server
     * 
     * @param serverIP The server to connect to
     * @throws IOException If the server rejects our communication on Constants.PORT
     */
    public CommunicationHandler(String serverIP) throws IOException
    {
        this.serverIP = serverIP;

        socket = new Socket(serverIP, Constants.PORT);

        this.start();
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            while (keepReading)
            {
                // TODO: handle communication interrupts here
                Message m = (Message) input.readObject();
                handle(m);
            }

        } catch (IOException e1)
        {
            e1.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
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

        for (MessageListener mh : listeners)
        {
            mh.handle(m);
        }
    }

    /**
     * Registers a listener for communications
     * 
     * @param mh The MessageListener to register
     */
    public void registerListener(MessageListener mh)
    {
        listeners.add(mh);
    }

    /**
     * Removes a listener
     * 
     * @param mh MessageListener to remove
     */
    public void removeListener(MessageListener mh)
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
            ObjectOutputStream outObjects = new ObjectOutputStream(socket.getOutputStream());
            outObjects.writeObject(m);

            outObjects.flush();
        } catch (IOException e)
        {
            // This happens sometimes. I forget when though.
            e.printStackTrace();
        }
    }

    /**
     * Gets a reply from the Server
     * 
     * @param m Message to send
     * @return The reply from the server
     */
    public Message requestReply(Message m)
    {
        BlockingMessage bm = new BlockingMessage(m);

        ReplyWaiting wait;
        replies.add(wait = new ReplyWaiting(Thread.currentThread(), bm.getMessageNumber()));

        send(bm);

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
     * Handles replies that we wait for
     * 
     * @author Daniel Centore
     * 
     */
    private class ReplyWaiting {

        private Thread thread; // The thread that is (im)patiently waiting for us
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
