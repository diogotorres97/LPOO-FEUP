package com.lpoo.test;

import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.EnemiesStrategy.GreyBallStrategy;
import com.lpoo.bombic.Logic.GameLogic.DeathmatchGame;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Logic.GameLogic.MultiPlayerGame;
import com.lpoo.bombic.Logic.GameLogic.StoryGame;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Logic.Sprites.Enemies.GreyBall;
import com.lpoo.bombic.Logic.Sprites.Players.Player;

import org.junit.Test;

import static javax.swing.text.html.HTML.Tag.DT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Rui Quaresma on 02/06/2017.
 */

public class GameTest extends GenericTest {

    final float DT = 0.0165346f;

    @Test
    public void pauseGame() {
        DeathmatchGame deathmatchGame = new DeathmatchGame(1, 2, 2, true, 3, 2, new int[]{0, 0});
        StoryGame storyGame = new StoryGame(1, 1, 1);
        storyGame.pause();
        assertTrue(storyGame.getGamePaused());
        deathmatchGame.pause();
        assertTrue(deathmatchGame.getGamePaused());
    }

    private void objectExplode(Player player, Game game) {
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
    }

    @Test
    public void killEnemies() {
        float stateTime = 0;
        StoryGame storyGame = new StoryGame(1, 1, 1);

        Player player = storyGame.getPlayers()[0];
        GreyBall greyBall = new GreyBall(storyGame, 1.25f, 4.75f);
        greyBall.setStrategy(new GreyBallStrategy());
        storyGame.setEnemies(greyBall);
        storyGame.activateEnemies(greyBall);

        player.placeBomb();
        storyGame.handleSpawningItems(storyGame.getPlayers()[0]);
        while (stateTime < 10f) {
            storyGame.getWorld().step(1 / 60f, 6, 2);
            storyGame.getItems().get(0).update(DT);
            stateTime += DT;
        }
        stateTime = 0;
        assertTrue(storyGame.getEnemies().get(storyGame.getEnemies().size - 1).getX() == 1.25f);
        float numEnemies = storyGame.getEnemies().size;
        while (stateTime < 2f) {
            storyGame.getWorld().step(1 / 60f, 6, 2);
            storyGame.update(DT);
            stateTime += DT;
        }
        assertTrue(storyGame.getEnemies().size == (numEnemies - 1));
    }


    @Test
    public void gameUpdate() {
        float stateTime = 0;
        StoryGame storyGame = new StoryGame(1, 1, 1);
        Player player = storyGame.getPlayers()[0];
        objectExplode(player, storyGame);
        while (stateTime < 2f) {
            storyGame.getWorld().step(1 / 60f, 6, 2);
            storyGame.update(DT);
            stateTime += DT;
        }
        assertTrue(storyGame.isGameOver());
        assertTrue(player.isDead());
        assertTrue(storyGame.getMap_id() == 1);
        assertEquals(1, storyGame.getMode());
        assertEquals(0, storyGame.getPlayers().length);
    }

    @Test
    public void winStoryGame(){
        float stateTime = 0;
        StoryGame storyGame = new StoryGame(1, 1, 1);
        for(Enemy enemy : storyGame.getEnemies()){
            enemy.hitByFlame();
        }
        while (stateTime < 2f) {
            storyGame.getWorld().step(1 / 60f, 6, 2);
            storyGame.update(DT);
            stateTime += DT;
        }
        assertTrue(storyGame.getEnemies().size == 0);
        assertTrue(storyGame.isLevelWon());
    }

    @Test
    public void winDeathMatchGame(){
        float stateTime = 0;
        DeathmatchGame deathmatchGame = new DeathmatchGame(1, 2, 2, true, 3, 2, new int[]{0, 0});
        deathmatchGame.getPlayers()[0].die();
        while (stateTime < 2f) {
            deathmatchGame.getWorld().step(1 / 60f, 6, 2);
            deathmatchGame.update(DT);
            stateTime += DT;
        }
        assertTrue(deathmatchGame.isLevelWon());
        deathmatchGame.dispose();
        int[] numVics = new int[]{0, 1};
        assertTrue(deathmatchGame.getCurrent_vics()[1] > 0);
    }


}
