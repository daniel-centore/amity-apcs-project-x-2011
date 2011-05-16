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

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.amityregion5.projectx.common.communication.messages.BlockingMessage;
import org.amityregion5.projectx.common.communication.messages.BooleanReplyMessage;
import org.amityregion5.projectx.common.communication.messages.ChatMessage;
import org.amityregion5.projectx.common.communication.messages.IntroduceMessage;
import org.amityregion5.projectx.common.communication.messages.Message;
import org.amityregion5.projectx.common.communication.messages.TextualMessage;

/**
 * Represents a client in the game
 * 
 * @author Daniel Centore
 * @author Joe Stein
 */
public class Client extends Thread {

    private Socket sock; // socket
    private Server server; // the server to which this Client belongs
    private String username;
    private ObjectOutputStream outObjects;
    private ObjectInputStream inObjects;

    /**
     * Creates a client
     * 
     * @param sock Socket for communications
     */
    public Client(Socket sock, Server server)
    {
        this.server = server;
        this.sock = sock;
        
        try
        {
            outObjects = new ObjectOutputStream(sock.getOutputStream());
            inObjects = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {

            boolean quit = false;

            while(!quit)
            {
                final Message m = (Message) inObjects.readObject();
                
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        Client.this.processMessage(m);
                    }
                }.start();

            }

            inObjects.close();
            sock.close();

        }
        catch(EOFException eof)
        {
            System.out.println("Client disconnected");
            // remove this client from the server list
            if(username != null) // this client gave us its username
            {
                server.removeClient(username); // take it off the server's list
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
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
        }
        catch(IOException e)
        {
            // This happens sometimes. I forget when though.
            e.printStackTrace();
        }
    }

    private void processMessage(Message m)
    {
        if(m instanceof BlockingMessage)
        {
            Message contained = ((BlockingMessage) m).getMessage();

            if(contained instanceof IntroduceMessage)
            {
                IntroduceMessage im = (IntroduceMessage) contained;

                if(!server.hasClient(im.getText()))
                {
                    username = im.getText();
                    server.addClient(username, this);
                    sendReply((BlockingMessage) m, new BooleanReplyMessage(true));
                }
                else
                {
                    sendReply((BlockingMessage) m, new BooleanReplyMessage(false));
                }
            }
        }
        else if(m instanceof TextualMessage)
        {
            TextualMessage tm = (TextualMessage) m;
            if(tm instanceof ChatMessage)
            {
                server.relayChat((ChatMessage) tm);
            }

        }
        // TODO more message processing!! :P
    }

    private void sendReply(BlockingMessage original, Message reply)
    {
        send(new BlockingMessage(original, reply));
    }
}
