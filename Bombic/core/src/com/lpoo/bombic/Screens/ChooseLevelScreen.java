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
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Network.Character;

import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 05/05/2017.
 */

public class ChooseLevelScreen extends AbstractScreen {


    private Texture background;

    //private Table overlay;
    private Table table;

    private Skin mySkin;

    private boolean toDispose;

    public ChooseLevelScreen(Bombic bombicGame) {
        super(bombicGame);

    }

    @Override
    public void show() {
        toDispose = false;

        background = new Texture(Gdx.files.internal("background.png"));

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
        int nColumns = (bombicGame.getNumLevel() % 8) == 0 ? bombicGame.getNumLevel() / 8 : (bombicGame.getNumLevel() / 8) + 1;
        int nLines = 8;
        for (int i = 0; i < nColumns; i++) {
            Stack stack = new Stack();
            Table overlay = new Table();
            if ((i == nColumns - 1) && (bombicGame.getNumLevel() % 8 != 0))
                nLines = bombicGame.getNumLevel() % 8;
            for (int j = 0; j < nLines; j++) {
                TextButton txtBtn = new TextButton("Level " + (btnId + 1), mySkin, "default");
                txtBtn.getLabel().setFontScale(0.8f, 1);

                final String btnLabel = txtBtn.getLabel().toString();
                if (!(btnId < bombicGame.getAvailableLevels()))
                    txtBtn.setDisabled(true);
                else {
                    txtBtn.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            String aux = btnLabel;
                            if (!java.lang.Character.isDigit(aux.charAt(aux.length() - 2))) {
                                bombicGame.setCurrentLevel(Integer.parseInt(aux.substring(aux.length() - 1)));
                            } else {
                                bombicGame.setCurrentLevel(Integer.parseInt(aux.substring(aux.length() - 2)));
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
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level) {

    }

    @Override
    public void setMapId(int map_id) {

    }

    @Override
    public void setMonsters(boolean monsters) {

    }

    @Override
    public void setNumBonus(int numBonus) {

    }

    @Override
    public void setMaxVictories(int maxVictories) {

    }

    @Override
    public void setCurrentVictories(int[] currentVictories) {

    }

    @Override
    public void setGame(Game game) {

    }

    @Override
    public void update(float delta) {

    }


    @Override
    public void render(float delta) {

        super.render(delta);
        stage.act();


        if(toDispose || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
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

}

