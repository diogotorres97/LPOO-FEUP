package com.lpoo.bombic;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;

import java.util.Random;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class DeathmatchGame extends Game {


    public DeathmatchGame(int map_id, int numPlayers, int mode, boolean hasEnemies, int numBonus, int max_victories, int[] current_vics) {
        super(numPlayers, mode);
        this.hasEnemies = hasEnemies;
        this.numBonus = numBonus;
        this.max_victories = max_victories;
        this.current_vics = current_vics;
        this.map_id = map_id;


        loadMap();
        createWorld();

    }

    private void loadMap(){
        if(map_id == 0){
            Random rand = new Random();
            map = mapLoader.load("dm_" + (rand.nextInt(5) + 1) + ".tmx");
        }else{
            map = mapLoader.load("dm_" + map_id + ".tmx");
        }
    }

    private void createWorld(){
        creator = new B2WorldCreator(this);

        if (hasEnemies) {
            creator.startEnemyCreation();
            enemies = creator.getEnemies();
        }
        creator.setNumBonus(numBonus);
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

        playersUpdate(dt);
        enemiesUpdate(dt);
        itemsUpdate(dt);

        gameEnds();
    }

    private void playersUpdate(float dt){
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
    }

    private void itemsUpdate(float dt){
        for (Item item : items)
            item.update(dt);
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
        if (players.length == 1) {
            setLevelWon(true);
            current_vics[players[0].getId() - 1]++;
        }
    }
}
