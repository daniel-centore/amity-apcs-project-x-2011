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

import org.amityregion5.projectx.common.entities.characters.enemies.ArmoredEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.BossEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.DefaultEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.server.game.GameController;

/**
 * A wave of enemies, consisting of several EnemyGroups.
 * 
 * @author Michael Wenke
 */
public class EnemyWave {
    private int waveNumber;
    private ArrayList<EnemyGroup> enemies;
    private long spawnTime;
    private GameController controller;

    /**
     * Creates this wave. Use difficulty, etc. to generate the wave.
     */
    public EnemyWave(int n, ArrayList<EnemyGroup> en, GameController c)
    {
        waveNumber = n;
        enemies = new ArrayList<EnemyGroup>(en.size());
        controller = c;
        for (EnemyGroup group : en)
        {
            Enemy oldEnemy = group.getEnemy();
            Enemy newEnemy;
            int number = -1;
            if (oldEnemy instanceof BossEnemy)
            {
                newEnemy = new BossEnemy(n, (int)(oldEnemy.getMaxHp()*waveEnemyHealth(waveNumber)), 0, 0);
                number = 1;
            }
            else if (oldEnemy instanceof SuicideBomber)
            {
                newEnemy = new SuicideBomber((int)(oldEnemy.getMaxHp()*waveEnemyHealth(waveNumber)), 0, 0);
            }
            else if (oldEnemy instanceof ArmoredEnemy)
            {
                newEnemy = new ArmoredEnemy(((ArmoredEnemy) oldEnemy).getArmor(), (int)(oldEnemy.getMaxHp()*waveEnemyHealth(waveNumber)), 0, 0);
            }
            else
            {
                newEnemy = new DefaultEnemy((int)(oldEnemy.getMaxHp()*waveEnemyHealth(waveNumber)), 0, 0);
            }
            if (number < 0)
                number = (int) (waveNumEnemies(waveNumber));
            EnemyGroup newGroup = new EnemyGroup(newEnemy, number*controller.getPlayers().size());
            enemies.add(newGroup);
        }
        spawnTime = (int) (1000 * waveSpawnTime(n));
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

    public static double waveEnemyHealth(int wn)
    {
        return 1+wn*.05;
    }


    public static double waveSpawnTime(int wn)
    {
        return .01; // inversely-decreasing number
    }

    public static double waveNumEnemies(int wn)
    {
        return (wn*wn + 12*wn + 36) / 49;
    }
}
