package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Input;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Logic.Sprites.Players.Player;

import java.util.HashMap;

import static com.badlogic.gdx.Gdx.input;

public class MultiPlayerInputController {

    /**
     * HashMap that contains players keys pressed
     */
    private HashMap<Integer, Boolean> playersKeysPressed;
    /**
     * Array that contains each player keys
     */
    private int[][]                   playersKeys;
    /**
     * Array that contains each player bomb keys
     */
    private int[]                     playersBombsKeys;
    /**
     * HashMap that contains players bomb keys pressed
     */
    private HashMap<Integer, Boolean> playersBombsKeysPressed;

    /**
     * Game that contains actual game
     */
    private Game game;

    /**
     * Constructor of the MultiPlayerInputController
     */
    public MultiPlayerInputController(Game game) {
        this.game = game;

        playersKeysPressed = new HashMap<Integer, Boolean>();
        playersKeys = new int[4][4];

        initiatePlayer1Keys();
        initiatePlayer2Keys();

        initiateBombKeys();
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
     * Initiates the bombKeys array
     */
    private void initiateBombKeys() {
        playersBombsKeys = new int[2];
        playersBombsKeysPressed = new HashMap<Integer, Boolean>();

        playersBombsKeys[0] = Input.Keys.CONTROL_RIGHT;
        playersBombsKeys[1] = Input.Keys.CONTROL_LEFT;

        playersBombsKeysPressed.put(Input.Keys.CONTROL_RIGHT, false);
        playersBombsKeysPressed.put(Input.Keys.CONTROL_LEFT, false);

    }

    /**
     * Calls the handle Input methods
     */

    public void handleInput(Player player, int input) {
        pressedPlaceBomb(player, input);
        handlePlayersInput(player, input);

    }

    /**
     * Moves the player in Desktop
     *
     * @param player
     */
    private void handlePlayersInput(Player player, int input) {
        for(int testInput : playersKeys[player.getId()-1]){
            if (input == testInput) {
                player.move(testInput);
                playersKeysPressed.put(testInput, true);
                return;
            }else {
                player.stop(testInput);
                playersKeysPressed.put(testInput, false);
            }
        }


    }

    private void pressedPlaceBomb(Player player, int input) {
        int keyPressed = playersBombsKeys[player.getId() - 1];
        boolean pressedBomb = false;

        if(keyPressed==input)
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

    private void toPlaceBomb(Player player, int keyPressed) {
        if (player.isDistantExplode()) {
            if ((player.getPlacedBombs() == 0)) {
                player.placeBomb();
                player.setExplodeBombs(false);
            }
        } else if (player.isSendingBombs() && !player.isMoving() && (!playersBombsKeysPressed.get(keyPressed))) {
            player.placeBomb();
        } else if (!player.isSendingBombs() || (player.isSendingBombs() && player.isMoving()))
            player.placeBomb();

        playersBombsKeysPressed.put(keyPressed, true);
        player.setPressedBombButton(true);
    }

    /**
     * Get KeyPressed by user
     *
     * @return a integer array with playerID and keyPressed
     *
     */
    public int[] getKeyPressed() {

        int[] idKey = new int[2];

        for( int i = 0; i < playersKeys.length; i++) {
            idKey[0] = i+1;
            for (int testInput : playersKeys[i]) {
                if (input.isKeyPressed(testInput)) {
                    idKey[1] = testInput;
                    return idKey;
                }
            }
        }

        for(int i=0; i < playersBombsKeys.length; i++){
            if(input.isKeyPressed(playersBombsKeys[i])){
                idKey[0] = i+1;
                idKey[1] = playersBombsKeys[i];
                return idKey;
            }
        }
        idKey[0]=-1;
        return idKey;
    }
}
