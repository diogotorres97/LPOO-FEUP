package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.NullGameSessionCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NullGameSessionHandler
        implements IGameCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    private final GameConnection   mGameConnection;
    private final NullGameSessionCommand mNullGameSessionCommand;

    public NullGameSessionHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mNullGameSessionCommand = (NullGameSessionCommand) pGameCommand;
    }

    @Override
    public void run() {
        final GameSession gameSession = mGameConnection.getGameSession();
        if(gameSession != null) {
            final GameManager gameManager = mGameConnection.getGameManager();
            final GameSession gameSession1 = gameManager.getGameSession1();
            final GameSession gameSession2 = gameManager.getGameSession2();
            if (gameSession == gameSession1) {
                gameManager.setGameSession1();
            } else if (gameSession == gameSession2) {
                gameManager.setGameSession2();
            }


        }
    }

}
