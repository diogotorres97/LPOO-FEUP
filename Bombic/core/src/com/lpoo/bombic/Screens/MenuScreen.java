package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class MenuScreen implements Screen{

    public Stage stage;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Texture background;
    private Image mouse;

    private Bombic game;

    private Label storyModeLabel;
    private float limitUp, limitDown;

    private int selectedOption;

    public MenuScreen(Bombic game) {
        this.game = game;

        //create cam used to follow bomber through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Bombic.V_WIDTH , Bombic.V_HEIGHT, gamecam);

        stage = new Stage(gamePort, game.batch);

        //showing the whole menu
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        background = new Texture(Gdx.files.internal("background.png"));
        mouse = new Image(new Texture(Gdx.files.internal("mouse.png")));

        //define a table used to show bombers info
        Table table = new Table();
        //Top-Align table
        table.center();
        //make the table fill the entire stage
        table.setFillParent(true);

        storyModeLabel = new Label("Story Mode", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        storyModeLabel.setFontScale(2);
        Label deathmatchLabel = new Label("Deathmatch", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        deathmatchLabel.setFontScale(2);
        Label monstersInfoLabel = new Label("Monsters info", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        monstersInfoLabel.setFontScale(2);
        Label helpLabel = new Label("Help", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        helpLabel.setFontScale(2);
        Label creditsLabel = new Label("Credits", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        creditsLabel.setFontScale(2);
        Label quitLabel = new Label("Quit", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        quitLabel.setFontScale(2);

        table.add(storyModeLabel).expandX();
        table.row();
        table.add(deathmatchLabel).expandX().padTop(20);
        table.row();
        table.add(monstersInfoLabel).expandX().padTop(20);
        table.row();
        table.add(helpLabel).expandX().padTop(20);
        table.row();
        table.add(creditsLabel).expandX().padTop(20);
        table.row();
        table.add(quitLabel).expandX().padTop(20);
        table.row();
        Vector2 coords = new Vector2(0, 0);
       Gdx.app.log("LENGTH", "" + storyModeLabel.getWidth());
        table.getChildren().get(0).getY();

        //add our table to the stage
        stage.addActor(table);

        limitUp = stage.getHeight() - (stage.getHeight() - table.getCells().size * (storyModeLabel.getHeight() + 20)) / 2 + mouse.getHeight() / 2;
        limitDown = (stage.getHeight() - table.getCells().size * (storyModeLabel.getHeight() + 20)) / 2 - mouse.getHeight() / 2;

        mouse.setPosition(stage.getWidth() / 2 - storyModeLabel.getWidth() / 2 - mouse.getWidth() * 3, limitUp);
        stage.addActor(mouse);

        selectedOption = 0;


    }

    @Override
    public void show() {

    }

    private void chooseOptions(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && mouse.getY() < limitUp){
            mouse.setPosition(mouse.getX(), mouse.getY() + (storyModeLabel.getHeight() + 20));
            selectedOption--;
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && mouse.getY() > limitDown){
            mouse.setPosition(mouse.getX(), mouse.getY() - (storyModeLabel.getHeight() + 20));
            selectedOption++;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            openNewMenu(selectedOption);
        }
    }

    private void openNewMenu(int option){
        switch (option){
            case 0:
                System.out.println("0");
                break;
            case 1:
                System.out.println("1");
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
                System.out.println("5");
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
        //game.batch.draw(background, 0, 0);
        game.batch.end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
