package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 20/05/2017.
 */

public class RedBallStrategy extends Strategy {
    private Enemy enemy;
    private int[] xAddCell = new int[4];
    private int[] yAddCell = new int[4];
    private int[] availableDirs;
    private int numDirs;
    private boolean stayStill = false;

    private Vector2 newVelocity;

    @Override
    public void move(Enemy enemy) {
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1 / 2f);
        //Gdx.app.log("vel x", "" + enemy.velocity.x);
        //Gdx.app.log("vel y", "" + enemy.velocity.y);
        if (getCentered()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();

                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    stayStill = false;
                }
            } else {
                int dir = changeDir();

                if (dir == 4) {
                    stayStill = true;
                } else {
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));

                }
            }


        } else {
            if (enemy.getVelocity().x > 0) {
                newVelocity.x = enemy.getSpeed();
                newVelocity.y = 0;
            } else if (enemy.getVelocity().x < 0) {
                newVelocity.x = -enemy.getSpeed();
                newVelocity.y = 0;
            } else if (enemy.getVelocity().y > 0) {
                newVelocity.y = enemy.getSpeed();
                newVelocity.x = 0;
            } else if (enemy.getVelocity().y < 0) {
                newVelocity.y = -enemy.getSpeed();
                newVelocity.x = 0;
            }
            enemy.setVelocity(newVelocity);
        }


    }

    private int changeDir() {
        getFreeCells();
        int dir;
        if (numDirs == 0)
            dir = 4;
        else
            dir = getDir();

        switch (dir) {
            case 0:
                newVelocity.x = 0;
                newVelocity.y = enemy.getSpeed();
                enemy.setVelocity(newVelocity);
                break;
            case 1:
                newVelocity.x = enemy.getSpeed();
                newVelocity.y = 0;
                enemy.setVelocity(newVelocity);
                break;
            case 2:
                newVelocity.x = 0;
                newVelocity.y = -enemy.getSpeed();
                enemy.setVelocity(newVelocity);
                break;
            case 3:
                newVelocity.x = -enemy.getSpeed();
                newVelocity.y = 0;
                enemy.setVelocity(newVelocity);
                break;
            case 4:
                newVelocity.x = 0;
                newVelocity.y = 0;
                enemy.setVelocity(newVelocity);
                break;
            default:
                break;
        }
        return dir;
    }

    private int getDir() {
        int dir = rand.nextInt(numDirs);
        int firstPos = 0;
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            if (availableDirs[i] != 0 && !found)
                if (firstPos == dir) {
                    found = true;
                    dir = i;
                } else {
                    firstPos++;
                }

        }
        return dir;
    }

    private boolean freeForFirstMoveCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);

            if (auxCell.getTile().getId() == Constants.BLANK_TILE || auxCell.getTile().getId() == Constants.FLASH1_TILE ||
                    auxCell.getTile().getId() == Constants.FLASH2_TILE || auxCell.getTile().getId() == Constants.FLASH3_TILE)
                return true;

        }


        return false;
    }

    private void getFreeCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};


        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (auxCell.getTile().getId() == Constants.BLANK_TILE || auxCell.getTile().getId() == Constants.FLASH1_TILE ||
                    auxCell.getTile().getId() == Constants.FLASH2_TILE || auxCell.getTile().getId() == Constants.FLASH3_TILE) {
                availableDirs[i] = 1;
                numDirs++;
            }
        }

    }

    private TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) enemy.getGame().getMap().getLayers().get(1);

        int xPos = (int) ((enemy.b2body.getPosition().x * Constants.PPM / 50) + xAdd / 50);
        int yPos = (int) (enemy.b2body.getPosition().y * Constants.PPM / 50 + yAdd / 50);

        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    private boolean getCentered() {
        float xPos = enemy.b2body.getPosition().x;
        float yPos = enemy.b2body.getPosition().y;
        if (((xPos + enemy.getWidth() / 2) * 2) < enemy.getLastSquareX()) {
            return true;
        } else if (((xPos - enemy.getWidth() / 2) * 2) > (enemy.getLastSquareX() + 1)) {
            return true;
        }

        if (((yPos + enemy.getHeight() / 2) * 2) < enemy.getLastSquareY()) {

            return true;
        } else if (((yPos - enemy.getHeight() / 2) * 2) > (enemy.getLastSquareY() + 1)) {
            return true;
        }

        return false;
    }
}