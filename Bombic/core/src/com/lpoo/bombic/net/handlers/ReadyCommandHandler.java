package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.GameLogic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.ReadyCommand;


public class ReadyCommandHandler
        implements IGameCommandHandler {

    private final MultiPlayerGame mMultiPlayerGame;
    private final ReadyCommand   mReadyCommand;

    public ReadyCommandHandler(final MultiPlayerGame pMultiPlayerGame, final AbstractGameCommand pGameCommand) {
        mMultiPlayerGame = pMultiPlayerGame;
        mReadyCommand = (ReadyCommand) pGameCommand;
    }

    public void run() {
        mMultiPlayerGame.gameReady(mReadyCommand.getPlayerId());
    }
}
