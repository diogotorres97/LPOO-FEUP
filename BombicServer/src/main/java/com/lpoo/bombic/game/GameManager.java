package com.lpoo.bombic.game;

import com.lpoo.bombic.game.scheduler.GameScheduler;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.net.commands.ReadyCommand;

public class GameManager {

    private final GameScheduler    mGameScheduler    = new GameScheduler(this);

    private GameSession mGameSession1;
    private GameSession mGameSession2;

    public void init() {
         mGameScheduler.startSimulation();
    }

    public void doTick() {

    }

    public void addGameSession(final GameSession pGameSession) {
        if (mGameSession1 == null) {
            mGameSession1 = pGameSession;
        } else if (mGameSession2 == null) {
            mGameSession2 = pGameSession;
            this.initGame();
        }
    }

    private void initGame() {
        mGameSession1.sendCommand(new ReadyCommand(1, mGameSession1.getUsername()));
        mGameSession2.sendCommand(new ReadyCommand(2, mGameSession2.getUsername()));

    }

    public GameSession getGameSession1() {
        return mGameSession1;
    }

    public GameSession getGameSession2() {
        return mGameSession2;
    }

    public GameScheduler getGameScheduler() {
        return mGameScheduler;
    }

}
