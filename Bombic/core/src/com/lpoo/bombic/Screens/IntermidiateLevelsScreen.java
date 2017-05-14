package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Logic.StoryGame;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 06/05/2017.
 */

public class IntermidiateLevelsScreen extends AbstractScreen {

    private int numPlayers;
    private int level;

    private Image[] backgrounds;
    private Image showingImage;

    private int currentLevel;
    private int numLevels;
    private int availableLevels;

    public IntermidiateLevelsScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        createImages();
    }

    private void createImages() {
        backgrounds = new Image[numLevels + 2];
        for (int i = 0; i < 5; i++)
            backgrounds[i] = new Image(bombicGame.getGam().manager.get("menus/level" + i + ".png", Texture.class));


        showingImage = backgrounds[level];
        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage = new Stage(gamePort, bombicGame.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(showingImage);

        increaseAvailableLevels();

        //TODO: criar as varias textures e meter num array, ao criar o screen esse sera o fundo
    }

    private void increaseAvailableLevels() {
        if (currentLevel > availableLevels)
            availableLevels = currentLevel;
    }

    @Override
    public void setAvailableLevels(int level) {
        this.availableLevels = level;
    }

    @Override
    public void setNumLevel(int num) {
        this.numLevels = num;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setCurrentLevel(int level) {
        if (level != 0)
            currentLevel = level;
        this.level = level;

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

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            bombicGame.gsm.getScreen(GameScreenManager.STATE.STORY).setAvailableLevels(availableLevels);
            bombicGame.gsm.getScreen(GameScreenManager.STATE.STORY).setAvailableLevels(1);
            bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (level == 0) {
                level = currentLevel;
                stage.getActors().get(0).clear();
                showingImage = backgrounds[level];
                showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
                stage.addActor(showingImage);
            } else if (level > numLevels) {
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
            } else {
                Game game = new StoryGame(level, numPlayers, 1);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.PLAY).setGame(game);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.PLAY);
            }

        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput();

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
