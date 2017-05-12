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

    private Texture background;
    private Image storyText;
    private Image mouse;
    private Image players[];
    private TextureAtlas atlasPlayers;
    private int numPlayers;

    private Stack stackPlayersImgs;
    private Table overlay;

    private Label startLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    public StoryModeScreen(Bombic bombicGame) {
        super(bombicGame);

    }

    @Override
    public void show() {
        stackPlayersImgs = new Stack();
        numPlayers = 1;

        background = new Texture(Gdx.files.internal("background.png"));
        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));
        players = new Image[4];
        atlasPlayers = new TextureAtlas("players_imgs.atlas");

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasPlayers.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }
        /*storyText = new Image(new Texture(Gdx.files.internal("labelStory.png")));
        storyText.setScaleY((gamePort.getWorldHeight() / 30)/storyText.getHeight());
        storyText.setScaleX((gamePort.getWorldHeight() / 8)/storyText.getWidth());*/

        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
        table.setFillParent(true);

        startLabel = new Label("Start", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        startLabel.setFontScale(2);
        Label numberPlayersLabel = new Label("Number of players", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numberPlayersLabel.setFontScale(2);
        Label chooseLevelLabel = new Label("Choose Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        chooseLevelLabel.setFontScale(2);
        Label backLevel = new Label("Back", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        backLevel.setFontScale(2);

        overlay = new Table();
        overlay.add(players[0]);
        stackPlayersImgs.add(overlay);

        table.add(stackPlayersImgs).padBottom(10);
        table.row();
        table.add(startLabel).expandX();
        table.row();
        table.add(numberPlayersLabel).expandX().padTop(20);
        table.row();
        table.add(chooseLevelLabel).expandX().padTop(20);
        table.row();
        table.add(backLevel).expandX().padTop(20);
        table.row();

        Image backImg = new Image(background);
        backImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage.addActor(backImg);

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (startLabel.getHeight() + 20)) / 2 - 20 - mouse.getHeight() / 2;
        limitDown = (stage.getHeight() - table.getCells().size * (startLabel.getHeight() + 20)) / 2 - mouse.getHeight() / 2;

        mouse.setPosition(stage.getWidth() / 2 - numberPlayersLabel.getWidth() / 2 - mouse.getWidth() * 3, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;
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
            mouse.setPosition(mouse.getX(), mouse.getY() + (startLabel.getHeight() + 20));
            selectedOption--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown) {
            mouse.setPosition(mouse.getX(), mouse.getY() - (startLabel.getHeight() + 20));
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
                bombicGame.gsm.getScreen(GameScreenManager.STATE.INTERMIDIATE_LEVELS).setCurrentLevel(bombicGame.getCurrentLevel());
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
