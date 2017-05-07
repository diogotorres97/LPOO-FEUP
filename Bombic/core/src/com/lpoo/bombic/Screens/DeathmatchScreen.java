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

/**
 * Created by Rui Quaresma on 06/05/2017.
 */

public class DeathmatchScreen implements Screen {
    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

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

    private Stack stackMenuIcons;
    private Table overlayPlayers;

    private Bombic game;

    private Label fightLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    public DeathmatchScreen(Bombic game) {
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH , Bombic.V_HEIGHT, gamecam);

        stage = new Stage(gamePort, game.batch);

        stackMenuIcons = new Stack();
        numPlayers = 2;

        background = new Texture(Gdx.files.internal("background.png"));
        box_background = new Image(new Texture(Gdx.files.internal("menus/box_dm.png")));

        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));

        players = new Image[4];
        atlasMenuIcons = new TextureAtlas("menu_icons.atlas");

        for(int  i = 0; i< players.length; i++){
            players[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_imgs"),i * 50, 0, 50, 50 ));
        }

        bonus = new Image[13];
        atlasBonus = new TextureAtlas("bonus.atlas");
        for(int  i = 0; i< bonus.length; i++){
            bonus[i] = new Image(new TextureRegion(atlasBonus.findRegion("bonus"),i * 50, 0, 50, 50 ));
        }

        enemy = new Image[2];
        for(int  i = 0; i< enemy.length; i++){
            enemy[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("enemy"),i * 50, 0, 50, 50 ));
        }

        victories = new Image[9];
        for(int  i = 0; i< victories.length; i++){
            victories[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"),0, 0, 34, 64 ));
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

        fightLabel = new Label("FIGHT!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        fightLabel.setFontScale(2);
        Label mapLabel = new Label("MAP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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
        imgTable.add(box_background).size(600, 180);
        stackMenuIcons.add(imgTable);

        overlayPlayers = new Table();

        overlayPlayers.add(victories[0]).left();
        overlayPlayers.row();
        overlayPlayers.add(players[0]).left();
        overlayPlayers.add(players[1]).left();

        overlayPlayers.add(enemy[0]).right();
        overlayPlayers.row();
        overlayPlayers.add(bonus[0]);
        overlayPlayers.add(bonus[1]);
        overlayPlayers.add(bonus[2]);
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

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (fightLabel.getHeight() + 10)) / 2 - 10 - mouse.getHeight() / 2 ;
        limitDown = (stage.getHeight() - table.getCells().size * (fightLabel.getHeight() + 10)) / 2 - mouse.getHeight() / 2;

        mouse.setPosition(stage.getWidth() / 2 - numberPlayersLabel.getWidth() / 2 - mouse.getWidth() * 3, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;

    }

    @Override
    public void show() {

    }

    private void chooseOptions(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp){
            mouse.setPosition(mouse.getX(), mouse.getY() + (fightLabel.getHeight() + 20));
            selectedOption--;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown){
            mouse.setPosition(mouse.getX(), mouse.getY() - (fightLabel.getHeight() + 20));
            selectedOption++;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            openNewMenu(selectedOption);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            openNewMenu(3);
        }

        if(selectedOption == 1){
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && numPlayers>1){
                overlayPlayers.removeActor(players[numPlayers-1]);
                numPlayers--;
            }else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && numPlayers < 4){
                overlayPlayers.add(players[numPlayers]);
                numPlayers++;
            }
        }
    }

    private void openNewMenu(int option){
        switch (option){
            case 0:
                game.setScreen(new IntermidiateLevelsScreen(game, numPlayers, game.getCurrentLevel()));
                dispose();
                break;
            case 1:
                System.out.println("1");
                break;
            case 2:
                game.setScreen(new ChooseLevelScreen(game));
                dispose();
                break;
            case 3:
                game.setScreen(new MenuScreen(game));
                dispose();
                break;
            default:
                break;
        }
    }

    @Override
    public void render(float delta) {

        gamecam.update();
        chooseOptions();

        //Clear the menu screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        /*Gdx.app.log("X", gamePort.getWorldWidth() + "");
        Gdx.app.log("Y", gamePort.getWorldHeight() + "");*/
        game.batch.draw(background, 0, 0, gamePort.getWorldWidth(), gamePort.getWorldHeight());
        game.batch.end();

        stage.draw();

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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
