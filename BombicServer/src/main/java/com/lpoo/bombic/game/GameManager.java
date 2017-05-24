package com.lpoo.bombic.game;

import com.lpoo.bombic.game.resources.ResourcesManager;
import com.lpoo.bombic.game.scheduler.GameScheduler;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.net.commands.AddPlayerCommand;
import com.lpoo.bombic.net.commands.ReadyCommand;

/**
 Created by pedro on 07/05/2017.
 */
public class GameManager {

    private final ResourcesManager mResourcesManager = new ResourcesManager();
    private final GameScheduler    mGameScheduler    = new GameScheduler(this);

    private final World mWorld = new World();


    private GameSession mGameSession1;
    private GameSession mGameSession2;

    public void init() {
        mResourcesManager.init();
        mWorld.init(mResourcesManager);
        mGameScheduler.startSimulation();
    }

    public void doTick() {
        mWorld.doTick();
    }

    public void addGameSession(final GameSession pGameSession) {
        if (mGameSession1 == null) {
            mGameSession1 = pGameSession;
        } else if (mGameSession2 == null) {
            mGameSession2 = pGameSession;
            this.initGame();
        }
    }

    //    public GameSession getSessionByUsername(final String pUsername) {
    //        if (mGameSession1.getUsername()
    //                         .equals(pUsername)) {
    //            return mGameSession1;
    //        } else if (mGameSession2.getUsername()
    //                                .equals(pUsername)) {
    //            return mGameSession2;
    //        }
    //        return null;
    //    }

    private void initGame() {
        mGameSession1.sendCommand(new ReadyCommand(mGameSession1.getPlayerId(), mGameSession1.getUsername()));
        mGameSession1.sendCommand(new AddPlayerCommand(mGameSession2.getPlayerId(), 0.0F, 0.0F));
        mGameSession2.sendCommand(new ReadyCommand(mGameSession2.getPlayerId(), mGameSession2.getUsername()));
        mGameSession2.sendCommand(new AddPlayerCommand(mGameSession1.getPlayerId(), 5.0F, 5.0F));

    }


    public ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }

    public GameScheduler getGameScheduler() {
        return mGameScheduler;
    }

    public World getWorld() {
        return mWorld;
    }

}
