package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;

/**
 * Created by Rui Quaresma on 22/04/2017.
 */

public class InputController {
    private Viewport viewport;
    private Stage stage;

    private boolean keyUpPressed = false;
    private boolean keyDownPressed = false;
    private boolean keyLeftPressed = false;
    private boolean keyRightPressed = false;

    private boolean keyWPressed = false;
    private boolean keySPressed = false;
    private boolean keyAPressed = false;
    private boolean keyDPressed = false;

    private OrthographicCamera cam;
    private Bomber bomber;
    private PlayScreen screen;

    public InputController(PlayScreen screen) {
        this.screen = screen;
        this.cam = screen.getGamecam();
        this.viewport = screen.getGamePort();
        stage = new Stage(viewport, screen.getGame().batch);


    }

    public void handleInput(Bomber bomber) {
        switch (bomber.getId()) {
            case 1:
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    bomber.move(Input.Keys.UP);
                    keyUpPressed = true;
                } else if (keyUpPressed) {
                    bomber.stop(Input.Keys.UP);
                    keyUpPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    keyDownPressed = true;
                    bomber.move(Input.Keys.DOWN);
                } else if (keyDownPressed) {
                    bomber.stop(Input.Keys.DOWN);
                    keyDownPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    bomber.move(Input.Keys.LEFT);
                    keyLeftPressed = true;
                } else if (keyLeftPressed) {
                    bomber.stop(Input.Keys.LEFT);
                    keyLeftPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    bomber.move(Input.Keys.RIGHT);
                    keyRightPressed = true;
                } else if (keyRightPressed) {
                    bomber.stop(Input.Keys.RIGHT);
                    keyRightPressed = false;
                }

                //Place bombs
                if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)) {
                    bomber.placeBomb();
                }
                break;

            case 2:

                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    bomber.move(Input.Keys.W);
                    keyWPressed = true;
                } else if (keyWPressed) {
                    bomber.stop(Input.Keys.W);
                    keyWPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    keySPressed = true;
                    bomber.move(Input.Keys.S);
                } else if (keySPressed) {
                    bomber.stop(Input.Keys.S);
                    keySPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    bomber.move(Input.Keys.A);
                    keyAPressed = true;
                } else if (keyAPressed) {
                    bomber.stop(Input.Keys.A);
                    keyAPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    bomber.move(Input.Keys.D);
                    keyDPressed = true;
                } else if (keyDPressed) {
                    bomber.stop(Input.Keys.D);
                    keyDPressed = false;
                }

                //Place bombs
                if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
                    bomber.placeBomb();
                }
                break;
            default:
                break;
        }


        //Increase game speed
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS) && Bombic.GAME_SPEED <= 4) {
            Bombic.GAME_SPEED += 0.1f;
        }

        //Decrease game speed
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS) && Bombic.GAME_SPEED >= 0.8) {
            Bombic.GAME_SPEED -= 0.1f;
        }

    }
}
