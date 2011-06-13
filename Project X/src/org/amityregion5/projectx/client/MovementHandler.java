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
package org.amityregion5.projectx.client;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.amityregion5.projectx.common.entities.EntityConstants;
import org.amityregion5.projectx.common.tools.TimeController;

/**
 * Does some movement stuff
 * 
 * @author Daniel Centore
 *
 */
public class MovementHandler {

    /**
     * Figures out where an entity probably is based on the
     * time its last move instructions were sent
     * @param location The place the entity was
     * @param directionMoving The direction (360 degrees) it is moving
     * @param speed The speed it is moving
     * @param sent The time the instruction was sent at
     * @param tc The {@link TimeController} we are using
     * @return The position to put it at
     */
    public static Point calculatePosition(Point2D.Double location, int directionMoving, double speed, long sent, TimeController tc)
    {
        double oldX = location.x;
        double oldY = location.y;

        double hypotenuse = speed * (tc.getDifference(sent) / EntityConstants.MOVE_UPDATE_TIME);

        double newX = Math.sin(Math.toRadians(directionMoving) * hypotenuse) + oldX;
        double newY = Math.cos(Math.toRadians(directionMoving) * hypotenuse) + oldY;

        return new Point((int) newX, (int) newY);
    }

}
