package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Creates the moonerStrategy
 */

public class MoonerStrategy extends Strategy {
    /**
     * Constructor
     */
    public MoonerStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1.1f);
        initiateDirectionVeloctiesMap();

        chaseCenteredMove();


    }

    protected void getFreeCells() {
        super.getFreeCells();
        if (exceptionMove)
            exceptionMove = false;
    }


}
