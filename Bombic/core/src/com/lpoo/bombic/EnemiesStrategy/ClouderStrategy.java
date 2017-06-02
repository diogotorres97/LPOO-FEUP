package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Creates the clouderStrategy
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
        super.move(enemy);
        enemy.setSpeed(1 / 2f);
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        centeredMove();
    }

}

