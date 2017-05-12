package com.lpoo.bombic.Logic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;

public class StoryGame extends Game {
    public StoryGame(int map_id, int numPlayers, int mode) {
        super(numPlayers, mode);

        map = mapLoader.load("lvl" + map_id + ".tmx");
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
