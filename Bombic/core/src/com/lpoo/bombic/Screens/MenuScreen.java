package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameAssetManager;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.Constants;

public class MenuScreen extends AbstractScreen{
    private Image mouse, backgroundImg;

    private Table table;

    private Image storyModeLabel, deathmatchLabel, monstersInfoLabel, helpLabel, creditsLabel, quitLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    private float label_height, max_label_width;

    private static final float PADDING = Constants.V_HEIGHT / 20;
    private static final float DIVIDER = (Constants.V_HEIGHT / 20) / Constants.PPM;

    public MenuScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {

        createImages();

        createTable();

        stage.addActor(backgroundImg);

        stage.addActor(table);

        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2  - mouse.getHeight() / 2;
        limitDown = (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2 ;
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
    }

    private void createImages(){
        mouse = new Image(bombicGame.getGam().manager.get("mouse.png", Texture.class));
        backgroundImg = new Image(bombicGame.getGam().manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        storyModeLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelStory.png", Texture.class));
        deathmatchLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelDeathmatch.png", Texture.class));
        monstersInfoLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelMonstersInfo.png", Texture.class));
        helpLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelHelp.png", Texture.class));
        creditsLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelCredits.png", Texture.class));
        quitLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelQuit.png",Texture.class));
    }

    private void createTable(){
        table = new Table();
        table.center();
        table.setFillParent(true);

        label_height = storyModeLabel.getHeight() * DIVIDER;
        max_label_width = monstersInfoLabel.getWidth() * DIVIDER;

        table.add(storyModeLabel).size(storyModeLabel.getWidth() * DIVIDER, label_height);
        table.row();
        table.add(deathmatchLabel).size(deathmatchLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(monstersInfoLabel).size(monstersInfoLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(helpLabel).size(helpLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(creditsLabel).size(creditsLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(quitLabel).size(quitLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
    }

    @Override
    public void setAvailableLevels(int level) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void update(float delta) {

    }

    private void chooseOptions(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp){
            mouse.setPosition(mouse.getX(), mouse.getY() + (label_height + PADDING));
            selectedOption--;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown){
            mouse.setPosition(mouse.getX(), mouse.getY() - (label_height + PADDING));
            selectedOption++;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            openNewMenu(5);
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            openNewMenu(selectedOption);
        }
    }

    private void openNewMenu(int option){
        switch (option){
            case 0:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
                break;
            case 1:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH);
                break;
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("3");
                break;
            case 4:
                System.out.println("4");
                break;
            case 5:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        chooseOptions();


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
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level){

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

}
