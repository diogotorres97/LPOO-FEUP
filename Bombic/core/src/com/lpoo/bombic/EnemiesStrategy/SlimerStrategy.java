package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Creates the slimerStrategy
 */

public class SlimerStrategy extends Strategy {
    /**
     * Constructor
     */
    public SlimerStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 4f);
        tickingMove = true;
        originalTickingMove = true;
        aiLevel = 2;
        centeredMove();
    }


}
