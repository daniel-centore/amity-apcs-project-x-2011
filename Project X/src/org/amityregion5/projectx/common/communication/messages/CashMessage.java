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
package org.amityregion5.projectx.common.communication.messages;

/**
 * A message indicating a change in cash.
 *
 * @author Joe Stein
 */
public class CashMessage extends Message
{
    private static final long serialVersionUID = 1L;
    private int amount;
    private long id;

    /**
     * Create a new CashMessage.
     * @param amount the new amount of points/cash the player has
     * @param id the unique id of the player
     */
    public CashMessage(int amount, long id)
    {
        this.amount = amount;
        this.id = id;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getID()
    {
        return id;
    }
}
