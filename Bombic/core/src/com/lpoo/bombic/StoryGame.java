package com.lpoo.bombic;

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

    private void enemiesUpdate(float dt){
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

    private void itemUpdate(float dt){
        for (Item item : items)
            item.update(dt);
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
