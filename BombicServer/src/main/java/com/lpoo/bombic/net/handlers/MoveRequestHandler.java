package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.MoveCommand;
import com.lpoo.bombic.net.commands.MoveRequest;

/**
 Created by pedro on 07/05/2017.
 */
public class MoveRequestHandler
        implements IGameCommandHandler {

    private final GameConnection mGameConnection;
    private final MoveRequest mMoveRequest;

    public MoveRequestHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mMoveRequest = (MoveRequest) pGameCommand;
    }

    public void run() {
        final GameSession gameSession = mGameConnection.getGameSession();
        if(gameSession != null) {
            final GameManager gameManager = mGameConnection.getGameManager();
            final GameSession gameSession1 = gameManager.getGameSession1();
            final GameSession gameSession2 = gameManager.getGameSession2();
            if (gameSession == gameSession1) {
                gameSession2.sendCommand(new MoveCommand(1, mMoveRequest.getKey()));
            } else {
                gameSession1.sendCommand(new MoveCommand(2, mMoveRequest.getKey()));
            }
        }
    }
}
