package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
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
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Scenes.Hud;
import com.lpoo.bombic.Sprites.Players.Bomber;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
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
 * Created by Rui Quaresma on 17/04/2017.
 */

public class PlayScreen implements Screen {

    //Reference to our Game, used to set Screens
    private Bombic bombicGame;
    private Game game;

    private TextureAtlas atlasHud;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private OrthogonalTiledMapRenderer renderer;

    private Box2DDebugRenderer b2dr;


    public PlayScreen(Bombic bombicGame, Game game) {


        atlasHud = new TextureAtlas("hud.atlas");
        this.bombicGame = bombicGame;
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH / Bombic.PPM, Bombic.V_HEIGHT / Bombic.PPM, gamecam);

        //hud to display players information
        hud = new Hud(this, bombicGame.batch);

        //Set map renderer
        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1 / Bombic.PPM);

        //instead of having camera at (0,0) we will set it to catch the always the position of the players(story mode)
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        b2dr = new Box2DDebugRenderer();

    }

    public Game getGame() {
        return game;
    }


    public OrthographicCamera getGamecam() {
        return gamecam;
    }

    public Viewport getGamePort() {
        return gamePort;
    }


    public TextureAtlas getAtlasHud() {
        return atlasHud;
    }

    @Override
    public void show() {

    }


    public void update(float dt) {
        game.getWorld().step(1 / 60f, 6, 2);
        game.update(dt);


        for (Bomber player : game.getPlayers()) {
            hud.setValues(player);
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
        //TODO: change cam position FOLLOW PLAYER, when map is bigger than screen
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
        b2dr.render(game.getWorld(), gamecam.combined);

        bombicGame.batch.setProjectionMatrix(gamecam.combined);
        bombicGame.batch.begin();

        for (Item item : game.getItems())
            item.draw(bombicGame.batch);
        if (game.getMode()==2 && game.hasEnemies())
            for (Enemy enemy : game.getEnemies())
                enemy.draw(bombicGame.batch);
        else if(game.getMode() == 1)
            for (Enemy enemy : game.getEnemies())
                enemy.draw(bombicGame.batch);
        for (Bomber player : game.getPlayers()) {
            player.draw(bombicGame.batch);
        }
        bombicGame.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        switch (game.getMode()){
            case 1:
                if (game.isGameOver()) {
                    bombicGame.setScreen(new IntermidiateLevelsScreen(bombicGame, game.getNumPlayers(), 0));
                    dispose();
                } else if (game.isLevelWon()) {
                    bombicGame.setCurrentLevel(bombicGame.getCurrentLevel() + 1);
                    if (bombicGame.getCurrentLevel() > bombicGame.getAvailableLevels() && bombicGame.getCurrentLevel() != bombicGame.getNumLevel())
                        bombicGame.setAvailableLevels(bombicGame.getCurrentLevel());
                    bombicGame.setScreen(new IntermidiateLevelsScreen(bombicGame, game.getNumPlayers(), bombicGame.getCurrentLevel()));
                    dispose();
                }
                break;
            case 2:
                if (game.isGameOver()) {
                    bombicGame.setScreen(new DeathmatchIntermidiateScreen(bombicGame, game.getNumPlayers(), 0, game.hasEnemies(), game.getNumBonus()));
                    dispose();
                } else if (game.isLevelWon()) {
                    bombicGame.setCurrentLevel(bombicGame.getCurrentLevel() + 1);
                    if (bombicGame.getCurrentLevel() > bombicGame.getAvailableLevels() && bombicGame.getCurrentLevel() != bombicGame.getNumLevel())
                        bombicGame.setAvailableLevels(bombicGame.getCurrentLevel());
                    bombicGame.setScreen(new DeathmatchIntermidiateScreen(bombicGame, game.getNumPlayers(), bombicGame.getCurrentLevel(), game.hasEnemies(), game.getNumBonus()));
                    dispose();
                }
                break;
            default:
                break;
        }


    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        game.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
