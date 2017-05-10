package com.lpoo.bombic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.Players.Player1;
import com.lpoo.bombic.Sprites.Players.Player2;
import com.lpoo.bombic.Sprites.Players.Player3;
import com.lpoo.bombic.Sprites.Players.Player4;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.InputController;
import com.lpoo.bombic.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Rui Quaresma on 08/05/2017.
 */

public abstract class Game {

    /**
     * Map variables
     */
    protected TmxMapLoader mapLoader;
    protected TiledMap map;

    /**
     * Box2d variables
     */
    protected World world;
    protected B2WorldCreator creator;

    /**
     * Sprites of the game: players, enemies, items
     */
    protected Player[] players;
    protected Array<Enemy> enemies;
    protected Array<Item> items;
    protected LinkedBlockingQueue<ItemDef> itemsToSpawn;

    /**
     * InputController responsible for the user input
     */
    protected InputController inputController;

    /**
     * Positions of the players, that depend on the game mode
     */
    protected Vector2 pos1, pos2, pos3, pos4;
    /**
     * Number of players
     */
    protected int numPlayers;
    /**
     * Identifies weather game is over or not
     */
    protected boolean gameOver;
    /**
     * Identifies weather level is won or not
     */
    protected boolean levelWon;
    /**
     * Identifies game mode
     */
    protected int mode;


    /**
     * Variables needed in DeathMatch game mode
     * hasEnemies - Identifies weather the game has enemies or not
     * numBonus - Number of bonus present in the game
     * max_victories - Maximum number of victories
     * current_vics - Number of victories each player has
     * map_id - Id of the map
     */
    protected boolean hasEnemies;
    protected int numBonus;
    protected int max_victories;
    protected int[] current_vics;
    protected int map_id;
    /**
     * Represents the game current speed
     */
    private float gameSpeed;

    /**
     * Game constructor
     * @param numPlayers - number of players
     * @param mode - game mode (1-> Story, 2-> Deathmatch)
     */
    public Game(int numPlayers, int mode) {

        this.mode = mode;
        this.numPlayers = numPlayers;

        gameSpeed = 1.4f;

        mapLoader = new TmxMapLoader();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        inputController = new InputController(this);

        players = new Player[numPlayers];

        createBombers();
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getMode() {
        return mode;
    }

    public boolean hasEnemies() {
        return hasEnemies;
    }

    public int getNumBonus() {
        return numBonus;
    }

    public int getMax_victories() {
        return max_victories;
    }

    public int getMap_id() {
        return map_id;
    }

    public int[] getCurrent_vics() {
        return current_vics;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isLevelWon() {
        return levelWon;
    }

    public void setLevelWon(boolean levelWon) {
        this.levelWon = levelWon;
    }

    public void addBomber(Player player) {
        players[numPlayers] = player;
        numPlayers++;
    }

    protected void createBombers() {

        switch (numPlayers) {
            case 4:
                players[3] = new Player4(this, 4, pos4);
            case 3:
                players[2] = new Player3(this, 3, pos3);
            case 2:
                players[1] = new Player2(this, 2, pos2);
            default:
            case 1:
                players[0] = new Player1(this, 1, pos1);
                break;

        }
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(Player player) {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == ClassicBomb.class) {
                items.add(new ClassicBomb(this, idef.position.x, idef.position.y, player));
            } else if (idef.type == BombBonus.class) {
                items.add(new BombBonus(this, idef.position.x, idef.position.y));
            } else if (idef.type == FlameBonus.class) {
                items.add(new FlameBonus(this, idef.position.x, idef.position.y));
            } else if (idef.type == SpeedBonus.class) {
                items.add(new SpeedBonus(this, idef.position.x, idef.position.y));
            }
        }

    }

    public Player[] getPlayers() {
        return players;
    }

    public Array<Enemy> getEnemies() {
        return creator.getEnemies();
    }

    public Array<Item> getItems() {
        return items;
    }

    public abstract void update(float dt);

    protected abstract void removeEnemies(Enemy[] enemiesToRemove);

    protected void removePlayers(Player[] bombersToRemove) {
        List<Player> list = new ArrayList<Player>();
        Collections.addAll(list, players);
        for (Player player : bombersToRemove) {
            list.removeAll(Arrays.asList(player));
        }

        players = list.toArray(new Player[list.size()]);

    }

    public abstract void gameEnds();

    public void dispose() {
        map.dispose();
        world.dispose();

    }
}
