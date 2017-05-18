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
   Random rand = new Random();

   public abstract void move(Enemy enemy);
}
