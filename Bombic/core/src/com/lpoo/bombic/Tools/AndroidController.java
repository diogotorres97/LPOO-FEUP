package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Bombic.hasAccelerometer;
import static com.lpoo.bombic.Bombic.hasJoystick;

/**
 * Handles android input
 */
public class AndroidController {
    /**
     * Stage for placing the buttons and labels
     */
    public Stage stage;
    /**
     * Viewport used by the stage
     */
    private Viewport gamePort;
    /**
     * Touchpad joystick
     */
    private Touchpad joystick;
    /**
     * joystick style
     */
    private Touchpad.TouchpadStyle joystickStyle;
    /**
     * Joystick skin
     */
    private Skin joystickSkin;
    /**
     * Joystick back img
     */
    private Drawable joystickBackImg;
    /**
     * Joystick img
     */
    private Drawable joystickImg;
    /**
     * Buttons skins
     */
    private Skin btnEscSkin, btnPlusSkin, btnMinusSkin, btnPauseSkin, btnBombSkin;
    /**
     * Buttons
     */
    private Button btnEsc, btnPlus, btnMinus, btnPause, btnBomb;
    /**
     * Tables to place the objects
     */
    private Table table1, table2;
    /**
     * Whether the knob has returned to the original position
     */
    private boolean reset;
    /**
     * If bomb button has ben released
     */
    private boolean resetBomb;

    /**
     * Buttons pressed
     */
    private boolean bomb;
    private boolean escape;
    private boolean plus;
    private boolean minus;
    private boolean pause;
    /**
     * 1-> PlayMode, 2-> MenuMode
     */
    private int mode;

