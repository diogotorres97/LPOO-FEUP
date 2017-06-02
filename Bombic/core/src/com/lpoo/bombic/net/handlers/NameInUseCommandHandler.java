package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.GameLogic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.NameInUseCommand;

public class NameInUseCommandHandler
        implements IGameCommandHandler {

    private final MultiPlayerGame  mMultiPlayerGame;
    private final NameInUseCommand mNameInUseCommand;

    public NameInUseCommandHandler(final MultiPlayerGame pMultiPlayerGame, final AbstractGameCommand pGameCommand) {
        mMultiPlayerGame = pMultiPlayerGame;
        mNameInUseCommand = (NameInUseCommand) pGameCommand;
    }

    public void run() {
        mMultiPlayerGame.mNameInUseCommand= mNameInUseCommand;
        mMultiPlayerGame.gameEnds();
    }
}
