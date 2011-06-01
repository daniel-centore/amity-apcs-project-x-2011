/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.amityregion5.projectx.server.game.enemies;

import org.amityregion5.projectx.common.entities.characters.enemies.Enemy;

/**
 *
 * @author Michael Wenke
 */
public class EnemyGroup {

    private Enemy enemyType;
    private int numEnemies;
    public EnemyGroup(Enemy e, int n)
    {
        enemyType = e;
        numEnemies = n;
    }

    public Enemy getEnemy()
    {
        return enemyType;
    }

    public int getNumEnemies()
    {
        return numEnemies;
    }

}
