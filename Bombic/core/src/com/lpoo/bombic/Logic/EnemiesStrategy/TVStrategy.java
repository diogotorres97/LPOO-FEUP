package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * TV strategy
 */

public class TVStrategy extends Strategy {
    /**
     * Constructor
     */
    public TVStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 4f);
        followed = false;
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();

    }



}