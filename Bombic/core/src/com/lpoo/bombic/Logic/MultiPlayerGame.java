package com.lpoo.bombic.Logic;

/**
 * Created by Torres on 24-05-2017.
 */

import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.B2WorldCreator;
import com.lpoo.bombic.net.SocketManager;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.LoginRequest;
import com.lpoo.bombic.net.handlers.IGameCommandHandler;

import java.util.concurrent.ConcurrentLinkedDeque;

public class MultiPlayerGame extends Game {

    private final SocketManager mSocketManager = new SocketManager(this);

    private final ConcurrentLinkedDeque<IGameCommandHandler> mCommandHandlers = new ConcurrentLinkedDeque<>();

    public MultiPlayerGame(int map_id, int numPlayers, int mode, boolean hasEnemies, int numBonus, int max_victories, int[] current_vics) {
        super(numPlayers, mode);
        this.setReady(false);
        this.hasEnemies = false;
        this.numBonus = 0;
        this.max_victories = max_victories;
        this.current_vics = current_vics;
        this.map_id = map_id;

        loadMap();
        createWorld();

        this.init();
    }

    private void init() {
        if (mSocketManager.init()) {
            mSocketManager.sendCommand(new LoginRequest(""));
        }
    }

    public void gameReady() {
        this.setReady(true);
    }

    @Override
    public void update(float dt) {
        this.dequeuServerCommands();

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


    private void loadMap() {
        map = mapLoader.load("dm_" + 1 + ".tmx");

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
       // pos1 = new Vector2(75, 475);
       // pos2 = new Vector2(725, 75);
       // pos3 = new Vector2(75, 75);
       // pos4 = new Vector2(725, 475);
       // super.createBombers();
    }

    @Override
    public void pause() {
        for (Player player : players) {
            player.pause();
        }
        if (hasEnemies)
            for (Enemy enemy : enemies) {
                enemy.pause();
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
