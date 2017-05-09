package com.lpoo.bombic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.Players.Bomber;
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

    protected TextureAtlas atlasBomber;
    protected TextureAtlas atlasBombs;
    protected TextureAtlas atlasBonus;
    protected TextureAtlas atlasFlames;
    protected TextureAtlas atlasEnemies;

    protected TmxMapLoader mapLoader;
    protected TiledMap map;

    //Box2d variables
    protected World world;
    protected B2WorldCreator creator;

    //Sprites
    protected Bomber[] players;
    protected Array<Enemy> enemies;

    protected Array<Item> items;
    protected LinkedBlockingQueue<ItemDef> itemsToSpawn;

    protected int numPlayers;

    protected InputController inputController;

    protected boolean gameOver;


    protected boolean levelWon;

    protected int mode;

    protected Vector2 pos1, pos2, pos3, pos4;

    protected boolean hasEnemies;
    protected int numBonus;

    public Game(int level, int numPlayers, int mode) {
        atlasBomber = new TextureAtlas("bomber.atlas");
        atlasBonus = new TextureAtlas("bonus.atlas");
        atlasBombs = new TextureAtlas("bombs.atlas");
        atlasFlames = new TextureAtlas("flames.atlas");
        atlasEnemies = new TextureAtlas("enemies.atlas");

        this.mode = mode;
        this.numPlayers = numPlayers;
        //this.mode = mode;

        //Load map and its properties
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl" + level + ".tmx");

        //creation of the box2d world
        world = new World(new Vector2(0, 0), true);

        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        inputController = new InputController(this);

        players = new Bomber[numPlayers];

        createBombers();
    }

    public int getMode() {
        return mode;
    }

    public boolean hasEnemies(){
        return hasEnemies;
    }

    public int getNumBonus(){
        return numBonus;
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

    public void handleSpawningItems(Bomber player) {
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

    public TextureAtlas getAtlasBomber() {
        return atlasBomber;
    }

    public TextureAtlas getAtlasBombs() {
        return atlasBombs;
    }

    public TextureAtlas getAtlasBonus() {
        return atlasBonus;
    }

    public TextureAtlas getAtlasFlames() {
        return atlasFlames;
    }

    public TextureAtlas getAtlasEnemies() {
        return atlasEnemies;
    }

    public Bomber[] getPlayers() {
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

    protected void removePlayers(Bomber[] bombersToRemove) {
        List<Bomber> list = new ArrayList<Bomber>();
        Collections.addAll(list, players);
        for (Bomber player : bombersToRemove) {
            list.removeAll(Arrays.asList(player));
        }

        players = list.toArray(new Bomber[list.size()]);

    }



    public abstract void gameEnds();

    public void dispose() {
        map.dispose();
        world.dispose();

    }
}
