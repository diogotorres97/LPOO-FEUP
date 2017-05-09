package com.lpoo.bombic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Players.Bomber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class DeathmatchGame extends Game {


    public DeathmatchGame(int level, int numPlayers, int mode, boolean hasEnemies, int numBonus) {
        super(level, numPlayers, mode);
        this.hasEnemies = hasEnemies;
        this.numBonus = numBonus;

        if (hasEnemies) {
            creator.startEnemyCreation();
            enemies = creator.getEnemies();
        }
        creator.setNumBonus(numBonus);
    }

    @Override
    public boolean hasEnemies() {
        return hasEnemies;
    }

    @Override
    public int getNumBonus() {
        return numBonus;
    }

    @Override
    protected void createBombers() {
        pos1 = new Vector2(75, 475);
        pos2 = new Vector2(725, 75);
        pos3 = new Vector2(75, 75);
        pos4 = new Vector2(725, 475);
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

        if (hasEnemies) {
            Enemy[] enemieToRemove = new Enemy[enemies.size];
            for (Enemy enemy : enemies) {
                if (!enemy.getDestroyed())
                    enemy.update(dt);
                else
                    enemieToRemove[id] = enemy;
                id++;
            }
            removeEnemies(enemieToRemove);
        }


        for (Item item : items)
            item.update(dt);


        gameEnds();
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

    @Override
    public void gameEnds() {
        if (players.length == 0) {
            setGameOver(true);
        } else if (players.length == 1) {
            setLevelWon(true);
        }
    }
}
