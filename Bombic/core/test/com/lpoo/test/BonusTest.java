package com.lpoo.test;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Sprites.Items.Bonus.DeadBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.DistantExplodeBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.KickingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.LBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.NBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SendingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Rui Quaresma on 01/06/2017.
 */

public class BonusTest extends GameTest {
    StoryGame game = new StoryGame(1, 4, 1);
    Player player = game.getPlayers()[0];
    final float DT = 0.0165346f;

    private void playerMoveDown(){
        float stateTime = 0;
        player.move(Input.Keys.DOWN);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            player.update(DT);
            stateTime += DT;
        }
    }

    private void objectExplode() {
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

    @Test
    public void bombBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                BombBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.getBombs() > 0);

    }

    @Test
    public void flameBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                FlameBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.getFlames() > 0);

    }

    @Test
    public void speedBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                SpeedBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.getSpeedIncrease() > 0);
    }

    @Test
    public void deadBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                DeadBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(!player.isBadBonusActive());

    }
    @Test
    public void distantExplodeBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                DistantExplodeBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(!player.isBadBonusActive());

    }

    @Test
    public void kickingBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                KickingBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.isKickingBombs());
    }

    @Test
    public void sendingBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                SendingBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.isSendingBombs());
    }

    @Test
    public void lBombBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                LBombBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.getnLBombs() > 0);
    }

    @Test
    public void nBombBonusTest(){
        game.spawnItem(new ItemDef(new Vector2(0.75f, 4.25f),
                NBombBonus.class));
        game.handleSpawningItems(player);
        playerMoveDown();
        assertTrue(player.getnNBombs() > 0);
    }
}
