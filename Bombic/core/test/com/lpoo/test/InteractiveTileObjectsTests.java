package com.lpoo.test;

import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rui Quaresma on 01/06/2017.
 */

public class InteractiveTileObjectsTests extends GameTest {
    StoryGame game = new StoryGame(1, 4, 1);
    Player player = game.getPlayers()[0];
    final float DT = 0.0165346f;

    public void objectExplode() {
        float stateTime = 0;
        player.move(Input.Keys.RIGHT);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        stateTime = 0;
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getObjectsToDestroy().size > 0);
    }

    @Test
    public void getBonus() {
        objectExplode();
        assertTrue(game.getObjectsToDestroy().get(0).getBonus() > 0 && game.getObjectsToDestroy().get(0).getBonus() < 3);
        game.handleSpawningItems(player);
        assertTrue(((Bonus) game.getItems().get(1)).getId() ==  game.getObjectsToDestroy().get(0).getBonus());
    }
}
