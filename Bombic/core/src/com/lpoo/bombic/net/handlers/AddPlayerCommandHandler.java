package com.lpoo.bombic.net.handlers;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.MultiPlayerGame;
import com.lpoo.bombic.Sprites.Players.Bomber;
import com.lpoo.bombic.net.commands.AbstractGameCommand;
import com.lpoo.bombic.net.commands.AddPlayerCommand;

/**
 Created by pedro on 09/05/2017.
 */

public class AddPlayerCommandHandler
        implements IGameCommandHandler {

    private final MultiPlayerGame mMultiPlayerGame;
    private final AddPlayerCommand mAddPlayerCommand;

    public AddPlayerCommandHandler(final MultiPlayerGame pMultiPlayerGame, final AbstractGameCommand pGameCommand) {
        mMultiPlayerGame = pMultiPlayerGame;
        mAddPlayerCommand = (AddPlayerCommand) pGameCommand;
    }

    public void run() {
        mMultiPlayerGame.addPlayer(
                new Bomber(mMultiPlayerGame, 2, new Vector2(mAddPlayerCommand.getPosX(), mAddPlayerCommand.getPosY())));
    }
}
