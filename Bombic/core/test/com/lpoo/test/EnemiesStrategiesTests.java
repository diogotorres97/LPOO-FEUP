package com.lpoo.test;

import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.EnemiesStrategy.AdvancedTrapStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.ClouderStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.GhostStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.GreyBallStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.MoonerStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.RedBallStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.SandmasterStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.SlimerStrategy;
import com.lpoo.bombic.Logic.EnemiesStrategy.TrapStrategy;
import com.lpoo.bombic.Logic.GameLogic.StoryGame;
import com.lpoo.bombic.Logic.Sprites.Enemies.AdvancedTrap;
import com.lpoo.bombic.Logic.Sprites.Enemies.Clouder;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Logic.Sprites.Enemies.Ghost;
import com.lpoo.bombic.Logic.Sprites.Enemies.GreyBall;
import com.lpoo.bombic.Logic.Sprites.Enemies.Mooner;
import com.lpoo.bombic.Logic.Sprites.Enemies.RedBall;
import com.lpoo.bombic.Logic.Sprites.Enemies.Sandmaster;
import com.lpoo.bombic.Logic.Sprites.Enemies.Slimer;
import com.lpoo.bombic.Logic.Sprites.Enemies.Trap;
import com.lpoo.bombic.Logic.Sprites.Players.Player;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Enemy movement strategies
 */

public class EnemiesStrategiesTests extends GenericTest {
    StoryGame game;
    Player player;
    final float DT = 0.0165346f;

    private void playerOpenObject(){
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
        while (stateTime < 5f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
    }

    @Test
    public void greyBallTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        GreyBall greyBall = new GreyBall(game, 1.25f, 4.75f);
        greyBall.setStrategy(new GreyBallStrategy());
        game.setEnemies(greyBall);
        game.activateEnemies(greyBall);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void slimerTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Slimer slimer = new Slimer(game, 1.25f, 4.75f);
        slimer.setStrategy(new SlimerStrategy());
        game.setEnemies(slimer);
        game.activateEnemies(slimer);
        while (stateTime < 30f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());


    }

    @Test
    public void moonerTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Mooner mooner = new Mooner(game, 1.25f, 4.75f);
        mooner.setStrategy(new MoonerStrategy());
        game.setEnemies(mooner);
        game.activateEnemies(mooner);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void trapTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Trap trap = new Trap(game, 1.25f, 4.75f);
        trap.setStrategy(new TrapStrategy());
        game.setEnemies(trap);
        game.activateEnemies(trap);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void advancedTrapTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        AdvancedTrap advancedTrap = new AdvancedTrap(game, 1.25f, 4.75f);
        advancedTrap.setStrategy(new AdvancedTrapStrategy());
        game.setEnemies(advancedTrap);
        game.activateEnemies(advancedTrap);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void clouderTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Clouder clouder = new Clouder(game, 1.25f, 4.75f);
        clouder.setStrategy(new ClouderStrategy());
        game.setEnemies(clouder);
        game.activateEnemies(clouder);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void ghostTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Ghost ghost = new Ghost(game, 1.25f, 4.75f);
        ghost.setStrategy(new GhostStrategy());
        game.setEnemies(ghost);
        game.activateEnemies(ghost);
        while (stateTime < 30f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void sandmasterTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Sandmaster sandmaster = new Sandmaster(game, 1.25f, 4.75f);
        sandmaster.setStrategy(new SandmasterStrategy());
        game.setEnemies(sandmaster);
        game.activateEnemies(sandmaster);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void redBallTest() {
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        RedBall redBall = new RedBall(game, 1.25f, 4.75f);
        redBall.setStrategy(new RedBallStrategy());
        game.setEnemies(redBall);
        game.activateEnemies(redBall);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            for (Enemy enemy : game.getEnemies()) {
                enemy.update(DT);
            }
            stateTime += DT;
        }
        player.update(DT);
        player.update(DT);
        assertTrue(player.isDying());
    }

    @Test
    public void freeAI1Enemy(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        GreyBall greyBall = new GreyBall(game, 2.25f, 4.75f);
        greyBall.setStrategy(new GreyBallStrategy());
        game.setEnemies(greyBall);
        game.activateEnemies(greyBall);
        while (stateTime < 4f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x == 0);

        playerOpenObject();
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x != 0);
    }

    @Test
    public void freeAI2Enemy(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Slimer slimer = new Slimer(game, 2.25f, 4.75f);
        slimer.setStrategy(new SlimerStrategy());
        game.setEnemies(slimer);
        game.activateEnemies(slimer);
        while (stateTime < 4) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x == 0);

        playerOpenObject();
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x != 0);
    }

    @Test
    public void freeAI3Enemy(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Mooner mooner = new Mooner(game, 2.25f, 4.75f);
        mooner.setStrategy(new MoonerStrategy());
        game.setEnemies(mooner);
        game.activateEnemies(mooner);
        while (stateTime < 4) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x == 0);

        playerOpenObject();
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x != 0);
    }

    @Test
    public void moveFlameAI2(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        player.setFlames(3);
        float stateTime = 0;
        Slimer slimer = new Slimer(game, 0.75f, 3.25f);
        slimer.setStrategy(new SlimerStrategy());
        game.setEnemies(slimer);
        game.activateEnemies(slimer);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        while (stateTime < 2.5f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().y != 0);
    }

    @Test
    public void moveTickingAI3(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        player.setFlames(3);
        float stateTime = 0;
        Mooner mooner = new Mooner(game, 0.75f, 3.255f);
        mooner.setStrategy(new MoonerStrategy());
        game.setEnemies(mooner);
        game.activateEnemies(mooner);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        while (stateTime < 1f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().y != 0);
    }

    @Test
    public void moveFlameAI3(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        player.setFlames(3);
        float stateTime = 0;
        Ghost ghost = new Ghost(game, 0.75f, 3.255f);
        ghost.setStrategy(new GhostStrategy());
        game.setEnemies(ghost);
        game.activateEnemies(ghost);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        while (stateTime < 2.5f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getItems().get(0).update(DT);
            stateTime += DT;
        }
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().y != 0);
    }

    @Test
    public void freeTrap(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        Trap trap = new Trap(game, 2.25f, 4.75f);
        trap.setStrategy(new TrapStrategy());
        game.setEnemies(trap);
        game.activateEnemies(trap);
        while (stateTime < 4) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x == 0);

        playerOpenObject();
        game.getEnemies().get(game.getEnemies().size - 1).update(DT);
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).getVelocity().x != 0);
    }

    @Test
    public void enemyeHitBomb(){
        game = new StoryGame(3, 1, 1);
        player = game.getPlayers()[0];
        float stateTime = 0;
        GreyBall greyBall = new GreyBall(game, 1.25f, 4.75f);
        greyBall.setStrategy(new GreyBallStrategy());
        game.setEnemies(greyBall);
        game.activateEnemies(greyBall);
        player.placeBomb();
        game.handleSpawningItems(game.getPlayers()[0]);
        while (stateTime < 10f) {
            game.getWorld().step(1 / 60f, 6, 2);
            game.getEnemies().get(game.getEnemies().size - 1).update(DT);
            stateTime += DT;
        }
        assertTrue(game.getEnemies().get(game.getEnemies().size - 1).isEnemyHitBomb());
    }
}