    /**
     * Constructor
     *
     * @param sb
     * @param mode
     */
    public AndroidController(SpriteBatch sb, int mode) {
        gamePort = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        stage = new Stage(gamePort, sb);

        bomb = escape = pause = plus = minus = false;
        this.mode = mode;
        reset = false;
        resetBomb = true;

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        table1 = new Table();
        table1.setFillParent(true);
        table1.bottom();

        table2 = new Table();
        table2.setFillParent(true);
        table2.top();

        buttonsCreation();
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Creates the buttons
     */
    private void buttonsCreation() {
        createBtnEsc();
        if (mode == 1) {
            table2.row();
            createBtnPause();
            table2.row();
            createBtnPlus();
            table2.row();
            createBtnMinus();

            if (hasJoystick) {
                createJoystick();
                table1.add(joystick).size(128, 128).expandX().left().padBottom(25);
            }

        } else {
            createJoystick();
            table1.add(joystick).size(128, 128).expandX().left().padBottom(25);
        }

        createBtnBomb();
        stage.addActor(table1);
        stage.addActor(table2);
    }

    /**
     * Creates the joystick
     */
    private void createJoystick() {
        joystickSkin = new Skin();

        joystickSkin.add("joystickBack", gam.manager.get("joystickBack.png", Texture.class));
        joystickSkin.add("joystick", gam.manager.get("joystickKnob.png", Texture.class));

        joystickStyle = new Touchpad.TouchpadStyle();
        joystickImg = joystickSkin.getDrawable("joystick");
        joystickBackImg = joystickSkin.getDrawable("joystickBack");

        joystickStyle.background = joystickBackImg;
        joystickStyle.knob = joystickImg;
        joystick = new Touchpad(25, joystickStyle);
        joystick.setBounds(0, 0, 128, 128);
    }

    /**
     * Creates bomb button
     */
    private void createBtnBomb() {
        btnBombSkin = new Skin();
        if (mode == 1)
            btnBombSkin.add("default", gam.manager.get("bombButton.png", Texture.class));
        else
            btnBombSkin.add("default", gam.manager.get("enterButton.png", Texture.class));
        btnBomb = new Button(btnBombSkin.getDrawable("default"));

        btnBomb.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bomb = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bomb = false;
                resetBomb = true;
            }


        });

        table1.add(btnBomb).expandX().right().padRight(15).padBottom(25);

    }

    /**
     * Creates escape button
     */
    private void createBtnEsc() {
        btnEscSkin = new Skin();
        btnEscSkin.add("default", gam.manager.get("btnEscape.png", Texture.class));
        btnEsc = new Button(btnEscSkin.getDrawable("default"));

        btnEsc.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                escape = true;
                return true;
            }

        });

        table2.add(btnEsc).expandX().right().padRight(15);
    }

    /**
     * Creates pause button
     */
    private void createBtnPause() {
        btnPauseSkin = new Skin();
        btnPauseSkin.add("default", gam.manager.get("btnPause.png", Texture.class));
        btnPause = new Button(btnPauseSkin.getDrawable("default"));

        btnPause.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pause)
                    pause = false;
                else
                    pause = true;
                return true;
            }

        });

        table2.add(btnPause).expandX().right().padRight(15).padTop(10);
    }

    /**
     * Creates plus button
     */
    private void createBtnPlus() {
        btnPlusSkin = new Skin();
        btnPlusSkin.add("default", gam.manager.get("btnPlus.png", Texture.class));
        btnPlus = new Button(btnPlusSkin.getDrawable("default"));

        btnPlus.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                plus = true;
                return true;
            }

        });

        table2.add(btnPlus).expandX().right().padRight(15).padTop(10);
    }

    /**
     * Creates minus button
     */
    private void createBtnMinus() {
        btnMinusSkin = new Skin();
        btnMinusSkin.add("default", gam.manager.get("btnMinus.png", Texture.class));
        btnMinus = new Button(btnMinusSkin.getDrawable("default"));

        btnMinus.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                minus = true;
                return true;
            }
        });

        table2.add(btnMinus).expandX().right().padRight(15).padTop(10);
    }

    /**
     * Sets stage to act
     */
    public void handle() {

        stage.act();
    }

    public boolean getBomb() {
        return bomb;
    }

    public boolean getEscape() {
        return escape;
    }

    public boolean getPlus() {
        return plus;
    }

    public boolean getMinus() {
        return minus;
    }

    public boolean getPause() {
        return pause;
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    public void setMinus(boolean minus) {
        this.minus = minus;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isResetBomb() {
        return resetBomb;
    }

    public void setResetBomb(boolean resetBomb) {
        this.resetBomb = resetBomb;
    }

    /**
     * Returns the selected direction
     *
     * @return
     */
    public int getDir() {
        int dir = -1;
        if (mode == 1) {
            if (hasAccelerometer)
                dir = getAccelerometerDir();
            if (hasJoystick && getJoystickDir() != -1)
                dir = getJoystickDir();
        } else
            dir = getJoystickDir();


        return dir;

    }

    /**
     * Gets accelerometer direction
     *
     * @return
     */
     private int getAccelerometerDir() {
        float xDir = Gdx.input.getAccelerometerX();
        float yDir = Gdx.input.getAccelerometerY();
        if ((Math.abs(xDir) > 1 || Math.abs(yDir) > 1) && (xDir != yDir)) {
            switch (getSquadrant(yDir, xDir)) {
                case 1:
                    return getDirSquad4(yDir, xDir);
                case 2:
                    return getDirSquad3(yDir, xDir);
                case 3:
                    return getDirSquad2(yDir, xDir);
                case 4:
                    return getDirSquad1(yDir, xDir);
                default:
                    break;
            }
        }

        return -1;
    }

    /**
     * Gets joystick direction
     *
     * @return
     */
    private int getJoystickDir() {
        float xKnob = joystick.getKnobPercentX();
        float yKnob = joystick.getKnobPercentY();

        if ((xKnob != 0 || yKnob != 0) && (xKnob != yKnob)) {
            switch (getSquadrant(xKnob, yKnob)) {
                case 1:
                    return getDirSquad1(xKnob, yKnob);
                case 2:
                    return getDirSquad2(xKnob, yKnob);
                case 3:
                    return getDirSquad3(xKnob, yKnob);
                case 4:
                    return getDirSquad4(xKnob, yKnob);
                default:
                    break;
            }
        }
        if (xKnob == 0 && yKnob == 0)
            reset = true;

        return -1;
    }

    private int getDirSquad1(float xDir, float yDir){
        if (Math.abs(xDir) > Math.abs(yDir))
            return Input.Keys.RIGHT;
        else
            return Input.Keys.UP;
    }

    private int getDirSquad2(float xDir, float yDir){
        if (Math.abs(xDir) > Math.abs(yDir))
            return Input.Keys.LEFT;
        else
            return Input.Keys.UP;
    }

    private int getDirSquad3(float xDir, float yDir){
        if (Math.abs(xDir) > Math.abs(yDir))
            return Input.Keys.LEFT;
        else
            return Input.Keys.DOWN;
    }

    private int getDirSquad4(float xDir, float yDir){
        if (Math.abs(xDir) > Math.abs(yDir))
            return Input.Keys.RIGHT;
        else
            return Input.Keys.DOWN;
    }

    public boolean isReset() {
        return reset;
    }

    /**
     * Gets the squadrant correspondent to the x and y of the joystick/accelerometer
     *
     * @return
     */
    private int getSquadrant(float xKnob, float yKnob) {
        if (xKnob > 0 && yKnob > 0)
            return 1;
        else if (xKnob < 0 && yKnob > 0)
            return 2;
        else if (xKnob < 0 && yKnob < 0)
            return 3;
        else if (xKnob > 0 && yKnob < 0)
            return 4;
        return 0;
    }

}
