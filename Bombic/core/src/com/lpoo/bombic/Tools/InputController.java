package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Players.Player;

import java.util.HashMap;

/**
 * Created by Rui Quaresma on 22/04/2017.
 */

public class InputController {

    private AndroidController androidController;

    private HashMap<Integer, Boolean> player1KeysPressed;
    private int[] player1Keys;

    private HashMap<Integer, Boolean> player2KeysPressed;
    private int[] player2Keys;

    private HashMap<Integer, Boolean> player3KeysPressed;
    private int[] player3Keys;

    private HashMap<Integer, Boolean> player4KeysPressed;
    private int[] player4Keys;

    private int[] playersBombsKeys;
    private HashMap<Integer, Boolean> playersBombsKeysPressed;

    private Game game;

    boolean pressedEscape, pressedPlus, pressedMinus, pressedPause;

    private boolean isAndroid;

    public InputController(Game game, AndroidController androidController) {
        this.game = game;
        this.androidController = androidController;
        initiatePlayer1Keys();
        initiatePlayer2Keys();
        initiatePlayer3Keys();
        initiatePlayer4Keys();

        initiateBombKeys();

        if (Gdx.app.getType() != Application.ApplicationType.Android)
            isAndroid = false;
        else
            isAndroid = true;


        pressedEscape = pressedPlus = pressedMinus = pressedPause = false;

    }

    private void initiateBombKeys() {
        playersBombsKeys = new int[4];
        playersBombsKeysPressed = new HashMap<Integer, Boolean>();

        playersBombsKeys[0] = Input.Keys.CONTROL_RIGHT;
        playersBombsKeys[1] = Input.Keys.CONTROL_LEFT;
        playersBombsKeys[2] = Input.Keys.SPACE;
        playersBombsKeys[3] = Input.Keys.NUMPAD_0;

        playersBombsKeysPressed.put(Input.Keys.CONTROL_RIGHT, false);
        playersBombsKeysPressed.put(Input.Keys.CONTROL_LEFT, false);
        playersBombsKeysPressed.put(Input.Keys.SPACE, false);
        playersBombsKeysPressed.put(Input.Keys.NUMPAD_0, false);
    }

    private void initiatePlayer1Keys() {
        player1Keys = new int[4];
        player1KeysPressed = new HashMap<Integer, Boolean>();

        player1Keys[0] = Input.Keys.UP;
        player1Keys[1] = Input.Keys.DOWN;
        player1Keys[2] = Input.Keys.RIGHT;
        player1Keys[3] = Input.Keys.LEFT;

        player1KeysPressed.put(Input.Keys.UP, false);
        player1KeysPressed.put(Input.Keys.DOWN, false);
        player1KeysPressed.put(Input.Keys.RIGHT, false);
        player1KeysPressed.put(Input.Keys.LEFT, false);
    }

    private void initiatePlayer2Keys() {
        player2Keys = new int[4];
        player2KeysPressed = new HashMap<Integer, Boolean>();

        player2Keys[0] = Input.Keys.W;
        player2Keys[1] = Input.Keys.S;
        player2Keys[2] = Input.Keys.D;
        player2Keys[3] = Input.Keys.A;

        player2KeysPressed.put(Input.Keys.W, false);
        player2KeysPressed.put(Input.Keys.S, false);
        player2KeysPressed.put(Input.Keys.D, false);
        player2KeysPressed.put(Input.Keys.A, false);
    }

    private void initiatePlayer3Keys() {
        player3Keys = new int[4];
        player3KeysPressed = new HashMap<Integer, Boolean>();

        player3Keys[0] = Input.Keys.I;
        player3Keys[1] = Input.Keys.K;
        player3Keys[2] = Input.Keys.L;
        player3Keys[3] = Input.Keys.J;

        player3KeysPressed.put(Input.Keys.I, false);
        player3KeysPressed.put(Input.Keys.K, false);
        player3KeysPressed.put(Input.Keys.L, false);
        player3KeysPressed.put(Input.Keys.J, false);
    }

    private void initiatePlayer4Keys() {
        player4Keys = new int[4];
        player4KeysPressed = new HashMap<Integer, Boolean>();

        player4Keys[0] = Input.Keys.NUMPAD_8;
        player4Keys[1] = Input.Keys.NUMPAD_5;
        player4Keys[2] = Input.Keys.NUMPAD_6;
        player4Keys[3] = Input.Keys.NUMPAD_4;

        player4KeysPressed.put(Input.Keys.NUMPAD_8, false);
        player4KeysPressed.put(Input.Keys.NUMPAD_5, false);
        player4KeysPressed.put(Input.Keys.NUMPAD_6, false);
        player4KeysPressed.put(Input.Keys.NUMPAD_4, false);
    }

