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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;

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

    private Touchpad joystick;
    private Touchpad.TouchpadStyle joystickStyle;
    private Skin joystickSkin;
    private Drawable joystickBackImg;
    private Drawable joystickImg;

    private Skin btnEscSkin, btnPlusSkin, btnMinusSkin, btnPauseSkin, btnBombSkin;

    private Button btnEsc, btnPlus, btnMinus, btnPause, btnBomb;

    private Table table1, table2;

    private boolean reset;

    private boolean resetBomb;

    private boolean bomb;

    private boolean escape;
    private boolean plus;
    private boolean minus;
    private boolean pause;

    private int mode;

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


        //stage.addActor(joystick);
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
        Gdx.input.setInputProcessor(stage);

    }

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

    public int getDir() {
        int dir = -1;
        if (mode == 1) {
            if (hasAccelerometer)
                dir = getAccelerometerDir();
            if (hasJoystick && getJoystickDir() != -1)
                dir = getJoystickDir();
        }else
            dir = getJoystickDir();


        return dir;

    }

    private int getAccelerometerDir() {
        float xDir = Gdx.input.getAccelerometerX();
        float yDir = Gdx.input.getAccelerometerY();
        if ((Math.abs(xDir) > 1 || Math.abs(yDir) > 1) && (xDir != yDir)) {
            switch (getAccelerometerSquadrant()) {
                case 1:
                    if (Math.abs(yDir) > Math.abs(xDir))
                        return Input.Keys.RIGHT;
                    else
                        return Input.Keys.UP;
                case 2:
                    if (Math.abs(yDir) > Math.abs(xDir))
                        return Input.Keys.LEFT;
                    else
                        return Input.Keys.UP;
                case 3:
                    if (Math.abs(yDir) > Math.abs(xDir))
                        return Input.Keys.LEFT;
                    else
                        return Input.Keys.DOWN;
                case 4:
                    if (Math.abs(yDir) > Math.abs(xDir))
                        return Input.Keys.RIGHT;
                    else
                        return Input.Keys.DOWN;
                default:
                    break;
            }
        }

        return -1;
    }

    private int getJoystickDir() {
        float xKnob = joystick.getKnobPercentX();
        float yKnob = joystick.getKnobPercentY();

        if ((xKnob != 0 || yKnob != 0) && (xKnob != yKnob)) {
            switch (getSquadrant(xKnob, yKnob)) {
                case 1:
                    if (Math.abs(xKnob) > Math.abs(yKnob))
                        return Input.Keys.RIGHT;
                    else
                        return Input.Keys.UP;
                case 2:
                    if (Math.abs(xKnob) > Math.abs(yKnob))
                        return Input.Keys.LEFT;
                    else
                        return Input.Keys.UP;
                case 3:
                    if (Math.abs(xKnob) > Math.abs(yKnob))
                        return Input.Keys.LEFT;
                    else
                        return Input.Keys.DOWN;
                case 4:
                    if (Math.abs(xKnob) > Math.abs(yKnob))
                        return Input.Keys.RIGHT;
                    else
                        return Input.Keys.DOWN;
                default:
                    break;
            }
        }
        if (xKnob == 0 && yKnob == 0)
            reset = true;

        return -1;
    }

    public boolean isReset() {
        return reset;
    }

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

    private int getAccelerometerSquadrant() {
        float xDir = Gdx.input.getAccelerometerX();
        float yDir = Gdx.input.getAccelerometerY();
        if (yDir > 0 && xDir < 0)
            return 1;
        else if (yDir < 0 && xDir < 0)
            return 2;
        else if (yDir < 0 && xDir > 0)
            return 3;
        else if (yDir > 0 && xDir > 0)
            return 4;
        return 0;
    }

}
