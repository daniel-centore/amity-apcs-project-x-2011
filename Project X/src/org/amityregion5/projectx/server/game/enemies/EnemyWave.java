/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.server.game.enemies;

import java.util.ArrayList;

import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
/**
 * A wave of enemies, consisting of several EnemyGroups.
 * @author Michael Wenke
 */
public class EnemyWave 
{
    private int waveNumber;
    private ArrayList<EnemyGroup> enemies;
    private long spawnTime;
    private final double SPAWNTIME_DIFFICULTY_RAMP = .75;
    private final double NUM_ENEMIES_DIFFICULTY_RAMP = 1.5;
    private final double ENEMY_HEALTH_DIFFICULTY_RAMP = 1.5;

    public EnemyWave(int n, ArrayList<EnemyGroup> en)
    {
        waveNumber = n;
        enemies = en;
        spawnTime = 10000; //Random spawn time
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

    public void nextWave()
    {
        waveNumber++;
        setSpawnTime(spawnTime * (long)SPAWNTIME_DIFFICULTY_RAMP);
        for(EnemyGroup group : enemies)
        {
            group.setNumEnemies((int)(group.getNumEnemies() * NUM_ENEMIES_DIFFICULTY_RAMP));
            Enemy previous = group.getEnemy();
            Enemy e = new Enemy(previous.getMaxHp(),0,0);
            group.setEnemy(e);
        }
    }

}
