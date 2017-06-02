package com.lpoo.test;

import com.lpoo.bombic.Logic.GameLogic.StoryGame;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StoryGameTest extends GenericTest {


    @Test
    public void bombsCreation(){
        StoryGame game = new StoryGame(1, 1, 1);
        game.getPlayers()[0].placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        assertEquals(1,game.getItems().size);
    }
}
