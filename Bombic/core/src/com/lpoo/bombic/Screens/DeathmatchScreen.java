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

import static com.lpoo.bombic.Bombic.gam;

/**
 * Screen used to choose deathmatch options
 */

public class DeathmatchScreen extends AbstractScreen {
    private Image box_background, backgroundImg, mouse;
    private Image players[];
    private Image bonus[];
    private Image enemy[];
    private Image victories[];

    private Image fightLabel, mapRandomLabel, map1Label, map2Label, map3Label, map4Label, map5Label, numberPlayersLabel, monstersLabel, numVictoriesLabel, bonusAvailableLabel, backLabel;
    private Image mapImg;
    private TextureAtlas atlasBonus;
    private TextureAtlas atlasMenuIcons;
    private int numPlayers;

    private Stack stackMenuIcons, bonusStack;
    private Table overlayPlayers, numPlayersEnemyTable, victoriesTable;
    private Image currEnemy;

    private float label_height, max_label_width;

    private float labelRandom_width, labelMap_width;

    private float limitUp, limitDown;

    private int map_id, maxVictories, numBonus;
    private boolean monsters;

    private int selectedOption;

    private Table table, imgTable, mapTable;

    private static final float PADDING = Constants.V_HEIGHT / 30;
    private static final float DIVIDER = (Constants.V_HEIGHT / 30) / Constants.PPM;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public DeathmatchScreen(final Bombic bombicGame) {
        super(bombicGame);

    }

    @Override
    public void show() {
        stackMenuIcons = new Stack();
        numPlayers = 2;

        map_id = 0;
        maxVictories = 1;
        numBonus = 3;
        monsters = false;

        createImages();
        createTable();

        stage.addActor(backgroundImg);

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - imgTable.getCells().get(0).getActor().getHeight() + mouse.getHeight() / 2 + PADDING;
        limitDown = stage.getHeight() - imgTable.getCells().get(0).getActor().getHeight() + PADDING - PADDING * DIVIDER - (table.getCells().size - 3) * (label_height + PADDING);
        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
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

    private void createImages() {
        backgroundImg = new Image(gam.manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        mouse = new Image(gam.manager.get("mouse.png", Texture.class));
        box_background = new Image(gam.manager.get("menus/box_dm.png", Texture.class));
        atlasMenuIcons = gam.manager.get("menu_icons.atlas", TextureAtlas.class);
        atlasBonus = gam.manager.get("bonus.atlas", TextureAtlas.class);

        players = new Image[4];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        bonus = new Image[9];

        for (int i = 0; i < bonus.length; i++) {
            bonus[i] = new Image(new TextureRegion(atlasBonus.findRegion("bonus"), i * 50, 0, 50, 50));
        }

        enemy = new Image[2];
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("enemy"), i * 50, 0, 50, 50));
        }
        currEnemy = enemy[0];

        victories = new Image[9];
        for (int i = 0; i < victories.length; i++) {
            victories[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"), 0, 0, 34, 64));
        }
        createLabelsImgs();
    }

    private void createLabelsImgs() {
        fightLabel = new Image(gam.manager.get("menus/labels/labelFight.png", Texture.class));
        mapRandomLabel = new Image(gam.manager.get("menus/labels/labelMapRandom.png", Texture.class));
        map1Label = new Image(gam.manager.get("menus/labels/labelMap1.png", Texture.class));
        map2Label = new Image(gam.manager.get("menus/labels/labelMap2.png", Texture.class));
        map3Label = new Image(gam.manager.get("menus/labels/labelMap3.png", Texture.class));
        map4Label = new Image(gam.manager.get("menus/labels/labelMap4.png", Texture.class));
        map5Label = new Image(gam.manager.get("menus/labels/labelMap5.png", Texture.class));
        monstersLabel = new Image(gam.manager.get("menus/labels/labelMonsters.png", Texture.class));
        numVictoriesLabel = new Image(gam.manager.get("menus/labels/labelNumVictories.png", Texture.class));
        bonusAvailableLabel = new Image(gam.manager.get("menus/labels/labelBonusAvailable.png", Texture.class));
        backLabel = new Image(gam.manager.get("menus/labels/labelBack.png", Texture.class));
        numberPlayersLabel = new Image(gam.manager.get("menus/labels/labelNumPlayers.png", Texture.class));
    }

