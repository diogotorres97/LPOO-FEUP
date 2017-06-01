package com.lpoo.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.LBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.NBomb;
import com.lpoo.bombic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class PlayerTests extends GameTest {
    StoryGame game = new StoryGame(1, 4, 1);
    final float DT = 0.0165346f;

    @Test
    public void playersCreation() {
        int numPlayers = 4;

        assertEquals(numPlayers, game.getPlayers().length);
        for (int i = 0; i < numPlayers; i++)
            assertNotNull(game.getPlayers()[i]);

        float x = game.getPlayers()[0].getPosition().x;
        assertEquals(0.75, x, 0.1f);

        float y = game.getPlayers()[0].getPosition().y;
        assertEquals(4.75, y, 0.1f);

    }

    @Test
    public void movePlayer() {
        movePlayerRight();
        movePlayerLeft();
        movePlayerUp();
        movePlayerDown();


    }

    public void movePlayerRight() {
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.move(Input.Keys.RIGHT);
        assertEquals(1.4f, player.getBody().getLinearVelocity().x, 0.1f);
        while (stateTime < 1f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
        assertEquals(1, player.getOrientation());
        assertTrue(player.getPosition().x > 0.75);
        game.getPlayers()[0].stop(Input.Keys.RIGHT);
        assertEquals(0, player.getVelocity().x, 0.1f);
    }

    public void movePlayerLeft() {
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.move(Input.Keys.LEFT);
        assertEquals(-1.4f, player.getBody().getLinearVelocity().x, 0.1f);
        while (stateTime < 1f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
        assertEquals(3, player.getOrientation());
        assertTrue(player.getPosition().x < 1.75);
        player.stop(Input.Keys.LEFT);
        assertEquals(0, player.getVelocity().x, 0.1f);
    }

    public void movePlayerUp() {
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.move(Input.Keys.UP);
        assertEquals(1.4f, player.getBody().getLinearVelocity().y, 0.1f);
        while (stateTime < 1f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
        assertEquals(0, player.getOrientation());
        assertTrue(player.getPosition().y > 3.75);
        player.stop(Input.Keys.UP);
        assertEquals(0, player.getVelocity().y, 0.1f);
    }

    public void movePlayerDown() {
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.move(Input.Keys.DOWN);
        assertEquals(-1.4f, player.getBody().getLinearVelocity().y, 0.1f);
        while (stateTime < 1f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
        assertEquals(2, player.getOrientation());
        assertTrue(player.getPosition().y < 4.75);
        player.stop(Input.Keys.DOWN);
        assertEquals(0, player.getVelocity().y, 0.1f);


    }



    @Test
    public void placeClassicBomb() {
        Player player = game.getPlayers()[0];
        player.setBombs(2);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        assertEquals(ClassicBomb.class, game.getItems().get(0).getClass());
    }

    @Test
    public void placeNBomb() {
        Player player = game.getPlayers()[0];
        player.setnNBombs(1);
        movePlayerDown();
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        assertEquals(NBomb.class, game.getItems().get(0).getClass());
    }

    @Test
    public void placeLBomb() {
        Player player = game.getPlayers()[0];
        player.setnLBombs(1);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        assertEquals(LBomb.class, game.getItems().get(0).getClass());
    }

    @Test
    public void playerDying() {
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        while (stateTime < 3f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void hitBomb() {
        Player player = game.getPlayers()[0];
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        movePlayerRight();
        movePlayerLeft();
        assertTrue(player.isHitBomb());
        float x = player.getPosition().x;
        movePlayerLeft();
        float newX = player.getPosition().x;
        assertEquals(x, newX, 0.1f);
    }
    @Test
    public void kickBomb(){
        float stateTime = 0;
        Player player = game.getPlayers()[0];
        player.setKickingBombs(true);
        hitBomb();
        game.getItems().get(0).update(DT);
        assertEquals(-2f, ((Bomb)game.getItems().get(0)).getBody().getLinearVelocity().x, 0.1f);
        assertTrue(((Bomb)game.getItems().get(0)).isMovingBomb());
        while (stateTime < 3f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        assertEquals(0, ((Bomb)game.getItems().get(0)).getBody().getLinearVelocity().x, 0.1f);
    }
}
