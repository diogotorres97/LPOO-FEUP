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

/**
 * Created by Rui Quaresma on 06/05/2017.
 */

public class DeathmatchScreen extends AbstractScreen {

    private Texture background;
    private Image storyText;
    private Image mouse;
    private Image box_background;
    private Image players[];
    private Image bonus[];
    private Image enemy[];
    private Image victories[];
    private TextureAtlas atlasBonus;
    private TextureAtlas atlasMenuIcons;
    private int numPlayers;

    private Stack stackMenuIcons, bonusStack;
    private Table overlayPlayers, numPlayersEnemyTable, victoriesTable;
    private Image currEnemy;

    private Label fightLabel, mapLabel;
    private float limitUp, limitDown;

    private int map_id, maxVictories, numBonus;
    private boolean monsters;

    private int selectedOption;

    public DeathmatchScreen(Bombic bombicGame) {
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
        /*storyText = new Image(new Texture(Gdx.files.internal("labelStory.png")));
        storyText.setScaleY((gamePort.getWorldHeight() / 30)/storyText.getHeight());
        storyText.setScaleX((gamePort.getWorldHeight() / 8)/storyText.getWidth());*/

        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
        table.setFillParent(true);

        fightLabel = new Label("FIGHT!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fightLabel.setFontScale(2);
        mapLabel = new Label("MAP: RANDOM", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mapLabel.setFontScale(2);
        Label numberPlayersLabel = new Label("Number of players", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numberPlayersLabel.setFontScale(2);
        Label monstersLabel = new Label("Monsters (y / n)", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        monstersLabel.setFontScale(2);
        Label numVictoriesLabel = new Label("Number of victories", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numVictoriesLabel.setFontScale(2);
        Label bonusAvailableLabel = new Label("Bonus available", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bonusAvailableLabel.setFontScale(2);
        Label backLevel = new Label("Back", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        backLevel.setFontScale(2);

        /*stackMenuIcons.add(box_background);*/

        Table imgTable = new Table();
        imgTable.add(box_background).size(500, 180);
        stackMenuIcons.add(imgTable);

        numPlayersEnemyTable = new Table().center();
        numPlayersEnemyTable.add(players[0]);
        numPlayersEnemyTable.add(players[1]);
        numPlayersEnemyTable.add(currEnemy);

        victoriesTable = new Table();
        victoriesTable.add(victories[0]).left();

        bonusStack = new Stack();
        for (int i = 0; i < numBonus; i++) {
            Table bonusTable = new Table();
            bonusTable.add(bonus[i]).padLeft(i * 30);
            bonusStack.add(bonusTable);

        }

        overlayPlayers = new Table();

        overlayPlayers.add(victoriesTable);
        overlayPlayers.row();
        overlayPlayers.add(numPlayersEnemyTable);

        overlayPlayers.row();
        overlayPlayers.add(bonusStack).center();
        stackMenuIcons.add(overlayPlayers);

        table.add(stackMenuIcons).padBottom(10).padTop(50);
        table.row();
        table.add(fightLabel).expandX();
        table.row();
        table.add(mapLabel).expandX().padTop(10);
        table.row();
        table.add(numberPlayersLabel).expandX().padTop(10);
        table.row();
        table.add(monstersLabel).expandX().padTop(10);
        table.row();
        table.add(numVictoriesLabel).expandX().padTop(10);
        table.row();
        table.add(bonusAvailableLabel).expandX().padTop(10);
        table.row();
        table.add(backLevel).expandX().padTop(10);
        table.row();

        Image backImg = new Image(background);
        backImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage.addActor(backImg);

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - imgTable.getCells().get(0).getActor().getHeight() + mouse.getHeight() / 2 + 10;
        limitDown = fightLabel.getHeight() + 10;
        mouse.setPosition(stage.getWidth() / 2 - numberPlayersLabel.getWidth() / 2 - mouse.getWidth() * 3, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
    }

    private void createImages(){
        background = new Texture(Gdx.files.internal("background.png"));
        box_background = new Image(new Texture(Gdx.files.internal("menus/box_dm.png")));

        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));

        players = new Image[4];
        atlasMenuIcons = new TextureAtlas("menu_icons.atlas");

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        bonus = new Image[13];
        atlasBonus = new TextureAtlas("bonus.atlas");
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
            mouse.setPosition(mouse.getX(), mouse.getY() + (fightLabel.getHeight() + 10));
            selectedOption--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown) {
            mouse.setPosition(mouse.getX(), mouse.getY() - (fightLabel.getHeight() + 10));
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


    private void pressedEnter(int option) {
        switch (option) {
            case 0:

                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumPlayers(numPlayers);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMapId(map_id);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMonsters(monsters);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setNumBonus(numBonus);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setMaxVictories(maxVictories);
                bombicGame.gsm.getScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE).setCurrentVictories(new int[numPlayers]);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.DEATHMATCH_INTERMIDIATE);
                break;
            case 1:
                if (map_id < 5) {
                    map_id++;
                    mapLabel.setText("MAP: " + map_id);
                } else {
                    map_id = 0;
                    mapLabel.setText("MAP: RANDOM");
                }
                break;
            case 2:
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
                break;
            case 3:
                numPlayersEnemyTable.removeActor(currEnemy);
                if (monsters) {
                    currEnemy = enemy[0];
                } else {
                    currEnemy = enemy[1];
                }
                numPlayersEnemyTable.add(currEnemy);
                monsters = !monsters;
                break;
            case 4:
                if (maxVictories < 9) {
                    victoriesTable.add(victories[maxVictories]);
                    maxVictories++;
                } else {
                    for (int i = 0; i < 8; i++) {
                        victoriesTable.removeActor(victories[maxVictories - 1]);
                        maxVictories--;
                    }
                }
                break;
            case 5:
                if (numBonus < 13) {
                    Table bonusTable = new Table();
                    bonusTable.add(bonus[numBonus]).center();
                    bonusTable.padLeft(numBonus * 30);
                    bonusStack.add(bonusTable);
                    numBonus++;
                } else {
                    for (int i = 0; i < 10; i++) {
                        bonusStack.getChildren().get(numBonus - 1).remove();
                        numBonus--;
                    }
                }

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
                if (map_id > 0) {
                    map_id--;
                    if(map_id == 0)
                        mapLabel.setText("MAP: RANDOM");
                    else
                        mapLabel.setText("MAP: " + map_id);
                }
                break;
            case 2:
                if (numPlayers > 2) {
                    numPlayersEnemyTable.removeActor(players[numPlayers - 1]);
                    numPlayers--;
                }
                break;
            case 3:
                numPlayersEnemyTable.removeActor(currEnemy);
                if (monsters) {
                    currEnemy = enemy[0];
                } else {
                    currEnemy = enemy[1];
                }
                numPlayersEnemyTable.add(currEnemy);
                monsters = !monsters;
                break;
            case 4:
                if (maxVictories > 1) {
                    victoriesTable.removeActor(victories[maxVictories - 1]);
                    maxVictories--;
                }
                break;
            case 5:
                if (numBonus > 3) {

                    bonusStack.getChildren().get(numBonus - 1).remove();
                    numBonus--;
                }
                break;
            default:
                break;
        }
    }

    private void pressedRight() {
        switch (selectedOption) {
            case 1:
                if (map_id < 5) {
                    map_id++;
                    mapLabel.setText("MAP: " + map_id);
                }
                break;
            case 2:
                if (numPlayers < 4) {
                    numPlayersEnemyTable.removeActor(currEnemy);
                    numPlayersEnemyTable.add(players[numPlayers]);
                    numPlayersEnemyTable.add(currEnemy);
                    numPlayers++;
                }
                break;
            case 3:
                numPlayersEnemyTable.removeActor(currEnemy);
                if (monsters) {
                    currEnemy = enemy[0];
                } else {
                    currEnemy = enemy[1];
                }
                numPlayersEnemyTable.add(currEnemy);
                monsters = !monsters;
                break;
            case 4:
                if (maxVictories < 9) {
                    victoriesTable.add(victories[maxVictories]);
                    maxVictories++;
                }
                break;
            case 5:
                if (numBonus < 13) {
                    Table bonusTable = new Table();
                    bonusTable.add(bonus[numBonus]).center();
                    bonusTable.padLeft(numBonus * 30);
                    bonusStack.add(bonusTable);
                    numBonus++;
                }
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
