package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

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
