package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the redballStrategy
 */

public class RedBallStrategy extends com.lpoo.bombic.Logic.EnemiesStrategy.Strategy {
    /**
     * Constructor
     */
    public RedBallStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 2f);
        tickingMove = true;
        originalTickingMove = true;
        aiLevel = 2;
        centeredMove();

    }

}