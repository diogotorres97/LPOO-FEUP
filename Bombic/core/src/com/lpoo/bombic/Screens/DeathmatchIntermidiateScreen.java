package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.DeathmatchGame;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.StoryGame;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class DeathmatchIntermidiateScreen implements Screen {
    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private int numPlayers;

    //private Table overlay;
    private Table table;

    private Bombic game;
    private Game game1;

    private Skin mySkin;
    private int level;

    private Image[] backgrounds;
    private Image showingImage;

    private boolean hasEnemies;
    private int numBonus;

    public DeathmatchIntermidiateScreen(Bombic game, int numPlayers,  int level, boolean hasEnemies, int numBonus ) {
        this.game = game;
        this.level = level;
        this.numPlayers = numPlayers;
        this.hasEnemies = hasEnemies;
        this.numBonus = numBonus;
        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH, Bombic.V_HEIGHT, gamecam);



        backgrounds = new Image[game.getNumLevel() + 2];
        backgrounds[0] = new Image(new Texture(Gdx.files.internal("menus/level0.png")));
        backgrounds[1] = new Image(new Texture(Gdx.files.internal("menus/level1.png")));
        backgrounds[2] = new Image(new Texture(Gdx.files.internal("menus/level2.png")));
        showingImage = backgrounds[level];
        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(showingImage);

        //TODO: criar as varias textures e meter num array, ao criar o screen esse sera o fundo
    }

    @Override
    public void show() {

    }

    private void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new StoryModeScreen(game));
            dispose();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(level == 0){
                level = game.getCurrentLevel();
                stage.getActors().get(0).clear();
                showingImage= backgrounds[level];
                showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
                stage.addActor(showingImage);
            }else if(level > game.getNumLevel()){
                game.setScreen(new MenuScreen(game));
                dispose();
            }else{
                Game game1 = new DeathmatchGame(level, numPlayers, 2, hasEnemies, numBonus);
                game.setScreen(new PlayScreen(game, game1));
                dispose();
            }

        }

    }
    @Override
    public void render(float delta) {

        handleInput();
        gamecam.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
