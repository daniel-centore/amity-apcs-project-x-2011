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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.projectx.common.communication.messages.WaveMessage;
import org.amityregion5.projectx.common.entities.characters.enemies.ArmoredEnemy;

import org.amityregion5.projectx.common.entities.characters.enemies.DefaultEnemy;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.common.entities.characters.enemies.SuicideBomber;
import org.amityregion5.projectx.common.maps.AbstractMap;
import org.amityregion5.projectx.server.game.GameController;

/**
 * The thread that generates (and sends out) enemies.
 *
 * @author Michael Wenke
 */
public class GeneratorThread extends Thread {

    private GameController controller;
    private BlockingQueue<EnemyWave> waves;
    private ArrayList<Point> enemySpawns;
    private EnemyManager manager;
    private boolean spawn;
    private int waveNumber;

    public GeneratorThread(GameController c, ArrayList<Point> spawns, EnemyManager m)
    {
        waves = new LinkedBlockingQueue<EnemyWave>();
        controller = c;
        enemySpawns = c.getEnemySpawns();
        manager = m;
        waveNumber = 1;
    }

    /**
     * Adds a wave of enemies to the game, staggering timing by the specified
     * spawn time in the given wave's getSpawnTime().
     * Note: THIS METHOD BLOCKS UNTIL THE ENTIRE WAVE IS FINISHED.
     */
    private void sendWave(EnemyWave wave)
    {
        ArrayList<EnemyGroup> groups = wave.getEnemyGroups();
        Random gen = new Random();
        for (EnemyGroup group : groups)
        {
            Enemy e = group.getEnemy();
            AbstractMap map = controller.getMap();
            Point center = map.getCenter();
            for (int i = 0; i < group.getNumEnemies(); i++)
            {
                // picks a random spawn point from the spawn point list
                Point pt = enemySpawns.get(gen.nextInt(enemySpawns.size()));
                Point2D.Double spawn = new Point2D.Double(pt.getX(), pt.getY());

                // creates an enemy
                Enemy en;
                if (e instanceof SuicideBomber)
                {
                    en = new SuicideBomber(e.getHp(), (int) spawn.getX(), (int) spawn.getY());
                }
                else if(e instanceof ArmoredEnemy)
                {
                    en = new ArmoredEnemy(((ArmoredEnemy)e).getArmor(), e.getHp(), (int)spawn.getX(), (int)spawn.getY());
                }
                else
                {
                    en = new DefaultEnemy(e.getHp(), (int) spawn.getX(), (int) spawn.getY());
                }
                // puts the enemy at spawn
                en.setLocation(spawn);
                // makes enemy face center of map and move towards it
                en.setDirectionFacing((int) en.getDirectionTowards(center));
                en.setDirectionMoving((int) en.getDirectionTowards(center));
                // adds the entity to the controller
                controller.addEntity(en);
                try
                {
                    Thread.sleep(wave.getSpawnTime());
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(GeneratorThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Adds a wave to the queue.
     * This method does NOT block.
     * @param wave the wave to add to the wave queue
     */
    public void addWave(EnemyWave wave)
    {
        waves.add(wave);
    }

    @Override
    public void run()
    {
        spawn = true;
        while (spawn)
        {
            if (waves.peek() != null)
            {
                sendWave(waves.poll());
                controller.getServer().relayMessage(new WaveMessage(waveNumber,EnemyManager.waveDelayTime(waveNumber)));
                try
                {
                    Thread.sleep(EnemyManager.waveDelayTime(waveNumber++));
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(GeneratorThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void kill()
    {
        spawn = false;
    }

    public void skipWave() {
        waves.poll();
    }
}
