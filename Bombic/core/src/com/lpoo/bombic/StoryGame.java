package com.lpoo.bombic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Item;
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
    public void update(float dt) {

        playersUpdate(dt);
        enemiesUpdate(dt);
        itemUpdate(dt);

        gameEnds();

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

    private void playersUpdate(float dt) {
        Player[] playersToRemove = new Player[players.length];

        int id = 0;
        for (Player player : players) {
            if (!player.isDead()) {
                if (!player.isDying())
                    inputController.handleInput(player);
                handleSpawningItems(player);
                player.update(dt);
            } else
                playersToRemove[id] = player;
            id++;
        }

        removePlayers(playersToRemove);
    }

    private void enemiesUpdate(float dt) {
        int id = 0;
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

    private void itemUpdate(float dt) {
        int id = 0;
        Item[] itemsToRemove = new Item[items.size];
        for (Item item : items) {
            if (!item.getDestroyed())
                item.update(dt);
            else
                itemsToRemove[id] = item;
            id++;


        }
        removeItems(itemsToRemove);
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
