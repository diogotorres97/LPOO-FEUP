package com.lpoo.bombic.Logic.GameLogic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;

/**
 * StoryGame
 */
public class StoryGame extends Game {
    /**
     * Constructors
     * @param map_id
     * @param numPlayers
     * @param mode
     */
    public StoryGame(int map_id, int numPlayers, int mode) {
        super(numPlayers, mode);
        this.map_id = map_id;
        map = mapLoader.load("levels/lvl" + map_id + ".tmx");
        numBonus = 9;
        hasEnemies = true;
        creator = new B2WorldCreator(this);
        creator.startEnemyCreation();
        enemies = creator.getEnemies();
    }

    @Override
    protected void createBombers() {
        pos1 = pos2 = pos3 = pos4 = new Vector2(75, 475);
        super.createBombers();
    }

    @Override
    public void pause() {
        gamePaused = true;
        for (Player player : players) {
            player.pause();
        }
        for (Enemy enemy : enemies) {
            enemy.pause();
        }

    }

    @Override
    public void gameEnds() {
        if (players.length == 0) {
            setGameOver(true);
        } else if (enemies.size == 0) {
            setLevelWon(true);
        }
    }


}
