package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 20/05/2017.
 */

public class AdvancedTrapStrategy extends Strategy {

    private boolean centered;
    @Override
    public void move(Enemy enemy) {
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1 / 2f);
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
                if (getDangerousPos()){
                    enemy.setToMove(true);
                }
            }

        }


        if (centered && enemy.isToMove()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    enemy.setToMove(false);
                    stayStill = false;
                }
            } else {
                int dir = changeDir();
                if (dir == 4) {
                    stayStill = true;
                } else {
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    enemy.setToMove(false);
                }
            }


        } else {
            setEnemyKeepVelocity();
        }


    }


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




    protected boolean freeForFirstMoveCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);

            if (auxCell.getTile().getId() == Constants.BLANK_TILE)
                return true;

        }

        TiledMapTileLayer.Cell auxCell = getCell(0, 0);
        if (auxCell.getTile().getId() == Constants.FLASH1_TILE ||
                auxCell.getTile().getId() == Constants.FLASH2_TILE || auxCell.getTile().getId() == Constants.FLASH3_TILE)
            for (int i = 0; i < 4; i++) {
                TiledMapTileLayer.Cell auxCell2 = getCell(xAddCell[i], yAddCell[i]);

                if (auxCell2.getTile().getId() == Constants.FLASH1_TILE ||
                        auxCell2.getTile().getId() == Constants.FLASH2_TILE || auxCell2.getTile().getId() == Constants.FLASH3_TILE) {
                    exceptionMove = true;
                    return true;
                }
            }

        return false;
    }

    protected void getFreeCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};


        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (exceptionMove) {
                if (auxCell.getTile().getId() == Constants.BLANK_TILE || auxCell.getTile().getId() == Constants.FLASH1_TILE ||
                        auxCell.getTile().getId() == Constants.FLASH2_TILE || auxCell.getTile().getId() == Constants.FLASH3_TILE) {
                    availableDirs[i] = 1;
                    numDirs++;
                }
            } else if (auxCell.getTile().getId() == Constants.BLANK_TILE) {
                availableDirs[i] = 1;
                numDirs++;
            }
        }
        if(exceptionMove)
            exceptionMove = false;
    }


}
