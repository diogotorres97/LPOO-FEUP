package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates sandmasterStrategy
 */

public class SandmasterStrategy extends com.lpoo.bombic.Logic.EnemiesStrategy.Strategy {
    /**
     * Constructor
     */
    public SandmasterStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 3f);
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();
    }


}