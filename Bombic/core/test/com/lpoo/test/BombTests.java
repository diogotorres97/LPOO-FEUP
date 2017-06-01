package com.lpoo.test;

import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rui Quaresma on 01/06/2017.
 */

public class BombTests extends GameTest {
    StoryGame game = new StoryGame(1, 4, 1);
    Player player = game.getPlayers()[0];
    final float DT = 0.0165346f;

    @Test
    public void explodeNBomb(){
        float stateTime = 0;
        player.setnNBombs(2);
        player.placeBomb();
        game.handleSpawningItems(player);
        while (stateTime < 4f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        assertTrue(((Bomb)game.getItems().get(0)).isOnFire());
    }

    @Test
    public void explodeLBomb(){
        float stateTime = 0;
        player.setnLBombs(2);
        player.placeBomb();
        game.handleSpawningItems(player);
        while (stateTime < (4f / 1.4f)) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        assertTrue(((Bomb)game.getItems().get(0)).isOnFire());
    }
}
