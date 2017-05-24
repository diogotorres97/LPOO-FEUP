package com.lpoo.bombic.game.session;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.world.player.Player;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;

/**
 Created by pedro on 07/05/2017.
 */
public class GameSession {

    private final GameManager    mGameManager;
    private final GameConnection mGameConnection;
    private final String         mUsername;
    private final Player         mPlayer;

    public GameSession(final GameManager pGameManager, final GameConnection pGameConnection, final String pUsername) {
        mGameManager = pGameManager;
        mGameConnection = pGameConnection;
        mUsername = pUsername;
        mPlayer = new Player(mGameManager.getWorld());
    }

    public void sendCommand(final AbstractGameCommand pGameCommand) {
        mGameConnection.sendCommand(pGameCommand);
    }

    public String getUsername() {
        return mUsername;
    }

    public int getPlayerId() {
        return mPlayer.getEntityId();
    }
}
