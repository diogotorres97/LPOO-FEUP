package com.lpoo.bombic.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.LBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.NBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.Constants;
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
     * Identifies whether game is over or not
     */
    protected boolean gameOver;
    /**
     * Identifies whether game is paused or not
     */
    protected boolean gamePaused;
    /**
     * Identifies whether level is won or not
     */
    protected boolean levelWon;
    /**
     * Identifies game mode
     */
    protected int mode;

    Pool<ClassicBomb> classicBombPool = new Pool<ClassicBomb>() {
        @Override
        protected ClassicBomb newObject() {
            return new ClassicBomb(0, 0);
        }
    };

    Pool<BombBonus> bombBonusPool = new Pool<BombBonus>() {
        @Override
        protected BombBonus newObject() {
            return new BombBonus(0, 0);
        }
    };

    Pool<FlameBonus> flameBonusPool = new Pool<FlameBonus>() {
        @Override
        protected FlameBonus newObject() {
            return new FlameBonus(0, 0);
        }
    };

    Pool<SpeedBonus> speedBonusPool = new Pool<SpeedBonus>() {
        @Override
        protected SpeedBonus newObject() {
            return new SpeedBonus(0, 0);
        }
    };


    /**
     * Variables needed in DeathMatch game mode
     * hasEnemies - Identifies whether the game has enemies or not
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
     *
     * @param numPlayers - number of players
     * @param mode       - game mode (1-> Story, 2-> Deathmatch)
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

    public boolean getGamePaused() {
        return gamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
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
                players[3] = new Player(this, 4, pos4);
            case 3:
                players[2] = new Player(this, 3, pos3);
            case 2:
                players[1] = new Player(this, 2, pos2);
            default:
            case 1:
                players[0] = new Player(this, 1, pos1);
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
                ClassicBomb classicBomb = classicBombPool.obtain();
                classicBomb.setGame(this);
                classicBomb.setNewPosition(idef.position.x, idef.position.y);
                classicBomb.setPlayer(player);
                classicBomb.createBomb();
                items.add(classicBomb);
            } else if (idef.type == BombBonus.class) {
                BombBonus bombBonus = bombBonusPool.obtain();
                bombBonus.setGame(this);
                bombBonus.setNewPosition(idef.position.x, idef.position.y);
                bombBonus.createBonus();
                items.add(bombBonus);
            } else if (idef.type == FlameBonus.class) {
                FlameBonus flameBonus = flameBonusPool.obtain();
                flameBonus.setGame(this);
                flameBonus.setNewPosition(idef.position.x, idef.position.y);
                flameBonus.createBonus();
                items.add(flameBonus);
            } else if (idef.type == SpeedBonus.class) {
                SpeedBonus speedBonus = speedBonusPool.obtain();
                speedBonus.setGame(this);
                speedBonus.setNewPosition(idef.position.x, idef.position.y);
                speedBonus.createBonus();
                items.add(speedBonus);
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

    private void enemiesUpdate(float dt) {
        Array<Enemy> enemieToRemove = new Array<Enemy>();
        for (Enemy enemy : enemies) {
            if (!enemy.getDestroyed()) {
                if (!enemy.b2body.isActive())
                    activateEnemies(enemy);
                enemy.update(dt);
            } else
                enemieToRemove.add(enemy);
        }
        removeEnemies(enemieToRemove);
    }

    private void activateEnemies(Enemy enemy) {
        for (Player player : players) {
            if ((enemy.getX() < player.getX() + (Constants.V_WIDTH / 2) / Constants.PPM))
                enemy.b2body.setActive(true);
        }

    }

    private void itemUpdate(float dt) {
        Array<Item> itemsToRemove = new Array<Item>();
        for (Item item : items) {
            if (!item.getDestroyed())
                item.update(dt);
            else
                itemsToRemove.add(item);

        }
        removeItems(itemsToRemove);
    }

    public abstract void pause();

    protected void removeEnemies(Array<Enemy> enemiesToRemove) {
        for (Enemy enemy : enemiesToRemove) {
            enemies.removeValue(enemy, true);
        }
    }

    protected void removePlayers(Player[] bombersToRemove) {
        List<Player> list = new ArrayList<Player>();
        Collections.addAll(list, players);
        for (Player player : bombersToRemove) {
            list.removeAll(Arrays.asList(player));
        }

        players = list.toArray(new Player[list.size()]);

    }

    protected void removeItems(Array<Item> itemsToRemove) {
        for (Item item : itemsToRemove) {
            removeItem(item);
        }
    }

    public void removeItem(Item item) {
        if (item instanceof ClassicBomb) {
            classicBombPool.free((ClassicBomb) item);
        }
        /*if(item instanceof LBomb){
            largeBombPool.free((LBomb) item);
        }
        if(item instanceof NBomb){
            napalmBombPool.free((NBomb) item);
        }*/
        if (item instanceof BombBonus) {
            bombBonusPool.free((BombBonus) item);
        }
        if (item instanceof FlameBonus) {
            flameBonusPool.free((FlameBonus) item);
        }
        if (item instanceof SpeedBonus) {
            speedBonusPool.free((SpeedBonus) item);
        }
        items.removeValue(item, true);

    }

    public abstract void gameEnds();

    public void dispose() {
        map.dispose();
        world.dispose();

    }
}
