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
        super.move(enemy);
        enemy.setSpeed(1.1f);
        blankMove = true;
        originalBlankMove = true;
        aiLevel = 3;
        chaseCenteredMove();


    }


}
