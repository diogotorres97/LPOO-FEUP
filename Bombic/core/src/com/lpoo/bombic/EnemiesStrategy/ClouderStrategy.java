package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 20/05/2017.
 */

public class ClouderStrategy extends Strategy {
    /**
     * Constructor
     */
    public ClouderStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1 / 2f);
        initiateDirectionVeloctiesMap();

        centeredMove();


    }

    protected void getFreeCells() {
       super.getFreeCells();
        if (exceptionMove)
            exceptionMove = false;
    }
}

