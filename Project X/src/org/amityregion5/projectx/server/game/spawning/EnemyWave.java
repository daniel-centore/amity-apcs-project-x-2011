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
package org.amityregion5.projectx.server.game.spawning;

import java.util.List;

import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;

public class EnemyWave {

    private List<Enemy> enemies;

    public EnemyWave(List<Enemy> enemies)
    {
        this.enemies = enemies;
    }

    public synchronized void addEnemy(Enemy e)
    {
        enemies.add(e);
    }

    public void setEnemies(List<Enemy> enemies)
    {
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies()
    {
        return enemies;
    }

    public int getTimeout() // how long to wait after this wave
    {
        return 3000; // TODO: arbitrary
    }

}
