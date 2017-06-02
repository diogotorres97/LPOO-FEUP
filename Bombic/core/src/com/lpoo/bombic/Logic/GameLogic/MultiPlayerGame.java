package com.lpoo.bombic.Logic.GameLogic;


import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.Tools.MultiPlayerInputController;
import com.lpoo.bombic.net.SocketManager;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.LoginRequest;
import com.lpoo.bombic.net.commands.MoveCommand;
import com.lpoo.bombic.net.commands.MoveRequest;
import com.lpoo.bombic.net.commands.NameInUseCommand;
import com.lpoo.bombic.net.commands.NullGameSessionCommand;
import com.lpoo.bombic.net.handlers.IGameCommandHandler;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * MultiPlayer game
 */
public class MultiPlayerGame
        extends Game {

    private final SocketManager mSocketManager = new SocketManager(this);

    private final ConcurrentLinkedDeque<IGameCommandHandler> mCommandHandlers = new ConcurrentLinkedDeque<IGameCommandHandler>();

    private int[] key;
    private int mPlayerId;
    public MoveCommand mMoveCommand;
    protected MultiPlayerInputController inputControllerMP;
    public NameInUseCommand mNameInUseCommand;


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
                mSocketManager.sendCommand(new MoveRequest(key[1]));
                playersUpdate(dt, new int[]{mPlayerId, key[1]});
            }
            if (mMoveCommand != null) {
                playersUpdate(dt, new int[]{mMoveCommand.getPlayerId(), mMoveCommand.getKey()});
                mMoveCommand = null;
            }
            removeObjectsToDestroy();
            itemUpdate(dt);

            this.gameEnds();

        }
    }

    @Override
    public void pause() {

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
        if(input[0]==-1)
            return;

        input[0]-=1;

        if (!players[input[0]].isDead()) {
            if (!players[input[0]].isDying())
                inputControllerMP.handleInput(players[input[0]], input[1]);

            handleSpawningItems(players[input[0]]);
            players[input[0]].update(dt);
        } else{
            playersToRemove[id] = players[input[0]];
        }

        id++;

        removePlayers(playersToRemove);
    }

    private void loadMap() {
        map = mapLoader.load("levels/dm_" + map_id + ".tmx");

    }

    private void createWorld() {
        creator = new B2WorldCreator(this);

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
    public void gameEnds() {
        if (players.length == 1) {
            if(mNameInUseCommand==null){
                current_vics[(mPlayerId-1==0?1:0)]++;
                mSocketManager.sendCommand(new NameInUseCommand());
                mNameInUseCommand=null;
            }
            this.dequeuServerCommands();
            setGameOver(true);
        }

        if (mNameInUseCommand!= null){
           current_vics[(mPlayerId-1==0?0:1)]++;
            mSocketManager.sendCommand(new NullGameSessionCommand());
            mNameInUseCommand=null;
            this.dequeuServerCommands();
            setGameOver(true);
        }
    }


}