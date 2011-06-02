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

    public EnemyWave(int n, ArrayList<EnemyGroup> en)
    {
        waveNumber = n;
        enemies = en;
        spawnTime = 500; //Random spawn time
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

}
