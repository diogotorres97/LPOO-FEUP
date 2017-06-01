package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Creates the trapStrategy
 */
public class TrapStrategy extends Strategy {
    /**
     * Constructor
     */
    public TrapStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        flameMove = true;
        originalFlameMove = true;
        aiLevel = 1;
        enemy.setSpeed(1 / 2f);
        if (enemy.isObjectHit()) {
            if (Math.abs(enemy.getLastSquareX() - (int) (enemy.b2body.getPosition().x * Constants.PPM / 50)) > 0 ||
                    Math.abs(enemy.getLastSquareY() - (int) (enemy.b2body.getPosition().y * Constants.PPM / 50)) > 0)
                generateNewSquares();
            if (centered = getCentered()) {
                hitChangeDir();
            }
        }

        trapsCenteredMove();
    }
}

