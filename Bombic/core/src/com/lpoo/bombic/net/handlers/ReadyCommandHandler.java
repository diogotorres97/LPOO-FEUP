package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.ReadyCommand;

/**
 Created by pedro on 09/05/2017.
 */

public class ReadyCommandHandler
        implements IGameCommandHandler {

    private final MultiPlayerGame mMultiPlayerGame;
    private final ReadyCommand   mReadyCommand;

    public ReadyCommandHandler(final MultiPlayerGame pMultiPlayerGame, final AbstractGameCommand pGameCommand) {
        mMultiPlayerGame = pMultiPlayerGame;
        mReadyCommand = (ReadyCommand) pGameCommand;
    }

    public void run() {
       // mMultiPlayerGame.addPlayer(new Bomber(mMultiPlayerGame, 1, new Vector2(0, 0)));
        mMultiPlayerGame.gameReady(mReadyCommand.getPlayerId());
    }
}
