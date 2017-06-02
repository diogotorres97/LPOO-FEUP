package com.lpoo.test;

import com.lpoo.bombic.Logic.GameLogic.StoryGame;
import com.lpoo.bombic.Logic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Logic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for bombs
 */

public class BombTests extends GenericTest {
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
        while (stateTime < (8f / 1.4f)) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        assertTrue(!((Bomb)game.getItems().get(0)).isOnFire());
    }
}
