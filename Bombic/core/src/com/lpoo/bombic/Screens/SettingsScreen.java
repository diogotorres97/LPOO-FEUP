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
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.hasAccelerometer;
import static com.lpoo.bombic.Bombic.hasJoystick;
import static com.lpoo.bombic.Bombic.isAndroid;
import static com.lpoo.bombic.Bombic.soundsOn;

/**
 * Screen that displays the settings options
 */

public class SettingsScreen extends AbstractScreen {

    private Image backgroundImg;

    private Table table;

    private ImageButton btnEsc, btnSound, btnJoystick, btnAccelerometer;

    private Skin btnEscSkin, btnSoundSkin, btnJoystickSkin, btnAccelerometerSkin;

    private boolean escape;

    /**
     * Constructor
     *
     * @param bombicGame
     */
    public SettingsScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        backgroundImg = new Image(gam.manager.get("background.png", Texture.class));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        Gdx.input.setInputProcessor(stage);

        escape = false;

        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(backgroundImg);

        createBtnEsc();

        createBtnSound();
        if (isAndroid) {
            table.row();
            createBtnJoystick();
            table.row();
            createBtnAccelerometer();

        }
        stage.addActor(table);

    }

    private void createBtnSound() {
        btnSoundSkin = new Skin();
        btnSoundSkin.add("up", gam.manager.get("menus/buttons/btnSoundUnChecked.png"));
        btnSoundSkin.add("down", gam.manager.get("menus/buttons/btnSoundChecked.png"));
        btnSoundSkin.add("checked", gam.manager.get("menus/buttons/btnSoundChecked.png"));

        btnSound = new ImageButton(btnSoundSkin.getDrawable("up"), btnSoundSkin.getDrawable("down"), btnSoundSkin.getDrawable("checked"));

        if (soundsOn)
            btnSound.setChecked(true);
        else
            btnSound.setChecked(false);
        btnSound.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (soundsOn) {
                    soundsOn = false;
                    btnSound.setChecked(false);
                } else {
                    soundsOn = true;
                    btnSound.setChecked(true);
                }


                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (soundsOn)
                    btnSound.setChecked(true);
                else
                    btnSound.setChecked(false);
            }

        });

        table.add(btnSound).expandX().size(Constants.V_WIDTH / 4, Constants.V_WIDTH / 10);
    }

    private void createBtnJoystick() {
        btnJoystickSkin = new Skin();
        btnJoystickSkin.add("up", gam.manager.get("menus/buttons/btnJoystickUnChecked.png"));
        btnJoystickSkin.add("down", gam.manager.get("menus/buttons/btnJoystickChecked.png"));
        btnJoystickSkin.add("checked", gam.manager.get("menus/buttons/btnJoystickChecked.png"));

        btnJoystick = new ImageButton(btnJoystickSkin.getDrawable("up"), btnJoystickSkin.getDrawable("down"), btnJoystickSkin.getDrawable("checked"));

        if (hasJoystick)
            btnJoystick.setChecked(true);
        else
            btnJoystick.setChecked(false);
        btnJoystick.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (hasJoystick) {
                    if (hasAccelerometer) {
                        hasJoystick = false;
                        btnJoystick.setChecked(false);
                    }
                } else {
                    hasJoystick = true;
                    btnJoystick.setChecked(true);
                }


                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (hasJoystick)
                    btnJoystick.setChecked(true);
                else
                    btnJoystick.setChecked(false);
            }

        });

        table.add(btnJoystick).expandX().size(Constants.V_WIDTH / 3, Constants.V_WIDTH / 10);
    }

    private void createBtnAccelerometer() {
        btnAccelerometerSkin = new Skin();
        btnAccelerometerSkin.add("up", gam.manager.get("menus/buttons/btnAccelerometerUnChecked.png"));
        btnAccelerometerSkin.add("down", gam.manager.get("menus/buttons/btnAccelerometerChecked.png"));
        btnAccelerometerSkin.add("checked", gam.manager.get("menus/buttons/btnAccelerometerChecked.png"));

        btnAccelerometer = new ImageButton(btnAccelerometerSkin.getDrawable("up"), btnAccelerometerSkin.getDrawable("down"), btnAccelerometerSkin.getDrawable("checked"));

        if (hasAccelerometer)
            btnAccelerometer.setChecked(true);
        else
            btnAccelerometer.setChecked(false);
        btnAccelerometer.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (hasAccelerometer) {
                    if (hasJoystick) {
                        hasAccelerometer = false;
                        btnAccelerometer.setChecked(false);
                    }

                } else {
                    hasAccelerometer = true;
                    btnAccelerometer.setChecked(true);
                }


                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (hasAccelerometer)
                    btnAccelerometer.setChecked(true);
                else
                    btnAccelerometer.setChecked(false);
            }

        });

        table.add(btnAccelerometer).expandX().size(Constants.V_WIDTH / 1.8f, Constants.V_WIDTH / 10);
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
