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
import com.lpoo.bombic.Tools.Constants;

public class MenuScreen implements Screen{

    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture background;
    private Image mouse;

    private Bombic game;

    private Image storyModeLabel, deathmatchLabel, monstersInfoLabel, helpLabel, creditsLabel, quitLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    private float label_height, max_label_width;

    private static final float PADDING = Constants.V_HEIGHT / 20;
    private static final float DIVIDER = 0.3f;

    public MenuScreen(Bombic game) {
        this.game = game;

        //create cam used to follow player through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH , Constants.V_HEIGHT);

        stage = new Stage(gamePort, game.batch);

        background = new Texture(Gdx.files.internal("background.png"));
        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));

        storyModeLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelStory.png")));
        deathmatchLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelDeathmatch.png")));
        monstersInfoLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelMonstersInfo.png")));
        helpLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelHelp.png")));
        creditsLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelCredits.png")));
        quitLabel = new Image(new Texture(Gdx.files.internal("menus/labels/labelQuit.png")));

        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
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

        //add our table to the stage
        stage.addActor(table);

        mouse.setSize(stage.getWidth() / 27, stage.getHeight() / 20);

        limitUp = stage.getHeight() - (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2  - mouse.getHeight() / 2;
        limitDown = (stage.getHeight() - (table.getCells().size - 1) * (label_height + PADDING)) / 2 ;
        Gdx.app.log("a", ""+ limitDown);
        mouse.setPosition(stage.getWidth() / 2 - max_label_width / 2 - mouse.getWidth() * 2, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;

    }

    @Override
    public void show() {

    }

    private void chooseOptions(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp){
            mouse.setPosition(mouse.getX(), mouse.getY() + (label_height + PADDING));
            selectedOption--;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown){
            mouse.setPosition(mouse.getX(), mouse.getY() - (label_height + PADDING));
            selectedOption++;
            Gdx.app.log("a", ""+ mouse.getY());
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
                game.setScreen(new StoryModeScreen(game));
                dispose();
                break;
            case 1:
                game.setScreen(new DeathmatchScreen(game));
                dispose();
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
               /* dispose();
                Gdx.app.exit();
                System.exit(0);*/
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


        game.batch.begin();
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
