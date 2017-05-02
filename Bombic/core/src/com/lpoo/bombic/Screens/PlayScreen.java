package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Scenes.Hud;
import com.lpoo.bombic.Sprites.Bomber;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.InputController;
import com.lpoo.bombic.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class PlayScreen implements Screen {

    //Reference to our Game, used to set Screens
    private Bombic game;
    private TextureAtlas atlasBomber;
    private TextureAtlas atlasBombs;
    private TextureAtlas atlasBonus;
    private TextureAtlas atlasFlames;
    private TextureAtlas atlasEnemies;
    private TextureAtlas atlasHud;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Used to load map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Sprites
    private Bomber[] players;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    private int numPlayers;

    private InputController inputController;


    public PlayScreen(Bombic game, int numPlayers) {

        atlasBomber = new TextureAtlas("bomber.atlas");
        atlasBonus = new TextureAtlas("bonus.atlas");
        atlasBombs = new TextureAtlas("bombs.atlas");
        atlasFlames = new TextureAtlas("flames.atlas");
        atlasEnemies = new TextureAtlas("enemies.atlas");
        atlasHud = new TextureAtlas("hud.atlas");
        this.game = game;
        this.numPlayers = numPlayers;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH / Bombic.PPM, Bombic.V_HEIGHT / Bombic.PPM, gamecam);

        //hud to display players information
        hud = new Hud(this, game.batch);

        //Load map and its properties
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");


        //Set map renderer
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Bombic.PPM);

        //instead of having camera at (0,0) we will set it to catch the always the position of the players(story mode)
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //creation of the box2d world
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);


        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        inputController = new InputController(this);

        players = new Bomber[numPlayers];

        createBombers();
    }

    private void createBombers() {
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Bomber(world, this, i + 1);
        }
    }

    public OrthographicCamera getGamecam() {
        return gamecam;
    }

    public Viewport getGamePort() {
        return gamePort;
    }

    public Bombic getGame() {
        return game;
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

    public TextureAtlas getAtlasHud() {
        return atlasHud;
    }

    @Override
    public void show() {

    }


    public void update(float dt) {
        world.step(1 / 60f, 6, 2);
        for (Bomber player : players) {
            inputController.handleInput(player);
            handleSpawningItems(player);
            player.update(dt);
            hud.setValues(player);
        }

        for (Item item : items)
            item.update(dt);

        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            /*if(enemy.getX() < player.getX() + 224 / MarioBros.PPM) {
                enemy.b2body.setActive(true);
            }*/
        }

        //
        //changeCamPosition();
        //Making cam follow bomber
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;

        gamecam.update();
        //only renders what gamecam sees
        renderer.setView(gamecam);

    }

    public void changeCamPosition() {
        //FOLLOW PLAYER, when map is bigger than screen
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        for (Item item : items)
            item.draw(game.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);
        for (Bomber player : players) {
            player.draw(game.batch);
        }
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        /*if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }*/

    }

    /*public boolean gameOver() {
        //Meter para quando os numPlayers estiverem todos mortos
       *//* if (player.currentState == Bomber.State.DEAD) {
            return true;
        }
        return false;*//*
    }*/


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
