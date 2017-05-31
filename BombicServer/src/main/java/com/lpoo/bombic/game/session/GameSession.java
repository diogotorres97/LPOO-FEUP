package com.lpoo.bombic.game.session;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;


public class GameSession {

    private final GameManager    mGameManager;
    private final GameConnection mGameConnection;
    private final String         mUsername;


    public GameSession(final GameManager pGameManager, final GameConnection pGameConnection, final String pUsername) {
        mGameManager = pGameManager;
        mGameConnection = pGameConnection;
        mUsername = pUsername;
      }

    public void sendCommand(final AbstractGameCommand pGameCommand) {
        mGameConnection.sendCommand(pGameCommand);
    }

    public String getUsername() {
        return mUsername;
    }


}