    private void createTable() {
        table = new Table();
        table.center();
        table.setFillParent(true);

        imgTable = new Table();
        imgTable.add(box_background).size(gamePort.getWorldWidth() - (gamePort.getWorldWidth() * DIVIDER), gamePort.getWorldHeight() * (DIVIDER * 1.5f));
        stackMenuIcons.add(imgTable);

        label_height = fightLabel.getHeight() * DIVIDER;
        max_label_width = bonusAvailableLabel.getWidth() * DIVIDER;

        numPlayersEnemyTable = new Table().center();
        numPlayersEnemyTable.add(players[0]);
        numPlayersEnemyTable.add(players[1]);
        numPlayersEnemyTable.add(currEnemy);

        victoriesTable = new Table();
        victoriesTable.add(victories[0]).left();

        bonusStack = new Stack();
        for (int i = 0; i < numBonus; i++) {
            Table bonusTable = new Table();
            bonusTable.add(bonus[i]).padLeft(i * PADDING);
            bonusStack.add(bonusTable);

        }

        mapImg = mapRandomLabel;

        mapTable = new Table();
        mapTable.add(mapImg);
        labelRandom_width = mapRandomLabel.getWidth();
        labelMap_width = map1Label.getWidth();
        addItemstoTable();

    }

    private void addItemstoTable() {
        overlayPlayers = new Table();

        overlayPlayers.add(victoriesTable);
        overlayPlayers.row();
        overlayPlayers.add(numPlayersEnemyTable);

        overlayPlayers.row();
        overlayPlayers.add(bonusStack).center();
        stackMenuIcons.add(overlayPlayers);

        table.add(stackMenuIcons).padBottom(PADDING * DIVIDER).padTop(PADDING);
        table.row();
        table.add(fightLabel).size(fightLabel.getWidth() * DIVIDER, label_height).expandX();
        table.row();
        table.add(mapTable).size(labelRandom_width * DIVIDER, label_height).padTop(PADDING);
        table.row();
        table.add(numberPlayersLabel).size(numberPlayersLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(monstersLabel).size(monstersLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(numVictoriesLabel).size(numVictoriesLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(bonusAvailableLabel).size(bonusAvailableLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
        table.add(backLabel).size(backLabel.getWidth() * DIVIDER, label_height).expandX().padTop(PADDING);
        table.row();
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
            pressedEnter(6);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            pressedLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            pressedRight();
        }
    }

    private void setMapImage(int map_id) {
        switch (map_id) {
            case 0:
                mapTable.clear();
                mapImg = mapRandomLabel;
                mapTable.add(mapImg).size(labelRandom_width * DIVIDER, label_height);
                break;
            case 1:
                mapTable.clear();
                mapImg = map1Label;
                mapTable.add(mapImg).size(labelMap_width * DIVIDER, label_height);

                break;
            case 2:
                mapTable.clear();
                mapImg = map2Label;
                mapTable.add(mapImg).size(labelMap_width * DIVIDER, label_height);
                break;
            case 3:
                mapTable.clear();
                mapImg = map3Label;
                mapTable.add(mapImg).size(labelMap_width * DIVIDER, label_height);
                break;
            case 4:
                mapTable.clear();
                mapImg = map4Label;
                mapTable.add(mapImg).size(labelMap_width * DIVIDER, label_height);
                break;
            case 5:
                mapTable.clear();
                mapImg = map5Label;
                mapTable.add(mapImg).size(labelMap_width * DIVIDER, label_height);
                break;
            default:
                break;

        }

    }


    private void pressedEnter(int option) {
        switch (option) {
            case 0:
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumPlayers(numPlayers);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMapId(map_id);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMonsters(monsters);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumBonus(numBonus);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMaxVictories(maxVictories);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setCurrentVictories(new int[numPlayers]);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMultiGame(false);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE);
                break;
            case 1:
                pressedRightOpion1();
                break;
            case 2:
                pressedRightOpion2();
                break;
            case 3:
                pressedRightOpion3();
                break;
            case 4:
                pressedRightOpion4();
                break;
            case 5:
                pressedRightOpion5();
                break;
            case 6:
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
                break;
            default:
                break;
        }
    }

    private void pressedLeft() {
        switch (selectedOption) {
            case 1:
                pressedLeftOpion1();
                break;
            case 2:
                pressedLeftOpion2();
                break;
            case 3:
                pressedLeftOpion3();
                break;
            case 4:
                pressedLeftOpion4();
                break;
            case 5:
                pressedLeftOpion5();
                break;
            default:
                break;
        }
    }

    private void pressedLeftOpion1(){
        if (map_id > 0) {
            map_id--;
        } else {
            map_id = 5;
        }
        setMapImage(map_id);
    }
    private void pressedLeftOpion2(){
        if (numPlayers > 2) {
            numPlayersEnemyTable.removeActor(players[numPlayers - 1]);
            numPlayers--;
        } else {
            numPlayersEnemyTable.removeActor(currEnemy);
            numPlayersEnemyTable.add(players[numPlayers]);
            numPlayers++;
            numPlayersEnemyTable.add(players[numPlayers]);
            numPlayers++;
            numPlayersEnemyTable.add(currEnemy);

        }
    }
    private void pressedLeftOpion3(){
        numPlayersEnemyTable.removeActor(currEnemy);
        if (monsters) {
            currEnemy = enemy[0];
        } else {
            currEnemy = enemy[1];
        }
        numPlayersEnemyTable.add(currEnemy);
        monsters = !monsters;
    }
    private void pressedLeftOpion4(){
        if (maxVictories > 1) {
            victoriesTable.removeActor(victories[maxVictories - 1]);
            maxVictories--;
        } else {
            for (int i = 0; i < 8; i++) {
                victoriesTable.add(victories[maxVictories]);
                maxVictories++;
            }
        }

    }
    private void pressedLeftOpion5(){
        if (numBonus > 3) {

            bonusStack.getChildren().get(numBonus - 1).remove();
            numBonus--;
        } else {
            for (int i = 0; i < 6; i++) {
                Table bonusTable = new Table();
                bonusTable.add(bonus[numBonus]).center();
                bonusTable.padLeft(numBonus * 30);
                bonusStack.add(bonusTable);
                numBonus++;
            }
        }
    }


    private void pressedRight() {
        switch (selectedOption) {
            case 1:
                pressedRightOpion1();
                break;
            case 2:
                pressedRightOpion2();
                break;
            case 3:
                pressedRightOpion3();
                break;
            case 4:
                pressedRightOpion4();
                break;
            case 5:
                pressedRightOpion5();
                break;
            default:
                break;
        }
    }

    private void pressedRightOpion1() {
        if (map_id < 5) {
            map_id++;
        } else {
            map_id = 0;
        }
        setMapImage(map_id);
    }
    private void pressedRightOpion2() {
        if (numPlayers < 4) {
            numPlayersEnemyTable.removeActor(currEnemy);
            numPlayersEnemyTable.add(players[numPlayers]);
            numPlayersEnemyTable.add(currEnemy);
            numPlayers++;
        } else {
            numPlayersEnemyTable.removeActor(currEnemy);
            numPlayersEnemyTable.removeActor(players[numPlayers - 1]);
            numPlayers--;
            numPlayersEnemyTable.removeActor(players[numPlayers - 1]);
            numPlayers--;
            numPlayersEnemyTable.add(currEnemy);

        }
    }
    private void pressedRightOpion3() {
        numPlayersEnemyTable.removeActor(currEnemy);
        if (monsters) {
            currEnemy = enemy[0];
        } else {
            currEnemy = enemy[1];
        }
        numPlayersEnemyTable.add(currEnemy);
        monsters = !monsters;
    }
    private void pressedRightOpion4() {
        if (maxVictories < 9) {
            victoriesTable.add(victories[maxVictories]);
            maxVictories++;
        } else {
            for (int i = 0; i < 8; i++) {
                victoriesTable.removeActor(victories[maxVictories - 1]);
                maxVictories--;
            }
        }
    }
    private void pressedRightOpion5() {
        if (numBonus < 9) {
            Table bonusTable = new Table();
            bonusTable.add(bonus[numBonus]).center();
            bonusTable.padLeft(numBonus * 30);
            bonusStack.add(bonusTable);
            numBonus++;
        } else {
            for (int i = 0; i < 6; i++) {
                bonusStack.getChildren().get(numBonus - 1).remove();
                numBonus--;
            }
        }
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        chooseOptions();

    }

}
