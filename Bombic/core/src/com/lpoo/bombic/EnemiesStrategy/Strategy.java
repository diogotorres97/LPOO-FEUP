package com.lpoo.bombic.EnemiesStrategy;

import com.lpoo.bombic.Sprites.Enemies.Enemy;

import java.util.Random;

/**
 * Created by up201503005 on 18/05/2017.
 */
public interface Strategy {

   int BARREL_TILE = 31;
   int ROCK_TILE = 20;
   int BUSH_1TILE = 14;
   int BUSH_2TILE = 16;
   int BUSH_3TILE = 18;
   int BLANK_TILE = 11;
   int FLASH1_TILE = 88;
   int FLASH2_TILE = 98;
   int FLASH3_TILE = 108;
   Random rand = new Random();

   public abstract void move(Enemy enemy);
}
