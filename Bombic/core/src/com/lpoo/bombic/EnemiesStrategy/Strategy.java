package com.lpoo.bombic.EnemiesStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Tools.Constants;

import java.util.Random;

/**
 * Created by up201503005 on 18/05/2017.
 */
public abstract class Strategy {

    protected Random rand = new Random();
    protected Enemy enemy;
    protected int[] xAddCell = new int[4];
    protected int[] yAddCell = new int[4];
    protected int[] availableDirs;
    protected int numDirs;
    protected boolean stayStill = false;
    protected boolean exceptionMove = false;
    protected Vector2 newVelocity;
    protected boolean followed;

    float[] playerToChase = new float[2];

    public abstract void move(Enemy enemy);

    protected void centeredMove() {
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
            setEnemyKeepVelocity();
        }
    }

    protected void chaseCenteredMove(){
        if (getCentered()) {
            if (stayStill) {
                if (freeForFirstMoveCells()) {
                    changeDir();

                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    stayStill = false;

                }
            } else {
                if ((playerToChase = enemy.checkPlayerNear())[0] != 0) {
                    followed = isFollowPlayer();

                }
                if(!followed) {
                    int dir = changeDir();
                    if (dir == 4) {
                        stayStill = true;
                    } else {
                        enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                        enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    }
                }else {
                    enemy.setLastSquareX(((int) (enemy.b2body.getPosition().x * Constants.PPM / 50)));
                    enemy.setLastSquareY(((int) (enemy.b2body.getPosition().y * Constants.PPM / 50)));
                    followed = false;

                    if (exceptionMove)
                        exceptionMove = false;
                }
            }


        } else {
            setEnemyKeepVelocity();
        }
    }

    protected void setEnemyKeepVelocity(){
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

    protected int changeDir() {
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

    protected abstract boolean freeForFirstMoveCells();

    protected abstract void getFreeCells();

    protected boolean getCentered() {
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

    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) enemy.getGame().getMap().getLayers().get(1);

        int xPos = (int) ((enemy.b2body.getPosition().x * Constants.PPM / 50) + xAdd / 50);
        int yPos = (int) (enemy.b2body.getPosition().y * Constants.PPM / 50 + yAdd / 50);

        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    protected void generateNewSquares() {
        int newSquareX = (int) (enemy.b2body.getPosition().x * Constants.PPM / 50);
        int newSquareY = (int) (enemy.b2body.getPosition().y * Constants.PPM / 50);
        if (enemy.getLastSquareX() > newSquareX)
            enemy.setLastSquareX(newSquareX + 1);
        else if (enemy.getLastSquareX() < newSquareX)
            enemy.setLastSquareX(newSquareX - 1);

        if (enemy.getLastSquareY() > newSquareY)
            enemy.setLastSquareY(newSquareY + 1);
        else if (enemy.getLastSquareY() < newSquareY)
            enemy.setLastSquareY(newSquareY - 1);
    }

    protected void hitChangeDir() {
        if (enemy.velocity.x > 0) {
            if (isObjectTile(getCell(50, 0).getTile().getId()) || getCell(50, 0).getTile().getId() == Constants.BARREL_TILE){
                enemy.setToMove(true);
            }
        } else if (enemy.velocity.x < 0) {
            if (isObjectTile(getCell(-50, 0).getTile().getId())|| getCell(-50, 0).getTile().getId() == Constants.BARREL_TILE) {
                enemy.setToMove(true);
            }
        } else if (enemy.velocity.y > 0) {
            if (isObjectTile(getCell(0, 50).getTile().getId()) || getCell(0, 50).getTile().getId() == Constants.BARREL_TILE ) {
                enemy.setToMove(true);
            }
        } else if (enemy.velocity.y < 0) {
            if (isObjectTile(getCell(0, -50).getTile().getId()) || getCell(0, -50).getTile().getId() == Constants.BARREL_TILE) {
                enemy.setToMove(true);
            }
        }
        enemy.setObjectHit(false);

    }

    protected boolean isFollowPlayer() {
        if (enemy.getLastSquareX() + 1 < playerToChase[0]) {
            if (isFreeCell(getCell(50, 0))) {
                newVelocity.x = enemy.getSpeed();
                newVelocity.y = 0;
                enemy.setVelocity(newVelocity);
                return true;
            }
        } else if (enemy.getLastSquareX() - 1 > playerToChase[0]){
            if (isFreeCell(getCell(-50, 0))) {
                newVelocity.x = -enemy.getSpeed();
                newVelocity.y = 0;
                enemy.setVelocity(newVelocity);
                return true;
            }
        }

        if (enemy.getLastSquareY() + 1 < playerToChase[1]) {
            if (isFreeCell(getCell(0, 50))) {
                newVelocity.x = 0;
                newVelocity.y = enemy.getSpeed();
                enemy.setVelocity(newVelocity);
                return true;
            }
        } else if (enemy.getLastSquareY() - 1 > playerToChase[1]){
            if (isFreeCell(getCell(0, -50))) {
                newVelocity.x = 0;
                newVelocity.y = -enemy.getSpeed();
                enemy.setVelocity(newVelocity);
                return true;
            }
        }

        return false;
    }

    protected boolean isObjectTile(int id){
        for(int i = 0 ; i < Constants.OBJECTS_TILES.length; i++){
            if(id == Constants.OBJECTS_TILES[i])
                return true;
        }
        return false;

    }

    protected boolean isFreeCell(TiledMapTileLayer.Cell cell) {
        if (!exceptionMove) {
            if (cell.getTile().getId() == Constants.BLANK_TILE)
                return true;
        } else if (cell.getTile().getId() == Constants.BLANK_TILE || cell.getTile().getId() == Constants.FLASH1_TILE ||
                cell.getTile().getId() == Constants.FLASH2_TILE || cell.getTile().getId() == Constants.FLASH3_TILE)
            return true;

        return false;

    }
}
