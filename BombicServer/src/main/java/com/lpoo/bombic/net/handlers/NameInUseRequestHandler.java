package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.NameInUseCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameInUseRequestHandler
        implements IGameCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    private final GameConnection   mGameConnection;
    private final NameInUseCommand mNameInUseCommand;

    public NameInUseRequestHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mNameInUseCommand = (NameInUseCommand) pGameCommand;
    }

    @Override
    public void run() {
        final GameSession gameSession = mGameConnection.getGameSession();
        if(gameSession != null) {
            final GameManager gameManager = mGameConnection.getGameManager();
            final GameSession gameSession1 = gameManager.getGameSession1();
            final GameSession gameSession2 = gameManager.getGameSession2();
            if (gameSession == gameSession1) {
                gameSession2.sendCommand(new NameInUseCommand());
                gameManager.setGameSession1();
            } else {
                gameSession1.sendCommand(new NameInUseCommand());
                gameManager.setGameSession2();
            }
        }
    }

}
