package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.isAndroid;
import static com.lpoo.bombic.Bombic.soundsOn;

/**
 * Screen that displays credits information
 */

public class CreditsScreen extends AbstractScreen {
    private Image backgroundImg;

    private Table table;

    private ImageButton btnEsc;

    private Skin btnEscSkin;

    private boolean escape;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public CreditsScreen(Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {

        backgroundImg = new Image(gam.manager.get("menus/creditsBack.png", Texture.class));

        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        Gdx.input.setInputProcessor(stage);

        escape = false;

        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(backgroundImg);

        createBtnEsc();

    }

    private void createBtnEsc() {
        Table table2 = new Table();
        table2.setFillParent(true);
        table2.top();
        btnEscSkin = new Skin();
        btnEscSkin.add("default", gam.manager.get("btnEscape.png", Texture.class));
        btnEsc = new ImageButton(btnEscSkin.getDrawable("default"));

        btnEsc.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                escape = true;
                return true;
            }

        });
        table2.add(btnEsc).expandX().right().padRight(15).padTop(15);
        stage.addActor(table2);
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        stage.act();


        if (escape || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (soundsOn)
                soundEscape.play();
            bombicGame.gsm.setScreen(GameScreenManager.STATE.MENU);
        }
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
}
