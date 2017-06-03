package com.lpoo.bombic.Logic.GameLogic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;

import java.util.Random;

/**
 * DeathmatchGame
 */

public class DeathmatchGame extends Game {

    /**
     * Constructor
     * @param map_id
     * @param numPlayers
     * @param mode
     * @param hasEnemies
     * @param numBonus
     * @param max_victories
     * @param current_vics
     */
    public DeathmatchGame(int map_id, int numPlayers, int mode, boolean hasEnemies, int numBonus, int max_victories, int[] current_vics) {
        super(numPlayers, mode);
        this.hasEnemies = hasEnemies;
        this.numBonus = numBonus;
        this.max_victories = max_victories;
        this.current_vics = current_vics;
        this.map_id = map_id;

        loadMap();
        createBombers(0);
        createWorld();

    }

    private void loadMap() {
        if (map_id == 0) {
            Random rand = new Random();
            map = mapLoader.load("levels/dm_" + (rand.nextInt(5) + 1) + ".tmx");
        } else {
            map = mapLoader.load("levels/dm_" + map_id + ".tmx");
        }
    }

    private void createWorld() {
        creator = new B2WorldCreator(this);

        if (hasEnemies) {
            creator.startEnemyCreation();
            enemies = creator.getEnemies();
        }
        creator.setNumBonus(numBonus);
    }

    @Override
    protected void createBombers(int yPos) {
        pos1 = new Vector2(75, 475);
        pos2 = new Vector2(725, 75);
        pos3 = new Vector2(75, 75);
        pos4 = new Vector2(725, 475);
        super.createBombers(yPos);
    }

    @Override
    public void pause() {
        gamePaused = true;
        for (Player player : players) {
            player.pause();
        }
        if (hasEnemies)
            for (Enemy enemy : enemies) {
                enemy.pause();
            }

    }


    @Override
    public void gameEnds() {
        if (players.length == 1) {
            setLevelWon(true);
            current_vics[players[0].getId() - 1]++;
        }
    }
}
