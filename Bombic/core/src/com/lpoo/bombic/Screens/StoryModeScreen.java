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
 * Created by Rui Quaresma on 05/05/2017.
 */

public class StoryModeScreen implements Screen {
    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture background;
    private Image storyText;
    private Image mouse;
    private Image players[];
    private TextureAtlas atlasPlayers;
    private int numPlayers;

    private Stack stackPlayersImgs;
    private Table overlay;

    private Bombic game;

    private Label startLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    public StoryModeScreen(Bombic game) {
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH , Bombic.V_HEIGHT, gamecam);

        stage = new Stage(gamePort, game.batch);

        stackPlayersImgs = new Stack();
        numPlayers = 1;

        background = new Texture(Gdx.files.internal("background.png"));
        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));
        players = new Image[4];
        atlasPlayers = new TextureAtlas("players_imgs.atlas");

        for(int  i = 0; i< players.length; i++){
            players[i] = new Image(new TextureRegion(atlasPlayers.findRegion("players_imgs"),i * 50, 0, 50, 50 ));
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

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (startLabel.getHeight() + 20)) / 2 - 20 - mouse.getHeight() / 2 ;
        limitDown = (stage.getHeight() - table.getCells().size * (startLabel.getHeight() + 20)) / 2 - mouse.getHeight() / 2;

        mouse.setPosition(stage.getWidth() / 2 - numberPlayersLabel.getWidth() / 2 - mouse.getWidth() * 3, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;

    }

    @Override
    public void show() {

    }

    private void chooseOptions(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp){
            mouse.setPosition(mouse.getX(), mouse.getY() + (startLabel.getHeight() + 20));
            selectedOption--;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown){
            mouse.setPosition(mouse.getX(), mouse.getY() - (startLabel.getHeight() + 20));
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
                overlay.removeActor(players[numPlayers-1]);
                numPlayers--;
            }else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && numPlayers < 4){
                overlay.add(players[numPlayers]);
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
