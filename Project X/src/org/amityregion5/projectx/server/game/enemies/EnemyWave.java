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

import java.util.ArrayList;

import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;

/**
 * A wave of enemies, consisting of several EnemyGroups.
 * 
 * @author Michael Wenke
 */
public class EnemyWave {
    private int waveNumber;
    private ArrayList<EnemyGroup> enemies;
    private long spawnTime;
    private final double SPAWNTIME_DIFFICULTY_RAMP = 0.95;
    private final double NUM_ENEMIES_DIFFICULTY_RAMP = 1.25;
    private final double ENEMY_HEALTH_DIFFICULTY_RAMP = 1.25;

    public EnemyWave(int n, ArrayList<EnemyGroup> en)
    {
        waveNumber = n;
        enemies = en;
        spawnTime = 1000; // Random spawn time
    }

    public long getSpawnTime()
    {
        return spawnTime;
    }

    public void setSpawnTime(long time)
    {
        spawnTime = time;
    }

    public int getWaveNumber()
    {
        return waveNumber;
    }

    public ArrayList<EnemyGroup> getEnemyGroups()
    {
        return enemies;
    }

    /**
     * Create the next wave. Will use difficulty, etc. to generate the wave.
     * 
     * @return the next wave to be sent
     */
    public EnemyWave nextWave()
    {
        ArrayList<EnemyGroup> newEnemies = new ArrayList<EnemyGroup>();
        for (int i = 0; i < enemies.size(); i++)
        {
            EnemyGroup group = enemies.get(i);
            Enemy oldEnemy = group.getEnemy();
            Enemy newEnemy = new Enemy((int) (oldEnemy.getMaxHp() * ENEMY_HEALTH_DIFFICULTY_RAMP), 0, 0);
            EnemyGroup newGroup = new EnemyGroup(newEnemy, (int) (group.getNumEnemies() * NUM_ENEMIES_DIFFICULTY_RAMP));
            newEnemies.add(newGroup);
        }
        EnemyWave nextWave = new EnemyWave(this.getWaveNumber() + 1, newEnemies);
        nextWave.setSpawnTime(this.spawnTime * (long) SPAWNTIME_DIFFICULTY_RAMP);
        return nextWave;

    }

}
