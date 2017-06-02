package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.AndroidController;
import com.lpoo.bombic.Tools.Constants;

import java.io.File;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.isAndroid;
import static com.lpoo.bombic.Bombic.soundsOn;
import static com.lpoo.bombic.Tools.StorageLevels.loadLevels;

/**
 * Screen used to choose story mode options
 */

public class StoryModeScreen extends AbstractScreen {

    private Image startLabel, numberPlayersLabel, chooseLevelLabel, backLabel;
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

    private AndroidController androidController;

    private static final float PADDING = Constants.V_HEIGHT / 20;
    private static final float DIVIDER = (Constants.V_HEIGHT / 20) / Constants.PPM;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public StoryModeScreen(final Bombic bombicGame) {
        super(bombicGame);
        File file = new File(Constants.LEVELFILE);
        if (file.exists())
            availableLevels = loadLevels(file);
        else
            availableLevels = 1;

        currentLevel = 1;
        numLevels = 4;
    }


    @Override
    public void show() {

        androidController = new AndroidController(bombicGame.batch, 2);

        numPlayers = 1;

        createImages();

        createTable();

        stage.addActor(backgroundImg);

        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 2) * (label_height + PADDING)) / 2 - PADDING / 2 - mouse.getHeight();
        limitDown = (stage.getHeight() - (table.getCells().size - 2) * (label_height + PADDING)) / 2;
        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
    }

    public void createImages() {
        mouse = new Image(gam.manager.get("mouse.png", Texture.class));
        backgroundImg = new Image(gam.manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        players = new Image[4];
        atlasPlayers = gam.manager.get("players_imgs.atlas", TextureAtlas.class);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasPlayers.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        startLabel = new Image(gam.manager.get("menus/labels/labelStart.png", Texture.class));
        numberPlayersLabel = new Image(gam.manager.get("menus/labels/labelNumPlayers.png", Texture.class));
        chooseLevelLabel = new Image(gam.manager.get("menus/labels/labelChooseLevel.png", Texture.class));
        backLabel = new Image(gam.manager.get("menus/labels/labelBack.png", Texture.class));

    }

    private void createTable() {
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
        if (!isAndroid) {
            table.add(numberPlayersLabel).size(numberPlayersLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
            table.row();
        }
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
    public void setMultiGame(boolean multiGame) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level) {
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
        upAndDownPressed();

        if (isAndroid) {
            if (androidController.getEscape()) {
                androidPressedEnter(2);
                androidController.setEscape(false);
            }
            if (androidController.getBomb()) {
                androidPressedEnter(selectedOption);
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (soundsOn)
                    soundEscape.play();
                pressedEnter(3);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (soundsOn)
                    soundEnter.play();
                pressedEnter(selectedOption);
            }
        }

        if (!isAndroid) {
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
    }

    private void upAndDownPressed() {
        int dir = androidController.getDir();
        if (isAndroid) {
            if (androidController.isReset())
                if (dir == Input.Keys.UP && mouse.getY() < limitUp) {
                    mouse.setPosition(mouse.getX(), mouse.getY() + (label_height + PADDING));
                    selectedOption--;
                    androidController.setReset(false);
                } else if (dir == Input.Keys.DOWN && mouse.getY() > limitDown) {
                    mouse.setPosition(mouse.getX(), mouse.getY() - (label_height + PADDING));
                    selectedOption++;
                    androidController.setReset(false);
                }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp) {
                mouse.setPosition(mouse.getX(), mouse.getY() + (label_height + PADDING));
                selectedOption--;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown) {
                mouse.setPosition(mouse.getX(), mouse.getY() - (label_height + PADDING));
                selectedOption++;
            }
        }
    }

    private void androidPressedEnter(int option) {
        switch (option) {
            case 0:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setNumPlayers(numPlayers);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setCurrentLevel(currentLevel);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setAvailableLevels(availableLevels);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setNumLevel(numLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS);
                break;
            case 1:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setCurrentLevel(currentLevel);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setAvailableLevels(availableLevels);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.CHOOSE_LEVEL).setNumLevel(numLevels);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.CHOOSE_LEVEL);
                break;
            case 2:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
                break;
            default:
                break;
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
        if (isAndroid) {
            androidController.handle();
            androidController.stage.draw();
        }
        chooseOptions();
    }
}
