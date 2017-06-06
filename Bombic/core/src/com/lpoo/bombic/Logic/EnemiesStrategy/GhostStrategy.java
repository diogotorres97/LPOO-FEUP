package com.lpoo.bombic.Logic.EnemiesStrategy;


import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;

/**
 * Creates the ghostStrategy
 */
public class GhostStrategy extends Strategy {

    /**
     * Constructor
     */
    public GhostStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 3f);
        followed = false;
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();

    }



}
