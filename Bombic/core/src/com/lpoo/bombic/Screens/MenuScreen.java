package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.AndroidController;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.isAndroid;

/**
 * Screen that represents the main menu
 */
public class MenuScreen extends AbstractScreen {
    private Image mouse, backgroundImg;

    private Table table;

    private Image storyModeLabel, deathmatchLabel, networkingLabel, settingsLabel, monstersInfoLabel, helpLabel, creditsLabel, quitLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    private float label_height, max_label_width;

    private AndroidController androidController;

    private static final float PADDING = Constants.V_HEIGHT / 20;
    private static final float DIVIDER = (Constants.V_HEIGHT / 20) / Constants.PPM;

    /**
     * Constructor
     * @param bombicGame
     */
    public MenuScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        androidController = new AndroidController(bombicGame.batch, 2);

        createImages();

        createTable();

        stage.addActor(backgroundImg);

        stage.addActor(table);

        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2 - mouse.getHeight() / 2;
        limitDown = (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2;
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
    }

    private void createImages() {
        mouse = new Image(gam.manager.get("mouse.png", Texture.class));
        backgroundImg = new Image(gam.manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        storyModeLabel = new Image(gam.manager.get("menus/labels/labelStory.png", Texture.class));
        deathmatchLabel = new Image(gam.manager.get("menus/labels/labelDeathmatch.png", Texture.class));
        networkingLabel = new Image(gam.manager.get("menus/labels/labelNetworking.png", Texture.class));
        settingsLabel = new Image(gam.manager.get("menus/labels/labelSettings.png", Texture.class));
        monstersInfoLabel = new Image(gam.manager.get("menus/labels/labelMonstersInfo.png", Texture.class));
        helpLabel = new Image(gam.manager.get("menus/labels/labelHelp.png", Texture.class));
        creditsLabel = new Image(gam.manager.get("menus/labels/labelCredits.png", Texture.class));
        quitLabel = new Image(gam.manager.get("menus/labels/labelQuit.png", Texture.class));
    }

    private void createTable() {
        table = new Table();
        table.center();
        table.setFillParent(true);

        label_height = storyModeLabel.getHeight() * DIVIDER;
        max_label_width = monstersInfoLabel.getWidth() * DIVIDER;

        table.add(storyModeLabel).size(storyModeLabel.getWidth() * DIVIDER, label_height);
        table.row();
        if (!isAndroid) {
            table.add(deathmatchLabel).size(deathmatchLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
            table.row();
            table.add(networkingLabel).size(networkingLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
            table.row();
        }
        table.add(monstersInfoLabel).size(monstersInfoLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(settingsLabel).size(settingsLabel.getWidth() * DIVIDER, label_height).padTop(PADDING);
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
    public void setMultiGame(boolean multiGame) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void update(float delta) {

    }

    private void chooseOptions() {
        upAndDownPressed();

        if (isAndroid) {
            if (androidController.getEscape()) {
                androidOpenNewMenu(5);
                androidController.setEscape(false);
            }
            if (androidController.getBomb()) {
                androidOpenNewMenu(selectedOption);
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
                openNewMenu(7);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                openNewMenu(selectedOption);
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

    private void androidOpenNewMenu(int option) {
        switch (option) {
            case 0:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
                break;
            case 1:
                System.out.println("2");
                break;
            case 2:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.SETTINGS);
                break;
            case 3:
                System.out.println("4");
                break;
            case 4:
                break;
            case 5:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }

    private void openNewMenu(int option) {
        switch (option) {
            case 0:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.STORY);
                break;
            case 1:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH);
                break;
            case 2:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumPlayers(2);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMapId(1);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMonsters(false);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumBonus(0);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMaxVictories(1);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setCurrentVictories(new int[2]);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMultiGame(true);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE);
                break;
            case 3:
                System.out.println("2");
                break;
            case 4:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.SETTINGS);
                break;
            case 5:
                System.out.println("4");
                break;
            case 6:

                break;
            case 7:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        if(isAndroid) {
            androidController.handle();
            androidController.stage.draw();
        }
        chooseOptions();


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

}
