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
 * Screen used to display mosnters info
 */

public class MonstersInfoScreen extends AbstractScreen {
    private Image backgroundImg;

    private Table table1, table2;

    private ImageButton btnEsc, btnRight, btnLeft;

    private Skin btnEscSkin, btnRightSkin, btnLeftSkin;

    private boolean escape;

    private int selectedImage;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public MonstersInfoScreen(Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {

        selectedImage = 1;
        backgroundImg = new Image(gam.manager.get("menus/monster1.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        Gdx.input.setInputProcessor(stage);

        escape = false;

        table1 = new Table();
        table1.setFillParent(true);
        table1.top();

        table2 = new Table();
        table2.setFillParent(true);
        table2.center();

        stage.addActor(backgroundImg);

        createBtnEsc();
        createBtnLeft();
        createBtnRight();


        stage.addActor(table1);
        stage.addActor(table2);


    }

    private void createBtnEsc() {
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
        table1.add(btnEsc).expandX().right().padRight(15).padTop(15);
    }

    private void createBtnRight() {
        btnRightSkin = new Skin();
        btnRightSkin.add("default", gam.manager.get("btnRight.png", Texture.class));
        btnRight = new ImageButton(btnRightSkin.getDrawable("default"));

        btnRight.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedImage++;
                if(selectedImage> 12)
                    selectedImage = 1;
                stage.clear();
                backgroundImg = new Image(gam.manager.get("menus/monster" + selectedImage + ".png", Texture.class));
                backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
                stage.addActor(backgroundImg);
                stage.addActor(table1);
                stage.addActor(table2);
                return true;
            }

        });
        table2.add(btnRight).expandX().right().padRight(15).padTop(15);
    }

    private void createBtnLeft() {
        btnLeftSkin = new Skin();
        btnLeftSkin.add("default", gam.manager.get("btnLeft.png", Texture.class));
        btnLeft = new ImageButton(btnLeftSkin.getDrawable("default"));

        btnLeft.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedImage--;
                if(selectedImage< 1)
                    selectedImage = 12;
                stage.clear();
                backgroundImg = new Image(gam.manager.get("menus/monster" + selectedImage + ".png", Texture.class));
                backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());
                stage.addActor(backgroundImg);
                stage.addActor(table1);
                stage.addActor(table2);
                return true;
            }

        });
        table2.add(btnLeft).expandX().left().padLeft(15).padTop(15);
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
