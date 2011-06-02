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

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.amityregion5.projectx.common.communication.messages.AddEntityMessage;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.server.Server;
import org.amityregion5.projectx.server.game.GameController;

public class EnemySpawning extends Thread {

    private Server server;
    private GameController gc;
    private BlockingQueue<EnemyWave> waveQueue;

    public EnemySpawning(Server server, GameController gc)
    {
        this.gc = gc;
        this.server = server;
        
        waveQueue = new LinkedBlockingQueue<EnemyWave>();

        for (int j = 0; j < 20; j++)
        {
            EnemyWave e = new EnemyWave(new ArrayList<Enemy>());

            for (int i = 0; i < 50; i++)
            {
                Enemy enemy = new Enemy(100, i * 40, i * 40);
                e.addEnemy(enemy);
            }

            try
            {
                waveQueue.put(e);
            } catch (InterruptedException e1)
            {
            }
        }

        this.start();
    }

    public void run()
    {
        while (true)
        {
            EnemyWave ew = waveQueue.poll();
            if (ew == null)
                break;

            spawn(ew);

            try
            {
                Thread.sleep(ew.getTimeout());
            } catch (InterruptedException e)
            {
            }
        }
    }

    public void spawn(EnemyWave wave)
    {
        for (Enemy e : wave.getEnemies())
        {
            gc.addEntity(e);
            server.relayMessage(new AddEntityMessage(e));
        }
    }

}
