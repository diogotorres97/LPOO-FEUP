package com.lpoo.bombic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Players.Bomber;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class StoryGame extends Game {
    public StoryGame(int level, int numPlayers, int mode) {
        super(level, numPlayers, mode);
        creator.startEnemyCreation();
        enemies = creator.getEnemies();
    }

    @Override
    protected void createBombers() {
        pos1 = pos2 = pos3 = pos4 = new Vector2(75, 475);
        super.createBombers();
    }

    @Override
    public void update(float dt) {

        Bomber[] bombersToRemove = new Bomber[players.length];

        int id = 0;
        for (Bomber player : players) {
            if (!player.isDead()) {
                if (!player.isDying())
                    inputController.handleInput(player);
                handleSpawningItems(player);
                player.update(dt);
            } else
                bombersToRemove[id] = player;
            id++;
        }

        removePlayers(bombersToRemove);
        id = 0;


        Enemy[] enemieToRemove = new Enemy[enemies.size];
        for (Enemy enemy : enemies) {
            if (!enemy.getDestroyed())
                enemy.update(dt);
            else
                enemieToRemove[id] = enemy;
            id++;
        }
        removeEnemies(enemieToRemove);


        for (Item item : items)
            item.update(dt);


        gameEnds();
    }

    @Override
    public void gameEnds() {
        if (players.length == 0) {
            setGameOver(true);
        } else if (enemies.size == 0) {
            setLevelWon(true);
        }
    }

    @Override
    protected void removeEnemies(Enemy[] enemiesToRemove) {
        int i = 0;
        for (Enemy enemy : enemies) {
            if (enemiesToRemove[i] != null)
                enemies.removeValue(enemy, true);
            i++;
        }


    }


}
