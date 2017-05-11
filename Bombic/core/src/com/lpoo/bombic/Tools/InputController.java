package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 22/04/2017.
 */

public class InputController {
    private boolean keyUpPressed;
    private boolean keyDownPressed;
    private boolean keyLeftPressed;
    private boolean keyRightPressed;

    private boolean keyWPressed;
    private boolean keySPressed;
    private boolean keyAPressed;
    private boolean keyDPressed;

    private boolean keyIPressed;
    private boolean keyJPressed;
    private boolean keyKPressed;
    private boolean keyLPressed;

    private boolean key8Pressed;
    private boolean key5Pressed;
    private boolean key4Pressed;
    private boolean key6Pressed;


    private Game game;

    public InputController(Game game) {
        this.game = game;
        initiatePlayer1Keys();
        initiatePlayer2Keys();
        initiatePlayer3Keys();
        initiatePlayer4Keys();


    }

    private void initiatePlayer1Keys() {
        keyUpPressed = false;
        keyDownPressed = false;
        keyLeftPressed = false;
        keyRightPressed = false;
    }

    private void initiatePlayer2Keys() {
        keyWPressed = false;
        keySPressed = false;
        keyAPressed = false;
        keyDPressed = false;
    }

    private void initiatePlayer3Keys() {
        keyIPressed = false;
        keyJPressed = false;
        keyKPressed = false;
        keyLPressed = false;
    }

    private void initiatePlayer4Keys() {
        key8Pressed = false;
        key5Pressed = false;
        key4Pressed = false;
        key6Pressed = false;
    }

    public void handleInput(Player player) {
        switch (player.getId()) {
            case 1:
                handlePlayer1Input(player);
                break;

            case 2:
                handlePlayer2Input(player);

                break;
            case 3:
                handlePlayer3Input(player);
                break;
            case 4:
                handlePlayer4Input(player);
                break;
            default:
                break;
        }
    }

    private void handlePlayer1Input(Player player){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.move(Input.Keys.UP);
            keyUpPressed = true;
        } else if (keyUpPressed) {
            player.stop(Input.Keys.UP);
            keyUpPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            keyDownPressed = true;
            player.move(Input.Keys.DOWN);
        } else if (keyDownPressed) {
            player.stop(Input.Keys.DOWN);
            keyDownPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(Input.Keys.LEFT);
            keyLeftPressed = true;
        } else if (keyLeftPressed) {
            player.stop(Input.Keys.LEFT);
            keyLeftPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(Input.Keys.RIGHT);
            keyRightPressed = true;
        } else if (keyRightPressed) {
            player.stop(Input.Keys.RIGHT);
            keyRightPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
            player.placeBomb();
        }
    }
    private void handlePlayer2Input(Player player){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.move(Input.Keys.W);
            keyWPressed = true;
        } else if (keyWPressed) {
            player.stop(Input.Keys.W);
            keyWPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            keySPressed = true;
            player.move(Input.Keys.S);
        } else if (keySPressed) {
            player.stop(Input.Keys.S);
            keySPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.move(Input.Keys.A);
            keyAPressed = true;
        } else if (keyAPressed) {
            player.stop(Input.Keys.A);
            keyAPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.move(Input.Keys.D);
            keyDPressed = true;
        } else if (keyDPressed) {
            player.stop(Input.Keys.D);
            keyDPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            player.placeBomb();
        }
    }
    private void handlePlayer3Input(Player player){
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            player.move(Input.Keys.I);
            keyIPressed = true;
        } else if (keyIPressed) {
            player.stop(Input.Keys.I);
            keyIPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            keyKPressed = true;
            player.move(Input.Keys.K);
        } else if (keyKPressed) {
            player.stop(Input.Keys.K);
            keyKPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            player.move(Input.Keys.J);
            keyJPressed = true;
        } else if (keyJPressed) {
            player.stop(Input.Keys.J);
            keyJPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            player.move(Input.Keys.L);
            keyLPressed = true;
        } else if (keyLPressed) {
            player.stop(Input.Keys.L);
            keyLPressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.placeBomb();
        }
    }
    private void handlePlayer4Input(Player player){
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
            player.move(Input.Keys.NUMPAD_8);
            key8Pressed = true;
        } else if (key8Pressed) {
            player.stop(Input.Keys.NUMPAD_8);
            key8Pressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
            key5Pressed = true;
            player.move(Input.Keys.NUMPAD_5);
        } else if (key5Pressed) {
            player.stop(Input.Keys.NUMPAD_5);
            key5Pressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            player.move(Input.Keys.NUMPAD_4);
            key4Pressed = true;
        } else if (key4Pressed) {
            player.stop(Input.Keys.NUMPAD_4);
            key4Pressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
            player.move(Input.Keys.NUMPAD_6);
            key6Pressed = true;
        } else if (key6Pressed) {
            player.stop(Input.Keys.NUMPAD_6);
            key6Pressed = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_0)) {
            player.placeBomb();
        }
    }

    public void handleCommonInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS) && game.getGameSpeed() < 3.9) {
            game.setGameSpeed(game.getGameSpeed() + 0.1f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS) && game.getGameSpeed() >= 0.8) {
            game.setGameSpeed(game.getGameSpeed() - 0.1f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setGameOver(true);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {

            if (game.getGamePaused()) {
                game.setGamePaused(false);
            } else {
                game.setGamePaused(true);
            }

        }

    }


}
