package com.lpoo.bombic.Logic;

/**
 * Multiplayer game
 */

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.MultiPlayerInputController;
import com.lpoo.bombic.net.SocketManager;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.LoginRequest;
import com.lpoo.bombic.net.commands.MoveCommand;
import com.lpoo.bombic.net.commands.MoveRequest;
import com.lpoo.bombic.net.handlers.IGameCommandHandler;

import java.util.concurrent.ConcurrentLinkedDeque;

public class MultiPlayerGame
        extends Game {

    private final SocketManager mSocketManager = new SocketManager(this);

    private final ConcurrentLinkedDeque<IGameCommandHandler> mCommandHandlers = new ConcurrentLinkedDeque<>();

    private int[] key;
    private int mPlayerId;
    public MoveCommand mMoveCommand;
    protected MultiPlayerInputController inputControllerMP;


    public MultiPlayerGame() {

        super(2, 2);
        this.setReady(false);
        this.hasEnemies = false;
        this.numBonus = 0;
        this.max_victories = 1;
        this.current_vics = new int [] {0,0};
        this.map_id = 1;

        loadMap();
        createWorld();
        inputControllerMP = new MultiPlayerInputController(this);

        key = new int[2];

        this.init();
    }

    private void init() {
        if (mSocketManager.init()) {
            mSocketManager.sendCommand(new LoginRequest(""));
        }
    }

    public void gameReady(final int pPlayerId) {
        this.setReady(true);
        mPlayerId = pPlayerId;
    }

    @Override
    public void update(float dt) {
        this.dequeuServerCommands();


        if (getReady()) {
            key = this.inputControllerMP.getKeyPressed();
            if (key[0] != -1) {
                playersUpdate(dt, new int[]{mPlayerId, key[1]});
                mSocketManager.sendCommand(new MoveRequest(key[1]));
            }
            if (mMoveCommand != null) {
                playersUpdate(dt, new int[]{mMoveCommand.getPlayerId(), mMoveCommand.getKey()});
                mMoveCommand = null;
            }
            removeObjectsToDestroy();
            itemUpdate(dt);

            gameEnds();

        }
    }

    private void dequeuServerCommands() {
        IGameCommandHandler commandHandler;
        while ((commandHandler = mCommandHandlers.poll()) != null) {
            commandHandler.run();
        }
    }

    public void sendCommand(final AbstractGameCommand pGameCommand) {
        mSocketManager.sendCommand(pGameCommand);
    }

    public void addGameHandler(final IGameCommandHandler pCommandHandler) {
        mCommandHandlers.add(pCommandHandler);
    }


    private void playersUpdate(float dt, int[] input) {
        Player[] playersToRemove = new Player[players.length];

        int id = 0;
        switch (input[0]){
            case 1:
                if (!players[0].isDead()) {
                    if (!players[0].isDying())
                        inputControllerMP.handleInput(players[0], input[1]);

                    handleSpawningItems(players[0]);
                    players[0].update(dt);
                } else
                    playersToRemove[id] = players[0];
                id++;
                break;
            case 2:
                if (!players[1].isDead()) {
                    if (!players[1].isDying())
                        inputControllerMP.handleInput(players[1], input[1]);

                    handleSpawningItems(players[1]);
                    players[1].update(dt);
                } else
                    playersToRemove[id] = players[1];
                id++;
                break;

            default:
                break;
        }
    }

    private void loadMap() {
        map = mapLoader.load("levels/dm_" + map_id + ".tmx");

    }

    private void createWorld() {
        creator = new B2WorldCreator(this);

        if (hasEnemies) {
            creator.startEnemyCreation();
            enemies = creator.getEnemies();
        }
        creator.setNumBonus(numBonus);
    }

    @Override
    protected void createBombers() {
        pos1 = new Vector2(75, 475);
        pos2 = new Vector2(725, 75);
        pos3 = new Vector2(75, 75);
        pos4 = new Vector2(725, 475);
        super.createBombers();
    }

    @Override
    public void pause() {
        for (Player player : players) {
            player.pause();
        }
        if (hasEnemies) {
            for (Enemy enemy : enemies) {
                enemy.pause();
            }
        }

    }


    @Override
    public void gameEnds() {
        if (players.length == 1) {
            setLevelWon(true);
            current_vics[players[0].getId() - 1]++;
        }
    }


}