package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    public IntermidiateLevelsScreen(Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        createImages();
    }

    private void createImages(){
        backgrounds = new Image[bombicGame.getNumLevel() + 2];
        backgrounds[0] = new Image(new Texture(Gdx.files.internal("menus/level0.png")));
        backgrounds[1] = new Image(new Texture(Gdx.files.internal("menus/level1.png")));
        backgrounds[2] = new Image(new Texture(Gdx.files.internal("menus/level2.png")));
        backgrounds[3] = new Image(new Texture(Gdx.files.internal("menus/level3.png")));
        backgrounds[4] = new Image(new Texture(Gdx.files.internal("menus/level4.png")));
        Gdx.app.log("LEVEL", "" + level);
        showingImage = backgrounds[level];
        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage = new Stage(gamePort, bombicGame.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(showingImage);

        //TODO: criar as varias textures e meter num array, ao criar o screen esse sera o fundo
    }

    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public void setCurrentLevel(int level){
        Gdx.app.log("LEVEL", "" + level);
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

    private void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            bombicGame.setScreen(new StoryModeScreen(bombicGame));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(level == 0){
                level = bombicGame.getCurrentLevel();
                stage.getActors().get(0).clear();
                showingImage= backgrounds[level];
                showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
                stage.addActor(showingImage);
            }else if(level > bombicGame.getNumLevel()){
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
            }else{
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
