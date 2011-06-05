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

package org.amityregion5.projectx.server.game.enemies;

import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;

/**
 * A group of enemies of the same type. A wave has several of these.
 * 
 * @author Michael Wenke
 */
public class EnemyGroup {

    private Enemy enemyType;
    private int numEnemies;
    public EnemyGroup(Enemy e, int n)
    {
        enemyType = e;
        numEnemies = n;
    }

    public Enemy getEnemy()
    {
        return enemyType;
    }

    public int getNumEnemies()
    {
        return numEnemies;
    }

    public void setEnemy(Enemy e)
    {
        enemyType = e;
    }
    public void setNumEnemies(int num)
    {
        numEnemies = num;
    }

}
