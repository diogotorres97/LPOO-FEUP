package com.lpoo.bombic.EnemiesStrategy;

import com.lpoo.bombic.Sprites.Enemies.Enemy;

import java.util.Random;

/**
 * Created by up201503005 on 18/05/2017.
 */
public abstract class Strategy {

   Random rand = new Random();

   public abstract void move(Enemy enemy);
}
