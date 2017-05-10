package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.DeathmatchGame;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.StoryGame;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class DeathmatchIntermidiateScreen implements Screen {
    public Stage stage;

    private Viewport gamePort;

    private int numPlayers;

    //private Table overlay;
    private Table table;

    private Bombic game;

    private Image players[];
    private Image players_dying[];
    private TextureAtlas atlasMenuIcons;

    private Image[] backgrounds;
    private Image showingImage;

    private boolean hasEnemies;
    private int numBonus;
    private int map_id;
    private int max_victories;
    private int[] current_vics;

    private boolean gameWon;

    public DeathmatchIntermidiateScreen(Bombic game, int numPlayers, int map_id, boolean hasEnemies, int numBonus, int max_victories, int[] current_vics) {
        this.game = game;
        this.map_id = map_id;
        this.numPlayers = numPlayers;
        this.hasEnemies = hasEnemies;
        this.numBonus = numBonus;
        this.max_victories = max_victories;
        this.current_vics = current_vics;

        atlasMenuIcons = new TextureAtlas("menu_icons.atlas");

        players_dying = new Image[numPlayers];
        players = new Image[numPlayers];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        for (int i = 0; i < players_dying.length; i++) {
            players_dying[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_dying_imgs"), i * 50, 0, 50, 56));
        }


        gameWon = false;

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);

        endGame();

        backgrounds = new Image[2];
        backgrounds[0] = new Image(new Texture(Gdx.files.internal("menus/dm_menu1.png")));
        backgrounds[1] = new Image(new Texture(Gdx.files.internal("menus/dm_menu2.png")));

        if (gameWon)
            showingImage = backgrounds[1];
        else
            showingImage = backgrounds[0];

        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(showingImage);


        table = new Table();

        table.left();

        table.setSize(gamePort.getWorldWidth() - gamePort.getWorldWidth() / 4, gamePort.getWorldHeight() / 3);
        table.setFillParent(true);


        if (gameWon) {
            for (int i = 0; i < players_dying.length; i++) {
                if (current_vics[i] != max_victories)
                    table.add(players_dying[i]).padLeft(50).padBottom(10);
                else
                    table.add(players[i]).padLeft(50).padBottom(10);
                for(int j = 0; j<current_vics[i]; j++){
                    Image vic_img = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"), 0, 0, 34, 64));
                    table.add(vic_img).padLeft(20).padBottom(10);
                }
                table.row();
            }
        } else {

            for (int i = 0; i < players.length; i++) {



                table.add(players[i]).left().padLeft(50).padBottom(10);
                for(int j = 0; j< current_vics[i]; j++){
                    Image vic_img = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"), 0, 0, 34, 64));
                    table.add(vic_img).padLeft(20).padBottom(10);
                }

                table.row();
            }
        }

        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new DeathmatchScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (gameWon) {
                game.setScreen(new DeathmatchScreen(game));
                dispose();
            } else {
                Game game1 = new DeathmatchGame(map_id, numPlayers, 2, hasEnemies, numBonus, max_victories, current_vics);
                game.setGame(game1);
                game.setScreen(new PlayScreen(game, game1));
                dispose();
            }

        }

    }

    private void endGame() {
        for (int i = 0; i < numPlayers; i++) {
            if (current_vics[i] == max_victories) {
                gameWon = true;
            }
        }
    }

    @Override
    public void render(float delta) {

        handleInput();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
