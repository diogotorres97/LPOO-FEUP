package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the slimerStrategy
 */

public class SlimerStrategy extends com.lpoo.bombic.Logic.EnemiesStrategy.Strategy {
    /**
     * Constructor
     */
    public SlimerStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 4f);
        tickingMove = true;
        originalTickingMove = true;
        aiLevel = 2;
        centeredMove();
    }


}
