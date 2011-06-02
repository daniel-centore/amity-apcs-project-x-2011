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
import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;



import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.server.game.GameController;

/**
 * Class documentation.
 * 
 * @author Jenny Liu
 * @author Michael Wenke
 */
public class GeneratorThread extends Thread {

    private GameController controller;
    private EnemyWave currentWave;
    private ArrayList<Point> enemySpawns;
    private EnemyManager manager;
    //TODO: Get enemy spawning to work
    public GeneratorThread(GameController c, ArrayList<Point> spawns, EnemyManager m)
    {
        controller = c;
        enemySpawns = c.getEnemySpawns();
        manager = m;
    }

    public void addEnemies(EnemyWave wave) throws InterruptedException
    {
        currentWave = wave;
        ArrayList<Enemy> enemyTypes = wave.getEnemyTypes();
        ArrayList<Integer> enemyQuantities = wave.getEnemyNumbers();
        Random gen = new Random();
        for(int i = 0; i < enemyTypes.size(); i++)
        {
            Enemy e = enemyTypes.get(i);
            for(int j = 0; j < enemyQuantities.get(i); i++)
            {
                Point spawn = enemySpawns.get(gen.nextInt(enemySpawns.size()));
                Enemy en = new Enemy(e.getHp(),e.getHp());
                e.setLocation(spawn);
                controller.addEntity(en);
                


            }
        }
    }
    @Override
    public void run()
    {
        this.start();
        try {
            addEnemies(currentWave);
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneratorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
