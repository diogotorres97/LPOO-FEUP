package com.lpoo.bombic.Logic.EnemiesStrategy;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Creates the advancedTrapStrategy
 */

public class AdvancedTrapStrategy extends Strategy {
    /**
     * Constructor
     */
    public AdvancedTrapStrategy() {
        super();
    }

    @Override
    public void move(Enemy enemy) {
        super.move(enemy);
        enemy.setSpeed(1 / 2f);
        aiLevel = 3;
        blankMove = true;
        originalBlankMove = true;
        if (enemy.isObjectHit()) {
            if (Math.abs(enemy.getLastSquareX() - (int) (enemy.b2body.getPosition().x * Constants.PPM / 50)) > 0 ||
                    Math.abs(enemy.getLastSquareY() - (int) (enemy.b2body.getPosition().y * Constants.PPM / 50)) > 0)
                generateNewSquares();
            if (centered = getCentered())
                hitChangeDir();

        } else {
            if (Math.abs(enemy.getLastSquareX() - (int) (enemy.b2body.getPosition().x * Constants.PPM / 50)) > 0 ||
                    Math.abs(enemy.getLastSquareY() - (int) (enemy.b2body.getPosition().y * Constants.PPM / 50)) > 0)
                generateNewSquares();
            if (centered = getCentered()) {
                if (getDangerousPos()) {
                    enemy.setToMove(true);
                }
            }

        }

        trapsCenteredMove();
    }

    /**
     * If the enemy is headed to a dangerous position
     *
     * @return
     */
    private boolean getDangerousPos() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        int dir = 0;
        if (enemy.velocity.x > 0)
            dir = 1;
        else if (enemy.velocity.x < 0)
            dir = 3;
        else if (enemy.velocity.y > 0)
            dir = 0;
        else if (enemy.velocity.y < 0)
            dir = 2;


        TiledMapTileLayer.Cell auxCell = getCell(xAddCell[dir], yAddCell[dir]);

        if (auxCell.getTile().getId() != Constants.BLANK_TILE && !isObjectTile(auxCell.getTile().getId()) && auxCell.getTile().getId() != Constants.BARREL_TILE)
            return true;

        return false;

    }


}
