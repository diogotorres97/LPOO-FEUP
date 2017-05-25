package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by up201506428 on 25/05/2017.
 */

public class MultiPlayerInputController {

    private boolean keyUpPressed;
    private boolean keyDownPressed;
    private boolean keyLeftPressed;
    private boolean keyRightPressed;
    private boolean keyCtrlRightPressed;

    private boolean keyWPressed;
    private boolean keySPressed;
    private boolean keyAPressed;
    private boolean keyDPressed;
    private boolean keyCtrlLeftPressed;

    private Game game;

    public MultiPlayerInputController(Game game) {
        this.game = game;
        initiatePlayer1Keys();
        initiatePlayer2Keys();

    }

    private void initiatePlayer1Keys() {
        keyUpPressed = false;
        keyDownPressed = false;
        keyLeftPressed = false;
        keyRightPressed = false;
        keyCtrlRightPressed = false;
    }

    private void initiatePlayer2Keys() {
        keyWPressed = false;
        keySPressed = false;
        keyAPressed = false;
        keyDPressed = false;
        keyCtrlLeftPressed = false;
    }

      public void handleInput(Player player, int input) {
        switch (player.getId()) {
            case 1:
                handlePlayer1Input(player, input);
                break;

            case 2:
                handlePlayer2Input(player, input);

                break;

            default:
                break;
        }
    }

    private void handlePlayer1Input(Player player, int input) {
        System.out.println("HEY");
        if (input == Input.Keys.UP) {
            player.move(Input.Keys.UP);
            keyUpPressed = true;
        } else if (keyUpPressed) {
            player.stop(Input.Keys.UP);
            keyUpPressed = false;
        }

        if (input == Input.Keys.DOWN) {
            keyDownPressed = true;
            player.move(Input.Keys.DOWN);
        } else if (keyDownPressed) {
            player.stop(Input.Keys.DOWN);
            keyDownPressed = false;
        }

        if (input == Input.Keys.LEFT) {
            player.move(Input.Keys.LEFT);
            keyLeftPressed = true;
        } else if (keyLeftPressed) {
            player.stop(Input.Keys.LEFT);
            keyLeftPressed = false;
        }

        if (input == Input.Keys.RIGHT) {
            player.move(Input.Keys.RIGHT);
            keyRightPressed = true;
        } else if (keyRightPressed) {
            player.stop(Input.Keys.RIGHT);
            keyRightPressed = false;
        }


        if (input == Input.Keys.CONTROL_RIGHT) {
            if (player.isDistantExplode()) {
                if (player.getPlacedBombs() == 0) {
                    player.placeBomb();
                    player.setExplodeBombs(false);
                    keyCtrlRightPressed = true;
                }
            } else
                player.placeBomb();
        } else if (keyCtrlRightPressed) {
            if (player.isDistantExplode()) {
                player.setExplodeBombs(true);
            }
            keyCtrlRightPressed = false;

        }


    }

    private void handlePlayer2Input(Player player, int input) {
        System.out.println("HEY2");
        if (input == Input.Keys.W) {
            player.move(Input.Keys.W);
            keyWPressed = true;
        } else if (keyWPressed) {
            player.stop(Input.Keys.W);
            keyWPressed = false;
        }

        if (input == Input.Keys.S) {
            keySPressed = true;
            player.move(Input.Keys.S);
        } else if (keySPressed) {
            player.stop(Input.Keys.S);
            keySPressed = false;
        }

        if (input == Input.Keys.A) {
            player.move(Input.Keys.A);
            keyAPressed = true;
        } else if (keyAPressed) {
            player.stop(Input.Keys.A);
            keyAPressed = false;
        }

        if (input == Input.Keys.D) {
            player.move(Input.Keys.D);
            keyDPressed = true;
        } else if (keyDPressed) {
            player.stop(Input.Keys.D);
            keyDPressed = false;
        }

        if (input == Input.Keys.CONTROL_LEFT) {
            if (player.isDistantExplode()) {
                if (player.getPlacedBombs() == 0) {
                    player.placeBomb();
                    player.setExplodeBombs(false);
                    keyCtrlLeftPressed = true;
                }
            } else
                player.placeBomb();
        } else if (keyCtrlLeftPressed) {
            if (player.isDistantExplode()) {
                player.setExplodeBombs(true);
            }
            keyCtrlLeftPressed = false;

        }
    }

    public void handleCommonInput(int input) {

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

    public int[] getKeyPressed() {

        int[] idKey = new int[2];

        idKey[0]=1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            idKey[1]=Input.Keys.UP;
            return idKey;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            idKey[1]=Input.Keys.DOWN;
            return idKey;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            idKey[1]=Input.Keys.LEFT;
            return idKey;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            idKey[1]=Input.Keys.RIGHT;
            return idKey;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)){
            idKey[1]=Input.Keys.CONTROL_RIGHT;
            return idKey;
        }


        idKey[0]=2;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            idKey[1]=Input.Keys.W;
            return idKey;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            idKey[1]=Input.Keys.S;
            return idKey;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            idKey[1]=Input.Keys.A;
            return idKey;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            idKey[1]=Input.Keys.D;
            return idKey;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
            idKey[1]=Input.Keys.CONTROL_LEFT;
            return idKey;
        }

         idKey[0]=-1;
        return idKey;
    }
}
