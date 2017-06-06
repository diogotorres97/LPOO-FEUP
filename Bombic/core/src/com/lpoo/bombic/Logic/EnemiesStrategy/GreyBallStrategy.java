package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the greyballStrategy
 */

public class GreyBallStrategy extends Strategy {
    /**
     * Constructor
     */
    public GreyBallStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        flameMove = true;
        originalFlameMove = true;
        enemy.setSpeed(1 / 2f);
        aiLevel = 1;


        centeredMove();

    }

}
