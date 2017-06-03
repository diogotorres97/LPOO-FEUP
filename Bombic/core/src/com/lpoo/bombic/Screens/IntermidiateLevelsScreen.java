package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Logic.GameLogic.StoryGame;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.AndroidController;
import com.lpoo.bombic.Tools.Constants;

import java.io.File;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.isAndroid;
import static com.lpoo.bombic.Bombic.soundsOn;
import static com.lpoo.bombic.Tools.StorageLevels.StorageLevels;

/**
 * Screen in between story mode levels
 */
public class IntermidiateLevelsScreen extends AbstractScreen {

    private int numPlayers;
    private int level;

    private Image[] backgrounds;
    private Image showingImage;

    private int currentLevel;
    private int numLevels;
    private int availableLevels;

    private AndroidController androidController;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public IntermidiateLevelsScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        androidController = new AndroidController(bombicGame.batch, 2);
        createImages();

    }

    private void createImages() {
        backgrounds = new Image[numLevels + 2];
        for (int i = 0; i < 13; i++)
            backgrounds[i] = new Image(gam.manager.get("menus/level" + i + ".png", Texture.class));


        showingImage = backgrounds[level];
        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        stage.addActor(showingImage);

        increaseAvailableLevels();
    }

    private void increaseAvailableLevels() {
        if (currentLevel > availableLevels) {
            availableLevels = currentLevel;
            File file = new File(Constants.LEVELFILE);
            StorageLevels(availableLevels, file);
        }

    }


    @Override
    public void setAvailableLevels(int level) {
        this.availableLevels = level;
    }

    @Override
    public void setMultiGame(boolean multiGame) {

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

        if (isAndroid) {
            if (androidController.getEscape()) {
                bombicGame.gsm.getScreen(GameScreenManager.STATE.STORY).setAvailableLevels(availableLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
                androidController.setEscape(false);
            }
            if (androidController.getBomb() && androidController.isResetBomb()) {
                androidController.setResetBomb(false);
                pressedEnter();
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (soundsOn)
                    soundEscape.play();
                bombicGame.gsm.getScreen(GameScreenManager.STATE.STORY).setAvailableLevels(availableLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (soundsOn)
                    soundEnter.play();
                pressedEnter();
            }
        }
    }

    private void pressedEnter() {

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

    @Override
    public void render(float delta) {
        super.render(delta);
        if (isAndroid) {
            androidController.handle();
            androidController.stage.draw();
        }
        handleInput();

    }

}
