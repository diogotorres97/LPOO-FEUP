package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Players.Player;

import java.util.HashMap;

import static com.lpoo.bombic.Bombic.isAndroid;
import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Handles players input
 */
public class InputController {
    /**
     * The singleton instance of this controller
     */
    private static InputController instance;
    /**
     * If android is active the the input is passed via androidController
     */
    private AndroidController androidController;
    /**
     * HashMap that contains players keys pressed
     */
    private HashMap<Integer, Boolean> playersKeysPressed;
    /**
     * Array that contains each player keys
     */
    private int[][] playersKeys;
    /**
     * Array that contains each player bomb k eys
     */
    private int[] playersBombsKeys;
    /**
     * HashMap that contains players bomb keys pressed
     */
    private HashMap<Integer, Boolean> playersBombsKeysPressed;
    /**
     * Whether game is over or not (due to escape pressing)
     */
    private boolean gameOver;
    /**
     * Whether game is paused or not
     */
    private boolean paused;
    /**
     * Common input variables for keys pressed
     */
    boolean pressedEscape, pressedPlus, pressedMinus, pressedPause;

    /**
     * Constructor of the input controller
     */
    private InputController() {
        playersKeysPressed = new HashMap<Integer, Boolean>();
        playersKeys = new int[4][4];

        initiatePlayer1Keys();
        initiatePlayer2Keys();
        initiatePlayer3Keys();
        initiatePlayer4Keys();

        gameOver = false;
        paused = false;

        initiateBombKeys();

        pressedEscape = pressedPlus = pressedMinus = pressedPause = false;

    }

    /**
     * Returns a singleton instance of an input controller
     *
     * @return the singleton instance
     */
    public static InputController getInstance() {
        if (instance == null)
            instance = new InputController();
        return instance;
    }

    /**
     * Resets gameOver and paused
     */
    public void resetVariables() {
        gameOver = false;
        paused = false;
    }

    /**
     * Sets androidController
     *
     * @param androidController
     */
    public void setAndroidController(AndroidController androidController) {
        this.androidController = androidController;
    }

    /**
     * Initiates the bombKeys array
     */
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

    /**
     * Initiates the player1Keys array
     */
    private void initiatePlayer1Keys() {
        playersKeys[0][0] = Input.Keys.UP;
        playersKeys[0][1] = Input.Keys.DOWN;
        playersKeys[0][2] = Input.Keys.RIGHT;
        playersKeys[0][3] = Input.Keys.LEFT;

        playersKeysPressed.put(Input.Keys.UP, false);
        playersKeysPressed.put(Input.Keys.DOWN, false);
        playersKeysPressed.put(Input.Keys.RIGHT, false);
        playersKeysPressed.put(Input.Keys.LEFT, false);
    }

    /**
     * Initiates the player2Keys array
     */
    private void initiatePlayer2Keys() {
        playersKeys[1][0] = Input.Keys.W;
        playersKeys[1][1] = Input.Keys.S;
        playersKeys[1][2] = Input.Keys.D;
        playersKeys[1][3] = Input.Keys.A;

        playersKeysPressed.put(Input.Keys.W, false);
        playersKeysPressed.put(Input.Keys.S, false);
        playersKeysPressed.put(Input.Keys.D, false);
        playersKeysPressed.put(Input.Keys.A, false);
    }

    /**
     * Initiates the player3Keys array
     */
    private void initiatePlayer3Keys() {
        playersKeys[2][0] = Input.Keys.I;
        playersKeys[2][1] = Input.Keys.K;
        playersKeys[2][2] = Input.Keys.L;
        playersKeys[2][3] = Input.Keys.J;

        playersKeysPressed.put(Input.Keys.I, false);
        playersKeysPressed.put(Input.Keys.K, false);
        playersKeysPressed.put(Input.Keys.L, false);
        playersKeysPressed.put(Input.Keys.J, false);
    }

    /**
     * Initiates the player4Keys array
     */
    private void initiatePlayer4Keys() {
        playersKeys[3][0] = Input.Keys.NUMPAD_8;
        playersKeys[3][1] = Input.Keys.NUMPAD_5;
        playersKeys[3][2] = Input.Keys.NUMPAD_6;
        playersKeys[3][3] = Input.Keys.NUMPAD_4;

        playersKeysPressed.put(Input.Keys.NUMPAD_8, false);
        playersKeysPressed.put(Input.Keys.NUMPAD_5, false);
        playersKeysPressed.put(Input.Keys.NUMPAD_6, false);
        playersKeysPressed.put(Input.Keys.NUMPAD_4, false);
    }

