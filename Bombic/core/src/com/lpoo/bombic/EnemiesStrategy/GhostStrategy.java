package com.lpoo.bombic.EnemiesStrategy;


import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;

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
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1 / 3f);
        initiateDirectionVeloctiesMap();
        followed = false;

        chaseCenteredMove();

    }
    protected void getFreeCells() {
      super.getFreeCells();
        if (exceptionMove)
            exceptionMove = false;
    }


}
