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
package org.amityregion5.projectx.common.communication.messages;

/**
 * Acts as a container for Message with an ID, so we can receive replies
 * 
 * NOTE: This relies on having unique messageNumbers.
 * So, if you are going to implement this client-->server-->client then make a new class for it
 * 
 * @author Daniel Centore
 * 
 */
public class BlockingMessage extends Message {

    private static final long serialVersionUID = 1L;
    private static int currentMessage = 0; // the message id we're on

    private int messageNumber; // this message's number
    private Message message; // the message we are wrapping

    /**
     * Creates a BlockingMessage meant to be sent as a reply to original
     * 
     * @param original
     * @param message
     */
    public BlockingMessage(BlockingMessage original, Message message)
    {
        messageNumber = original.getMessageNumber();
        this.message = message;
    }

    /**
     * Creates an original BlockingMessage
     * 
     * @param message The message to wrap
     */
    public BlockingMessage(Message message)
    {
        messageNumber = currentMessage++;
        this.message = message;
    }

    /**
     * @return The message number
     */
    public int getMessageNumber()
    {
        return messageNumber;
    }

    /**
     * @return The message we are wrapping
     */
    public Message getMessage()
    {
        return message;
    }

    /**
     * Sets the message to wrap
     * 
     * @param message The message
     */
    public void setMessage(Message message)
    {
        this.message = message;
    }

}
