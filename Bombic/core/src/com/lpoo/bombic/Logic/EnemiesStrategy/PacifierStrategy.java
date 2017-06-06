package com.lpoo.bombic.Logic.EnemiesStrategy;

import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the pacifier strategy
 */

public class PacifierStrategy extends Strategy {

    /**
     * Constructor
     */
    public PacifierStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 2f);
        followed = false;
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();

    }



}