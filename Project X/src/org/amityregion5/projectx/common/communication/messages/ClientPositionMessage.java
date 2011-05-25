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
 * Tells the server that client received a move request
 * 
 * @author Mike DiBuduo
 * @author Daniel Centore
 */
public class ClientPositionMessage extends Message {

    private static final long serialVersionUID = 1L;

    private double offSetX; // how much to offset X
    private double offSetY; // how much to offset Y

    /**
     * Intializer
     * @param x X offset
     * @param y Y offset
     */
    public ClientPositionMessage(double x, double y)
    {
        offSetX = x;
        offSetY = y;
    }

    public double getOffSetX()
    {
        return offSetX;
    }

    public void setOffSetX(double offSetX)
    {
        this.offSetX = offSetX;
    }

    public double getOffSetY()
    {
        return offSetY;
    }

    public void setOffSetY(double offSetY)
    {
        this.offSetY = offSetY;
    }

    @Override
    public String toString()
    {
        return "(" + offSetX + "," + offSetY + ")";
    }
}
