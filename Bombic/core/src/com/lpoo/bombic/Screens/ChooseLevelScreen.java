package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Network.Character;

import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 05/05/2017.
 */

public class ChooseLevelScreen implements Screen {

    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture background;

    //private Table overlay;
    private Table table;

    private Bombic game;

    private Skin mySkin;

    private boolean toDispose;

    public ChooseLevelScreen(Bombic game) {
        this.game = game;

        toDispose = false;

        //create cam used to follow player through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);

        background = new Texture(Gdx.files.internal("background.png"));
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);

        //atlasButtons = new TextureAtlas("skin/glassy-ui.atlas");

        mySkin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

        table = generateButtons();


        //define a table used to show bombers info
        //Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
        table.setFillParent(true);
        Image backImg = new Image(background);
        backImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage.addActor(backImg);
        stage.addActor(table);

    }


    private Table generateButtons() {

        Table ret = new Table();
        int btnId = 0;
        int nColumns = (game.getNumLevel() % 8) == 0 ? game.getNumLevel() / 8 : (game.getNumLevel() / 8) + 1;
        int nLines = 8;
        for (int i = 0; i < nColumns; i++) {
            Stack stack = new Stack();
            Table overlay = new Table();
            if ((i == nColumns - 1) && (game.getNumLevel() % 8 != 0))
                nLines = game.getNumLevel() % 8;
            for (int j = 0; j < nLines; j++) {
                TextButton txtBtn = new TextButton("Level " + (btnId + 1), mySkin, "default");
                txtBtn.getLabel().setFontScale(0.8f, 1);

                final String btnLabel = txtBtn.getLabel().toString();
                if (!(btnId < game.getAvailableLevels()))
                    txtBtn.setDisabled(true);
                else {
                    txtBtn.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            String aux = btnLabel;
                            if (!java.lang.Character.isDigit(aux.charAt(aux.length() - 2))) {
                                game.setCurrentLevel(Integer.parseInt(aux.substring(aux.length() - 1)));
                            } else {
                                game.setCurrentLevel(Integer.parseInt(aux.substring(aux.length() - 2)));
                            }
                            toDispose = true;
                            return true;
                        }
                    });
                }
                overlay.add(txtBtn).size(120, 50).padRight(20).padBottom(20);
                overlay.row();
                btnId++;
            }
            stack.add(overlay);

            ret.add(stack);


        }
        return ret;
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

        //Clear the menu screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if(toDispose || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new StoryModeScreen(game));
            dispose();
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
        stage.dispose();
    }
}

