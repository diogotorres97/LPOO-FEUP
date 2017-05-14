package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 05/05/2017.
 */

public class StoryModeScreen extends AbstractScreen {

    private Image startLabel, numberPlayersLabel, chooseLevelLabel,backLabel;
    private Image mouse, backgroundImg;
    private Image players[];
    private TextureAtlas atlasPlayers;
    private int numPlayers;

    private int currentLevel;
    private int numLevels;
    private int availableLevels;

    private Stack stackPlayersImgs;
    private Table overlay, table;

    private float label_height, max_label_width;
    private float limitUp, limitDown;

    private int selectedOption;

    private static final float PADDING = Constants.V_HEIGHT / 20;
    private static final float DIVIDER = (Constants.V_HEIGHT / 20) / Constants.PPM;

    public StoryModeScreen(final Bombic bombicGame) {
        super(bombicGame);
        //TODO receber de um ficheiro binario
        availableLevels = 3;
        currentLevel = 1;
        numLevels = 3;
    }

    @Override
    public void show() {

        numPlayers = 1;

        createImages();

        createTable();

        stage.addActor(backgroundImg);

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 2) * (label_height + PADDING)) / 2 - PADDING / 2 - mouse.getHeight();
        limitDown = (stage.getHeight() - (table.getCells().size - 2) * (label_height + PADDING)) / 2 ;
        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
    }

    public void createImages(){
        mouse = new Image(bombicGame.getGam().manager.get("mouse.png", Texture.class));
        backgroundImg = new Image(bombicGame.getGam().manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        players = new Image[4];
        atlasPlayers = bombicGame.getGam().manager.get("players_imgs.atlas", TextureAtlas.class);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasPlayers.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        startLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelStart.png", Texture.class));
        numberPlayersLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelNumPlayers.png", Texture.class));
        chooseLevelLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelChooseLevel.png", Texture.class));
        backLabel = new Image(bombicGame.getGam().manager.get("menus/labels/labelBack.png", Texture.class));

    }

    private void createTable(){
        table = new Table();
        table.center();
        table.setFillParent(true);

        stackPlayersImgs = new Stack();

        overlay = new Table();
        overlay.add(players[0]);
        stackPlayersImgs.add(overlay);

        label_height = startLabel.getHeight() * DIVIDER;
        max_label_width = chooseLevelLabel.getWidth() * DIVIDER;

        table.add(stackPlayersImgs).padBottom(PADDING / 2);
        table.row();
        table.add(startLabel).size(startLabel.getWidth() * DIVIDER, label_height).expandX();
        table.row();
        table.add(numberPlayersLabel).size(numberPlayersLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(chooseLevelLabel).size(chooseLevelLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(backLabel).size(backLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
    }
    @Override
    public void setAvailableLevels(int level) {
        availableLevels = level;
    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level){
        this.currentLevel = level;
    }

    @Override
    public void setMapId(int map_id) {

    }

    @Override
    public void setGame(Game game) {

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
    public void update(float delta) {

    }

    private void chooseOptions() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp) {
            mouse.setPosition(mouse.getX(), mouse.getY() + (label_height + PADDING));
            selectedOption--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown) {
            mouse.setPosition(mouse.getX(), mouse.getY() - (label_height + PADDING));
            selectedOption++;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            pressedEnter(selectedOption);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pressedEnter(3);
        }

        if (selectedOption == 1) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && numPlayers > 1) {
                overlay.removeActor(players[numPlayers - 1]);
                numPlayers--;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && numPlayers < 4) {
                overlay.add(players[numPlayers]);
                numPlayers++;
            }
        }
    }

    private void pressedEnter(int option) {
        switch (option) {
            case 0:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setNumPlayers(numPlayers);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setCurrentLevel(currentLevel);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setAvailableLevels(availableLevels);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setNumLevel(numLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS);
                break;
            case 1:
                if (numPlayers < 4) {
                    overlay.add(players[numPlayers]);
                    numPlayers++;
                } else {
                    overlay.removeActor(players[numPlayers - 1]);
                    numPlayers--;
                    overlay.removeActor(players[numPlayers - 1]);
                    numPlayers--;
                    overlay.removeActor(players[numPlayers - 1]);
                    numPlayers--;
                }
                break;
            case 2:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setCurrentLevel(currentLevel);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setAvailableLevels(availableLevels);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setNumLevel(numLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.CHOOSE_LEVEL);
                break;
            case 3:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
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
