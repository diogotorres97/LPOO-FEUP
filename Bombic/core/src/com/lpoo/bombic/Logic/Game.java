package com.lpoo.bombic.Logic;

import com.badlogic.gdx.Gdx;
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
import com.lpoo.bombic.Sprites.Items.Bonus.LBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.NBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SendingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.Constants;
import com.lpoo.bombic.Tools.InputController;
import com.lpoo.bombic.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

interface FreePool {
    void free(Item item);
}
/**
 * Creates the game
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
     * HashMap containing the fre methods for each item
     */
    private HashMap<Class<?>, FreePool> freePoolHashMap;

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

    Pool<LBomb> LBombPool = new Pool<LBomb>() {
        @Override
        protected LBomb newObject() {
            return new LBomb(0, 0);
        }
    };

    Pool<NBomb> NBombPool = new Pool<NBomb>() {
        @Override
        protected NBomb newObject() {
            return new NBomb(0, 0);
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

    Pool<LBombBonus> LBombBonusPool = new Pool<LBombBonus>() {
        @Override
        protected LBombBonus newObject() {
            return new LBombBonus(0, 0);
        }
    };

    Pool<NBombBonus> NBombBonusPool = new Pool<NBombBonus>() {
        @Override
        protected NBombBonus newObject() {
            return new NBombBonus(0, 0);
        }
    };

    Pool<SendingBonus> sendingBonusPool = new Pool<SendingBonus>() {
        @Override
        protected SendingBonus newObject() {
            return new SendingBonus(0, 0);
        }
    };

    /**
     * HashMap to match bomb class to its pool
     */
    private HashMap<Class<?>, Pool> bombsMap;
    /**
     * HashMap to match bonus class to its pool
     */
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
    public static float GAMESPEED = 1.4f;

    /**
     * Game constructor
     *
     * @param numPlayers - number of players
     * @param mode       - game mode (1-> Story, 2-> Deathmatch)
     */
    public Game(int numPlayers, int mode) {

        this.mode = mode;
        this.numPlayers = numPlayers;

        GAMESPEED = 1.4f;

        mapLoader = new TmxMapLoader();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        objectsToDestroy = new Array<InteractiveTileObject>();

        players = new Player[numPlayers];

        createBombers();

        initializePoolHashMaps();
        initializeFreePoolHashMap();

    }

    private void initializeFreePoolHashMap(){
        freePoolHashMap = new HashMap<Class<?>, FreePool>();
        freePoolHashMapPutClassicBomb();
        freePoolHashMapPutLBombBomb();
        freePoolHashMapPutNBomb();
        freePoolHashMapPutBombBonus();
        freePoolHashMapPutFlameBonus();
        freePoolHashMapPutSpeedBonus();
        freePoolHashMapPutDeadBonus();
        freePoolHashMapPutDistantExplodeBonus();
        freePoolHashMapPutKickingBonus();
        freePoolHashMapPutSendingBonus();
        freePoolHashMapPutLBombBonus();
        freePoolHashMapPutNBombBonus();
    }

    private void freePoolHashMapPutClassicBomb(){
        freePoolHashMap.put(ClassicBomb.class, new FreePool() {
            @Override
            public void free(Item item) {
                classicBombPool.free((ClassicBomb) item);
            }
        });
    }
    private void freePoolHashMapPutLBombBomb(){
        freePoolHashMap.put(LBomb.class, new FreePool() {
            @Override
            public void free(Item item) {
                LBombPool.free((LBomb) item);
            }
        });
    }
    private void freePoolHashMapPutNBomb(){
        freePoolHashMap.put(NBomb.class, new FreePool() {
            @Override
            public void free(Item item) {
                NBombPool.free((NBomb) item);
            }
        });
    }
    private void freePoolHashMapPutBombBonus(){
        freePoolHashMap.put(BombBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                bombBonusPool.free((BombBonus) item);
            }
        });
    }
    private void freePoolHashMapPutFlameBonus(){
        freePoolHashMap.put(FlameBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                flameBonusPool.free((FlameBonus) item);
            }
        });
    }
    private void freePoolHashMapPutSpeedBonus(){
        freePoolHashMap.put(SpeedBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                speedBonusPool.free((SpeedBonus) item);
            }
        });
    }
    private void freePoolHashMapPutDeadBonus(){
        freePoolHashMap.put(DeadBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                deadBonusPool.free((DeadBonus) item);
            }
        });
    }
    private void freePoolHashMapPutDistantExplodeBonus(){
        freePoolHashMap.put(DistantExplodeBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                distantExplodeBonusPool.free((DistantExplodeBonus) item);
            }
        });
    }
    private void freePoolHashMapPutKickingBonus(){
        freePoolHashMap.put(KickingBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                kickingBonusPool.free((KickingBonus) item);
            }
        });
    }
    private void freePoolHashMapPutSendingBonus(){
        freePoolHashMap.put(SendingBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                sendingBonusPool.free((SendingBonus) item);
            }
        });
    }
    private void freePoolHashMapPutLBombBonus(){
        freePoolHashMap.put(LBombBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                LBombBonusPool.free((LBombBonus) item);
            }
        });
    }
    private void freePoolHashMapPutNBombBonus(){
        freePoolHashMap.put(NBombBonus.class, new FreePool() {
            @Override
            public void free(Item item) {
                NBombBonusPool.free((NBombBonus) item);
            }
        });
    }

    private void initializePoolHashMaps() {
        bombsMap = new HashMap<Class<?>, Pool>();
        bombsMap.put(ClassicBomb.class, classicBombPool);
        bombsMap.put(LBomb.class, LBombPool);
        bombsMap.put(NBomb.class, NBombPool);

        bonusMap = new HashMap<Class<?>, Pool>();
        bonusMap.put(BombBonus.class, bombBonusPool);
        bonusMap.put(FlameBonus.class, flameBonusPool);
        bonusMap.put(SpeedBonus.class, speedBonusPool);
        bonusMap.put(DeadBonus.class, deadBonusPool);
        bonusMap.put(DistantExplodeBonus.class, distantExplodeBonusPool);
        bonusMap.put(KickingBonus.class, kickingBonusPool);
        bonusMap.put(LBombBonus.class, LBombBonusPool);
        bonusMap.put(NBombBonus.class, NBombBonusPool);
        bonusMap.put(SendingBonus.class, sendingBonusPool);
    }

    public void setObjectsToDestroy(InteractiveTileObject obj) {
        objectsToDestroy.add(obj);
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
        if (player.isSendingBombs() && !player.isMoving())
            bomb.kick(player.getOrientation());
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
                if (player.isPressedBombButton()) {
                    spawnBombs(idef, player);
                } else
                    itemsToSpawn.add(idef);
            } else if (idef.type.getSuperclass() == Bonus.class)
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

    public void update(float dt) {
        removeObjectsToDestroy();

        playersUpdate(dt);
        if (hasEnemies())
            enemiesUpdate(dt);
        itemUpdate(dt);

        gameEnds();
    }

    private void removeObjectsToDestroy() {
        Array<InteractiveTileObject> objs = new Array<InteractiveTileObject>();

        for (InteractiveTileObject obj : objectsToDestroy) {
            objs.add(obj);
            obj.destroy();
        }

        for (InteractiveTileObject obj : objs) {
            objectsToDestroy.removeValue(obj, true);
        }
    }

    private void playersUpdate(float dt) {
        Player[] playersToRemove = new Player[players.length];

        int id = 0;
        for (Player player : players) {
            if (!player.isDead()) {
                if (!player.isDying())
                    InputController.getInstance().handleInput(player);

                handleSpawningItems(player);

                player.update(dt);
            } else
                playersToRemove[id] = player;
            id++;
        }

        gameOver = InputController.getInstance().isGameOver();

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
        freePoolHashMap.get(item.getClass()).free(item);
        items.removeValue(item, true);

    }

    public abstract void gameEnds();

    public void dispose() {
        map.dispose();
        world.dispose();

    }
}
