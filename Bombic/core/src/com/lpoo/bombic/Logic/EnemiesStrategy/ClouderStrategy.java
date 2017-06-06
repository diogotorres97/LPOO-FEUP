package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the clouderStrategy
 */

public class ClouderStrategy extends Strategy {
    /**
     * Constructor
     */
    public ClouderStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 2f);
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        centeredMove();
    }

}

