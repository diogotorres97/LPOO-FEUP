package com.lpoo.bombic.Logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.LBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.NBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Sprites.Items.Bonus.DeadBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.DistantExplodeBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.KickingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SendingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Bomber;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.Constants;
import com.lpoo.bombic.Tools.InputController;
import com.lpoo.bombic.Tools.MultiPlayerInputController;
import com.lpoo.bombic.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
    protected Array<InteractiveTileObject> objectsToDestroy;
    protected LinkedBlockingQueue<ItemDef> itemsToSpawn;

    /**
     * InputController responsible for the user input
     */
    //protected InputController inputController;
    protected MultiPlayerInputController inputController;

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

    Pool<DeadBonus> deadBonusPool = new Pool<DeadBonus>() {
        @Override
        protected DeadBonus newObject() {
            return new DeadBonus(0, 0);
        }
    };

    Pool<DistantExplodeBonus> distantExplodeBonusPool = new Pool<DistantExplodeBonus>() {
        @Override
        protected DistantExplodeBonus newObject() {
            return new DistantExplodeBonus(0, 0);
        }
    };

    Pool<KickingBonus> kickingBonusPool = new Pool<KickingBonus>() {
        @Override
        protected KickingBonus newObject() {
            return new KickingBonus(0, 0);
        }
    };

    private HashMap<Class<?>, Pool> bombsMap;

    private HashMap<Class<?>, Pool> bonusMap;


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
    private boolean mReady;

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
        objectsToDestroy = new Array<InteractiveTileObject>();

        //inputController = new InputController(this);
        inputController = new MultiPlayerInputController(this);

        players = new Player[numPlayers];

        mReady=true;

        createBombers();

        initializeHashMaps();
    }

    private void initializeHashMaps() {
        bombsMap = new HashMap<Class<?>, Pool>();
        bombsMap.put(ClassicBomb.class, classicBombPool);
        bombsMap.put(LBomb.class, classicBombPool);
        bombsMap.put(NBomb.class, classicBombPool);

        bonusMap = new HashMap<Class<?>, Pool>();
        bonusMap.put(BombBonus.class, bombBonusPool);
        bonusMap.put(FlameBonus.class, flameBonusPool);
        bonusMap.put(SpeedBonus.class, speedBonusPool);
        bonusMap.put(DeadBonus.class, deadBonusPool);
        bonusMap.put(DistantExplodeBonus.class, distantExplodeBonusPool);
        bonusMap.put(KickingBonus.class, kickingBonusPool);
    }

    public void setObjectsToDestroy(InteractiveTileObject obj) {
        objectsToDestroy.add(obj);
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

    private Bomb getBomb(Pool pool) {
        return (Bomb) pool.obtain();
    }

    private Bonus getBonus(Pool pool) {
        return (Bonus) pool.obtain();
    }

    private void spawnBombs(ItemDef idef, Player player) {
        Bomb bomb = getBomb(bombsMap.get(idef.type));
        bomb.setGame(this);
        bomb.setNewPosition(idef.position.x, idef.position.y);
        bomb.setPlayer(player);
        bomb.createBomb();
        items.add(bomb);
    }

    private void spawnBonus(ItemDef idef) {
        Bonus bonus = getBonus(bonusMap.get(idef.type));
        bonus.setGame(this);
        bonus.setNewPosition(idef.position.x, idef.position.y);
        bonus.createBonus();
        items.add(bonus);
    }

    public void handleSpawningItems(Player player) {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type.getSuperclass() == Bomb.class) {
                spawnBombs(idef, player);
            } else
                spawnBonus(idef);
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

    public void update(float dt, int[] input) {
        removeObjectsToDestroy();

        playersUpdate(dt, input);
        //enemiesUpdate(dt);
        itemUpdate(dt);

        gameEnds();
    }

    protected void removeObjectsToDestroy() {
        Array<InteractiveTileObject> objs = new Array<InteractiveTileObject>();

        for (InteractiveTileObject obj : objectsToDestroy) {
            objs.add(obj);
            obj.destroy();
        }

        for (InteractiveTileObject obj : objs) {
            objectsToDestroy.removeValue(obj, true);
        }
    }

    protected void playersUpdate(float dt, int [] input) {
        Player[] playersToRemove = new Player[players.length];

        int id = 0;
        switch (input[0]){
            case 1:
                if (!players[0].isDead()) {
                    if (!players[0].isDying())
                        inputController.handleInput(players[0], input[1]);

                    handleSpawningItems(players[0]);
                    players[0].update(dt);
                } else
                    playersToRemove[id] = players[0];
                id++;
                break;
            case 2:
                if (!players[1].isDead()) {
                    if (!players[1].isDying())
                        inputController.handleInput(players[1], input[1]);

                    handleSpawningItems(players[1]);
                    players[1].update(dt);
                } else
                    playersToRemove[id] = players[1];
                id++;
                break;

            default:
                break;
        }


        /*
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
*/
        removePlayers(playersToRemove);
    }

    protected void enemiesUpdate(float dt) {
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

    protected void itemUpdate(float dt) {
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
        if (item instanceof DeadBonus) {
            deadBonusPool.free((DeadBonus) item);
        }
        if (item instanceof DistantExplodeBonus) {
            distantExplodeBonusPool.free((DistantExplodeBonus) item);
        }
        if (item instanceof KickingBonus) {
            kickingBonusPool.free((KickingBonus) item);
        }
        if (item instanceof SendingBonus) {
            //speedBonusPool.free((SpeedBonus) item);
        }
        items.removeValue(item, true);

    }

    public abstract void gameEnds();

    public void dispose() {
        map.dispose();
        world.dispose();

    }

    public boolean getReady() {
        return mReady;
    }

    public void setReady(boolean pReady){
        mReady=pReady;
    }

    public void addPlayer(final Bomber pPlayer) {
        final ArrayList<Player> players = new ArrayList<>();
        Collections.addAll(players, this.players);
        players.add(pPlayer);
        this.players = players.toArray(new Bomber[players.size()]);
        System.out.println("Added player!");
    }

    protected void removePlayers(Bomber[] bombersToRemove) {
        List<Player> list = new ArrayList<Player>();
        Collections.addAll(list, players);
        for (Bomber player : bombersToRemove) {
            list.removeAll(Arrays.asList(player));
        }

        players = list.toArray(new Bomber[list.size()]);

    }

}
