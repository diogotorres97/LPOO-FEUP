package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class GameOverScreen implements Screen{

    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture background;

    private int numPlayers;

    //private Table overlay;
    private Table table;

    private Bombic game;

    private Skin mySkin;

    public GameOverScreen(Bombic game) {
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH, Bombic.V_HEIGHT, gamecam);

        background = new Texture(Gdx.files.internal("background.png"));
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(new Image(background));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gamecam.update();
        stage.draw();
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

    }
}
