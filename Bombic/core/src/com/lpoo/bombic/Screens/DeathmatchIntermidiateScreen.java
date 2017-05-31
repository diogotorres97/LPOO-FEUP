package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.DeathmatchGame;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Created by Rui Quaresma on 09/05/2017.
 */

public class DeathmatchIntermidiateScreen extends AbstractScreen {
    private int numPlayers;
    private Table table;

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

    public DeathmatchIntermidiateScreen(final Bombic bombicGame) {
        super(bombicGame);

    }

    @Override
    public void show() {

        gameWon = false;

        endGame();

        createImages();


        Gdx.input.setInputProcessor(stage);

        createTable();

        stage.addActor(showingImage);
        stage.addActor(table);
    }

    private void createImages() {
        atlasMenuIcons = gam.manager.get("menu_icons.atlas", TextureAtlas.class);

        players_dying = new Image[numPlayers];
        players = new Image[numPlayers];


        for (int i = 0; i < players.length; i++) {
            players[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_imgs"), i * 50, 0, 50, 50));
        }

        for (int i = 0; i < players_dying.length; i++) {
            players_dying[i] = new Image(new TextureRegion(atlasMenuIcons.findRegion("players_dying_imgs"), i * 50, 0, 50, 56));
        }

        backgrounds = new Image[2];
        backgrounds[0] = new Image(gam.manager.get("menus/dm_menu1.png", Texture.class));
        backgrounds[1] = new Image(gam.manager.get("menus/dm_menu2.png", Texture.class));

        if (gameWon) {
            showingImage = backgrounds[1];
        } else {
            showingImage = backgrounds[0];

        }

        showingImage.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
    }

    private void createTable() {
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
                for (int j = 0; j < current_vics[i]; j++) {
                    Image vic_img = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"), 0, 0, 34, 64));
                    table.add(vic_img).padLeft(20).padBottom(10);
                }
                table.row();
            }
        } else {

            for (int i = 0; i < players.length; i++) {


                table.add(players[i]).left().padLeft(50).padBottom(10);
                for (int j = 0; j < current_vics[i]; j++) {
                    Image vic_img = new Image(new TextureRegion(atlasMenuIcons.findRegion("victory"), 0, 0, 34, 64));
                    table.add(vic_img).padLeft(20).padBottom(10);
                }

                table.row();
            }
        }
    }

    @Override
    public void setAvailableLevels(int level) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void setCurrentLevel(int level) {

    }

    @Override
    public void setMapId(int map_id) {
        this.map_id = map_id;
    }

    @Override
    public void setMonsters(boolean monsters) {
        this.hasEnemies = monsters;
    }

    @Override
    public void setNumBonus(int numBonus) {
        this.numBonus = numBonus;
    }

    @Override
    public void setMaxVictories(int maxVictories) {
        this.max_victories = maxVictories;
    }

    @Override
    public void setCurrentVictories(int[] currentVictories) {
        this.current_vics = currentVictories;
    }

    @Override
    public void setGame(Game game) {

    }

    @Override
    public void update(float delta) {

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (gameWon) {
                bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
            } else {
                Game game = new DeathmatchGame(map_id, numPlayers, 2, hasEnemies, numBonus, max_victories, current_vics);

                bombicGame.gsm.getScreen(GameScreenManager.STATE.PLAY).setGame(game);
                bombicGame.gsm.setScreen(GameScreenManager.STATE.PLAY);
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
        super.render(delta);
        handleInput();

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
