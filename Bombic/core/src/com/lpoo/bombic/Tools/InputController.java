package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Players.Bomber;

/**
 * Created by Rui Quaresma on 22/04/2017.
 */

public class InputController {

    private boolean keyUpPressed = false;
    private boolean keyDownPressed = false;
    private boolean keyLeftPressed = false;
    private boolean keyRightPressed = false;

    private boolean keyWPressed = false;
    private boolean keySPressed = false;
    private boolean keyAPressed = false;
    private boolean keyDPressed = false;

    private boolean keyIPressed = false;
    private boolean keyJPressed = false;
    private boolean keyKPressed = false;
    private boolean keyLPressed = false;

    private boolean key8Pressed = false;
    private boolean key5Pressed = false;
    private boolean key4Pressed = false;
    private boolean key6Pressed = false;

    private Game game;

    public InputController(Game game) {
        this.game = game;
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

                if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
                    bomber.placeBomb();
                }
                break;
            case 3:
                if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                    bomber.move(Input.Keys.I);
                    keyIPressed = true;
                } else if (keyIPressed) {
                    bomber.stop(Input.Keys.I);
                    keyIPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                    keyKPressed = true;
                    bomber.move(Input.Keys.K);
                } else if (keyKPressed) {
                    bomber.stop(Input.Keys.K);
                    keyKPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                    bomber.move(Input.Keys.J);
                    keyJPressed = true;
                } else if (keyJPressed) {
                    bomber.stop(Input.Keys.J);
                    keyJPressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                    bomber.move(Input.Keys.L);
                    keyLPressed = true;
                } else if (keyLPressed) {
                    bomber.stop(Input.Keys.L);
                    keyLPressed = false;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    bomber.placeBomb();
                }
                break;
            case 4:
                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
                    bomber.move(Input.Keys.NUMPAD_8);
                    key8Pressed = true;
                } else if (key8Pressed) {
                    bomber.stop(Input.Keys.NUMPAD_8);
                    key8Pressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
                    key5Pressed = true;
                    bomber.move(Input.Keys.NUMPAD_5);
                } else if (key5Pressed) {
                    bomber.stop(Input.Keys.NUMPAD_5);
                    key5Pressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
                    bomber.move(Input.Keys.NUMPAD_4);
                    key4Pressed = true;
                } else if (key4Pressed) {
                    bomber.stop(Input.Keys.NUMPAD_4);
                    key4Pressed = false;
                }

                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
                    bomber.move(Input.Keys.NUMPAD_6);
                    key6Pressed = true;
                } else if (key6Pressed) {
                    bomber.stop(Input.Keys.NUMPAD_6);
                    key6Pressed = false;
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
                    bomber.placeBomb();
                }
                break;
            default:
                break;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS) && Bombic.GAME_SPEED <= 4) {
            Bombic.GAME_SPEED += 0.1f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS) && Bombic.GAME_SPEED >= 0.8) {
            Bombic.GAME_SPEED -= 0.1f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setGameOver(true);
        }
    }
}
