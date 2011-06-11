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
package org.amityregion5.projectx.server.game.enemies;

import java.awt.Point;
import java.util.ArrayList;

import org.amityregion5.projectx.common.entities.characters.enemies.ArmoredEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.BossEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.DefaultEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.server.game.GameController;

/**
 * Class documentation.
 *
 * @author Michal Wenke
 * @author Mike DiBuduo
 */
public class EnemyManager {

    public static final int INITIAL_SPAWN_TIMEOUT = 4500; // how many ms before we start spawining

    private GeneratorThread gen;
    private ArrayList<Point> spawnArea;
    private GameController controller;
    private final int NUM_WAVES = 666; // Completely arbitrary
    private final int START_WAVE = 1; // for testing

    public EnemyManager(GameController c, ArrayList<Point> area)
    {
        controller = c;
        spawnArea = area;

        gen = new GeneratorThread(controller, spawnArea, this);
    }

    public EnemyGroup createEnemyGroup(Enemy en, int num)
    {
        return new EnemyGroup(en, num);
    }

    /**
     * Starts the spawning thread. Adds an initial wave.
     * Note: starts in a new thread. This method returns immediately
     */
    public void startSpawning()
    {
        new Thread() {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(INITIAL_SPAWN_TIMEOUT);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                ArrayList<EnemyGroup> enemies = new ArrayList<EnemyGroup>();
                enemies.add(createEnemyGroup(new ArmoredEnemy(2, 10, 0, 0), 3));
                enemies.add(createEnemyGroup(new SuicideBomber(5, 0, 0), 2));
                enemies.add(createEnemyGroup(new DefaultEnemy(10, 0, 0), 5));
                //enemies.add(createEnemyGroup(new BossEnemy(10, 100, 0, 0), 1));
                for (int i = 1; i <= NUM_WAVES; i++)
                {
                    if (i >= START_WAVE)
                        gen.addWave(new EnemyWave(i, enemies));
                }
                gen.start();
            }
        }.start();
    }

    public void kill()
    {
        gen.kill();
    }

    public static int waveDelayTime(int wn)
    {
        double time = 30;
        time += 10 * wn * Math.log(2) / Math.log(wn + 1);
        return (int) (1000 * time);
    }
}
