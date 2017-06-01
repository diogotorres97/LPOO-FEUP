package com.lpoo.test;

import com.lpoo.bombic.EnemiesStrategy.GreyBallStrategy;
import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Enemies.GreyBall;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Rui Quaresma on 01/06/2017.
 */

public class EnemiesStrategiesTests extends GameTest {
    StoryGame game = new StoryGame(3, 1, 1);
    Player player = game.getPlayers()[0];
    final float DT = 0.0165346f;

    @Test
    public void greyBallTest(){
        float stateTime = 0;
        GreyBall greyBall = new GreyBall(game, 0.75f, 3.75f);
        greyBall.setStrategy(new GreyBallStrategy());
        game.setEnemies(greyBall);
        game.activateEnemies(greyBall);
        while (stateTime < 40f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for(Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }
}
