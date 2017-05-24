package com.lpoo.bombic.net.handlers;

import com.lpoo.bombic.net.GameConnection;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.DropBombCommand;

/**
 Created by pedro on 07/05/2017.
 */
public class DropBombCommandHandler
        implements IGameCommandHandler {

    private final GameConnection  mGameConnection;
    private final DropBombCommand mDropBombCommand;

    public DropBombCommandHandler(final GameConnection pGameConnection, final AbstractGameCommand pGameCommand) {
        mGameConnection = pGameConnection;
        mDropBombCommand = (DropBombCommand) pGameCommand;
    }

    public void run() {

    }
}