    /**
     * Calls the handle Input methods
     */
    public void handleInput(Player player) {
        if (isAndroid)
            handleAndroidInput(player);
        else {
            pressedPlaceBomb(player, false);
            handlePlayersInput(player);
        }

    }

    /**
     * Handles bomb keys pressed
     *
     * @param player - that placed the bomb
     * @param bomb   - used to know whether android bomb button was pressed
     */
    private void pressedPlaceBomb(Player player, boolean bomb) {
        int keyPressed = playersBombsKeys[player.getId() - 1];
        boolean pressedBomb = false;
        if (isAndroid)
            pressedBomb = bomb;
        else if (Gdx.input.isKeyPressed(keyPressed))
            pressedBomb = true;

        if (pressedBomb) {
            toPlaceBomb(player, keyPressed);
        } else if (playersBombsKeysPressed.get(keyPressed)) {
            if (player.isDistantExplode()) {
                player.setExplodeBombs(true);
            }
            playersBombsKeysPressed.put(keyPressed, false);
            player.setPressedBombButton(false);
        }
    }

    private void toPlaceBomb(Player player, int keyPressed){
        if (player.isDistantExplode() && (player.getPlacedBombs() == 0)) {
            player.placeBomb();
            player.setExplodeBombs(false);
        } else if (player.isSendingBombs() && !player.isMoving() && (!playersBombsKeysPressed.get(keyPressed))) {
            player.placeBomb();
        } else if (!player.isSendingBombs() || (player.isSendingBombs() && player.isMoving()))
            player.placeBomb();

        playersBombsKeysPressed.put(keyPressed, true);
        player.setPressedBombButton(true);
    }

    /**
     * Handles android input
     *
     * @param player - player to handle
     */
    private void handleAndroidInput(Player player) {
        androidController.handle();
        handleAndroidMove(player, androidController.getDir());
        pressedPlaceBomb(player, androidController.getBomb());

    }

    /**
     * Modes the player in Android
     *
     * @param player
     * @param dir
     */
    private void handleAndroidMove(Player player, int dir) {
        int id = player.getId() - 1;
        for (int i = 0; i < 4; i++) {
            if (dir == playersKeys[id][i]) {
                player.move(playersKeys[id][i]);
                playersKeysPressed.put(playersKeys[id][i], true);
            } else if (playersKeysPressed.get(playersKeys[id][i])) {
                player.stop(playersKeys[id][i]);
                playersKeysPressed.put(playersKeys[id][i], false);
            }
        }
    }

    /**
     * Moves the player in Desktop
     *
     * @param player
     */
    private void handlePlayersInput(Player player) {
        int id = player.getId() - 1;
        for (int i = 0; i < 4; i++) {
            if ((Gdx.input.isKeyPressed(playersKeys[id][i]))) {
                player.move(playersKeys[id][i]);
                playersKeysPressed.put(playersKeys[id][i], true);
            } else if (playersKeysPressed.get(playersKeys[id][i])) {
                player.stop(playersKeys[id][i]);
                playersKeysPressed.put(playersKeys[id][i], false);
            }
        }
    }

    /**
     * Handles common input
     */
    private void commonInputPressedKeys() {
        androidController.handle();
        pressedEscape = false;
        if (isAndroid) {
            pressedEscape = androidController.getEscape();
            androidController.setEscape(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            pressedEscape = true;

        pressedPause = false;
        if (isAndroid) {
            pressedPause = androidController.getPause();
            androidController.setPause(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            pressedPause = true;

        pressedPlus = false;
        if (isAndroid) {
            pressedPlus = androidController.getPlus();
            androidController.setPlus(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS))
            pressedPlus = true;

        pressedMinus = false;
        if (isAndroid) {
            pressedMinus = androidController.getMinus();
            androidController.setMinus(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
            pressedMinus = true;
    }

    /**
     * Returns gameOver
     *
     * @return gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns paused
     *
     * @return paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Handles common input
     */
    public void handleCommonInput() {

        commonInputPressedKeys();
        if (pressedPlus && GAMESPEED < Constants.MAX_SPEED)
            GAMESPEED += 0.1f;


        if (pressedMinus && GAMESPEED >= Constants.MIN_SPEED)
            GAMESPEED -= 0.1f;


        if (pressedEscape)
            gameOver = true;


        if (pressedPause)
            paused = !paused;

    }

}
