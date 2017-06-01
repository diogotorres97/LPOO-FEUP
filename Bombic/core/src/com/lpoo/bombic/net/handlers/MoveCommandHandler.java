package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.Logic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.MoveCommand;


public class MoveCommandHandler
        implements IGameCommandHandler {

    private final MultiPlayerGame mMultiPlayerGame;
    private final MoveCommand     mMoveCommand;

    public MoveCommandHandler(final MultiPlayerGame pMultiPlayerGame, final AbstractGameCommand pGameCommand) {
        mMultiPlayerGame = pMultiPlayerGame;
        mMoveCommand = (MoveCommand) pGameCommand;
    }

    public void run() {
        mMultiPlayerGame.mMoveCommand = mMoveCommand;
    }

}
