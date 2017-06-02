package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the moonerStrategy
 */

public class MoonerStrategy extends com.lpoo.bombic.Logic.EnemiesStrategy.Strategy {
    /**
     * Constructor
     */
    public MoonerStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1.1f);
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();


    }


}
