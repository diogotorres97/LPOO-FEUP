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
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Tools.B2WorldCreator;

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
    private Bomber player;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    //To use in handle input (change to the controller class)
    private boolean keyUpPressed = false;
    private boolean keyDownPressed = false;
    private boolean keyLeftPressed = false;
    private boolean keyRightPressed = false;


    public PlayScreen(Bombic game){

        atlasBomber = new TextureAtlas("bomber.atlas");
        atlasBonus = new TextureAtlas("bonus.atlas");
        atlasBombs = new TextureAtlas("bombs.atlas");
        atlasFlames = new TextureAtlas("flames.atlas");
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH / Bombic.PPM, Bombic.V_HEIGHT / Bombic.PPM, gamecam);

        //hud to display players information
        hud = new Hud(game.batch);

        //Load map and setup map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Bombic.PPM);

        //instead of having camera at (0,0) we will set it to catch the always the position of the players(story mode)
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //creation of the box2d world
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Bomber(world, this);

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == ClassicBomb.class){
                items.add(new ClassicBomb(this, idef.position.x, idef.position.y, player));
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

    @Override
    public void show() {

    }

    public void handleInput(float dt){


        //temporary, substitute with function from controller class
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(Input.Keys.UP);
            keyUpPressed = true;
        }else if(keyUpPressed) {
            player.stop(Input.Keys.UP);
            keyUpPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            keyDownPressed = true;
            player.move(Input.Keys.DOWN);
        }else if(keyDownPressed) {
            player.stop(Input.Keys.DOWN);
            keyDownPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(Input.Keys.LEFT);
            keyLeftPressed = true;
        }else if(keyLeftPressed){
            player.stop(Input.Keys.LEFT);
            keyLeftPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.move(Input.Keys.RIGHT);
            keyRightPressed = true;
        }else if(keyRightPressed) {
            player.stop(Input.Keys.RIGHT);
            keyRightPressed = false;
        }

        //Increase game speed
        if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)  && Bombic.GAME_SPEED <= 4) {
            Bombic.GAME_SPEED += 0.1f;
        }

        //Decrease game speed
        if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS) && Bombic.GAME_SPEED >= 0.8) {
            Bombic.GAME_SPEED -= 0.1f;
        }

        //Place bombs
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
           player.placeBomb();
        }



    }

    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        for(Item item : items)
            item.update(dt);

        //
        //changeCamPosition();
        //Making cam follow bomber
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;

        gamecam.update();
        //only renders what gamecam sees
        renderer.setView(gamecam);

    }

    public void changeCamPosition(){

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        for(Item item : items)
            item.draw(game.batch);

        player.draw(game.batch);
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
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