    public void handleInput(Player player) {
        if (isAndroid)
            handleAndroidInput(player);
        else {
            pressedPlaceBomb(player, false);
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
    }

    private void pressedPlaceBomb(Player player, boolean bomb) {
        int keyPressed = playersBombsKeys[player.getId() - 1];
        boolean pressedBomb = false;
        if (isAndroid)
            pressedBomb = bomb;
        else if (Gdx.input.isKeyPressed(keyPressed))
            pressedBomb = true;

        if (pressedBomb) {
            if (player.isDistantExplode()) {
                if (player.getPlacedBombs() == 0) {
                    player.placeBomb();
                    player.setExplodeBombs(false);
                }
            } else if (player.isSendingBombs() && !player.isMoving()) {
                if (!playersBombsKeysPressed.get(keyPressed))
                    player.placeBomb();
            } else if (!player.isSendingBombs() || (player.isSendingBombs() && player.isMoving()))
                player.placeBomb();

            playersBombsKeysPressed.put(keyPressed, true);
            player.setPressedBombButton(true);
        } else if (playersBombsKeysPressed.get(keyPressed)) {
            if (player.isDistantExplode()) {
                player.setExplodeBombs(true);
            }
            playersBombsKeysPressed.put(keyPressed, false);
            player.setPressedBombButton(false);
        }
    }

    private void handleAndroidInput(Player player) {
        androidController.handle();
        handleAndroidMove(player, androidController.getDir());
        pressedPlaceBomb(player, androidController.getBomb());

    }

    private void handleAndroidMove(Player player, int dir) {
        for (int i = 0; i < 4; i++) {
            if (dir == player1Keys[i]) {
                player.move(player1Keys[i]);
                player1KeysPressed.put(player1Keys[i], true);
            } else if (player1KeysPressed.get(player1Keys[i])) {
                player.stop(player1Keys[i]);
                player1KeysPressed.put(player1Keys[i], false);
            }
        }
    }

    private void handlePlayer1Input(Player player) {
        for (int i = 0; i < 4; i++) {
            if ((Gdx.input.isKeyPressed(player1Keys[i]))) {
                player.move(player1Keys[i]);
                player1KeysPressed.put(player1Keys[i], true);
            } else if (player1KeysPressed.get(player1Keys[i])) {
                player.stop(player1Keys[i]);
                player1KeysPressed.put(player1Keys[i], false);
            }
        }
    }

    private void handlePlayer2Input(Player player) {
        for (int i = 0; i < 4; i++) {
            if ((Gdx.input.isKeyPressed(player2Keys[i]))) {
                player.move(player2Keys[i]);
                player2KeysPressed.put(player2Keys[i], true);
            } else if (player2KeysPressed.get(player2Keys[i])) {
                player.stop(player2Keys[i]);
                player2KeysPressed.put(player2Keys[i], false);
            }
        }
    }

    private void handlePlayer3Input(Player player) {
        for (int i = 0; i < 4; i++) {
            if ((Gdx.input.isKeyPressed(player3Keys[i]))) {
                player.move(player3Keys[i]);
                player3KeysPressed.put(player3Keys[i], true);
            } else if (player3KeysPressed.get(player3Keys[i])) {
                player.stop(player3Keys[i]);
                player3KeysPressed.put(player3Keys[i], false);
            }
        }
    }

    private void handlePlayer4Input(Player player) {
        for (int i = 0; i < 4; i++) {
            if ((Gdx.input.isKeyPressed(player4Keys[i]))) {
                player.move(player4Keys[i]);
                player4KeysPressed.put(player4Keys[i], true);
            } else if (player4KeysPressed.get(player4Keys[i])) {
                player.stop(player4Keys[i]);
                player4KeysPressed.put(player4Keys[i], false);
            }
        }
    }

    private void commonInputPressedKeys(){
        androidController.handle();
        pressedEscape = false;
        if(isAndroid) {
            pressedEscape = androidController.getEscape();
            androidController.setEscape(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            pressedEscape = true;

        pressedPause = false;
        if(isAndroid) {
            pressedPause = androidController.getPause();
            androidController.setPause(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.P))
            pressedPause = true;

        pressedPlus = false;
        if(isAndroid) {
            pressedPlus = androidController.getPlus();
            androidController.setPlus(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS))
            pressedPlus = true;

        pressedMinus = false;
        if(isAndroid) {
            pressedMinus = androidController.getMinus();
            androidController.setMinus(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
            pressedMinus = true;
    }

    public void handleCommonInput() {

        commonInputPressedKeys();
        if (pressedPlus && game.getGameSpeed() < 3.9) {
            game.setGameSpeed(game.getGameSpeed() + 0.1f);
        }

        if (pressedMinus && game.getGameSpeed() >= 0.8) {
            game.setGameSpeed(game.getGameSpeed() - 0.1f);
        }

        if (pressedEscape) {
            game.setGameOver(true);
        }

        if (pressedPause) {
            if (game.getGamePaused()) {
                game.setGamePaused(false);
            } else {
                game.setGamePaused(true);
            }
        }
    }

}
