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

    public static final int INITIAL_SPAWN_TIMEOUT = 10000; // how many ms before we start spawining

    private GeneratorThread gen;
    private ArrayList<Point> spawnArea;
    private GameController controller;
    private static final int NUM_WAVES = 666; // Completely arbitrary
    private static final int START_WAVE = 30; // for testing

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
                enemies.add(createEnemyGroup(new ArmoredEnemy(5, 20, 0, 0), 5));
                enemies.add(createEnemyGroup(new SuicideBomber(5, 0, 0), 3));
                enemies.add(createEnemyGroup(new DefaultEnemy(10, 0, 0), 8));
                //enemies.add(createEnemyGroup(new BossEnemy(10, 100, 0, 0), 1));
                for (int i = 1; i <= NUM_WAVES; i++)
                {
                    if (i >= START_WAVE)
                        gen.addWave(new EnemyWave(i, enemies, controller));
                }
                gen.start();
            }
        }.start();
    }

    public void kill()
    {
        gen.kill();
    }

    public void skipWave() {
        gen.skipWave();
    }

    /**
     * Gets the delay time until the next wave in milliseconds.
     * @param wn the wave number that the game is currently on
     * @return the delay time in milliseconds
     */
    public static int waveDelayTime(int wn)
    {
        return 20000;
    }
}
