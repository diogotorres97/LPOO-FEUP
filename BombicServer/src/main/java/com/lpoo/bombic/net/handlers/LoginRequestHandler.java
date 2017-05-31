package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.game.GameManager;
import com.lpoo.bombic.game.session.GameSession;
import com.lpoo.bombic.net.GameConnection;

import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by pedro on 07/05/2017.
 */
public class LoginRequestHandler
        implements IGameCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequestHandler.class);

    private final GameConnection mGameConnection;
    private final LoginRequest mLoginRequest;

    public LoginRequestHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mLoginRequest = (LoginRequest) pGameCommand;
    }

    @Override
    public void run() {
        final GameManager gameManager = mGameConnection.getGameManager();
        //        if (gameManager.getSessionByUsername(mLoginCommand.getUsername()) != null) {
        //            mGameConnection.sendCommand(new NameInUseCommand());
        //            return;
        //        }
        final GameSession gameSession = new GameSession(gameManager, mGameConnection, mLoginRequest.getUsername());
        gameManager.addGameSession(gameSession);
        mGameConnection.setGameSession(gameSession);
    }
}