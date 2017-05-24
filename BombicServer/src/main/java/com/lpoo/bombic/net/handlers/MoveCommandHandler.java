package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.MoveCommand;

/**
 Created by pedro on 07/05/2017.
 */
public class MoveCommandHandler
        implements IGameCommandHandler {

    private final GameConnection mGameConnection;
    private final MoveCommand    mMoveCommand;

    public MoveCommandHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mMoveCommand = (MoveCommand) pGameCommand;
    }

    public void run() {

    }
}
