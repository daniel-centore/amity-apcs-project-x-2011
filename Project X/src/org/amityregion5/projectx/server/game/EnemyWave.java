/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.server.game;

import java.util.ArrayList;
import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;
import org.amityregion5.projectx.server.game.EnemyGroup;

/**
 *
 * @author Michael Wenke
 */
public class EnemyWave 
{
    private int waveNumber;
    private ArrayList<EnemyGroup> enemies;
    private int spawnTime;

    public EnemyWave(int n, ArrayList<EnemyGroup> en)
    {
        waveNumber = n;
        enemies = en;
        spawnTime = 500; //Random spawn time
    }

    public ArrayList<Enemy> getEnemyTypes()
    {
        ArrayList<Enemy> enemyTypes = new ArrayList<Enemy>();
        for(EnemyGroup group : enemies)
        {
           enemyTypes.add(group.getEnemy());

        }
        return enemyTypes;
    }

    public ArrayList<Integer> getEnemyNumbers()
    {
        ArrayList<Integer> enemyNumbers = new ArrayList<Integer>();
        for(EnemyGroup group : enemies)
        {
            enemyNumbers.add(group.getNumEnemies());
        }
        return null;
    }
}
