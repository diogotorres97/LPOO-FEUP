package com.lpoo.test;

import com.lpoo.bombic.Logic.StoryGame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rui Quaresma on 14/05/2017.
 */

public class StoryGameTest extends GameTest {
    @Test
    public void playersCreation() {
        int numPlayers = 4;
        StoryGame game = new StoryGame(1, numPlayers, 1);

        assertEquals(numPlayers, game.getPlayers().length);
        for (int i = 0; i < numPlayers; i++)
            assertNotNull(game.getPlayers()[i]);


    }

    @Test
    public void bombsCreation(){
        StoryGame game = new StoryGame(1, 1, 1);
        game.getPlayers()[0].placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        assertEquals(1,game.getItems().size);
    }
}
