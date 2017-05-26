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

public class GreyBallStrategy extends Strategy {
    @Override
    public void move(Enemy enemy) {
        this.enemy = enemy;
        numDirs = 0;
        availableDirs = new int[4];
        newVelocity = new Vector2();
        enemy.setSpeed(1 / 2f);


        centeredMove();

    }

    protected boolean freeForFirstMoveCells(){
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);

            if(auxCell.getTile().getId() == Constants.BLANK_TILE)
                return true;

        }


        return false;
    }

    protected void getFreeCells() {
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};


        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell auxCell = getCell(xAddCell[i], yAddCell[i]);
            if (auxCell.getTile().getId() != Constants.ROCK_TILE && auxCell.getTile().getId() != Constants.BARREL_TILE && auxCell.getTile().getId() != Constants.BUSH_1TILE
                    && auxCell.getTile().getId() != Constants.BUSH_2TILE && auxCell.getTile().getId() != Constants.BUSH_3TILE) {
                availableDirs[i] = 1;
                numDirs++;
            }
        }

    }

}
