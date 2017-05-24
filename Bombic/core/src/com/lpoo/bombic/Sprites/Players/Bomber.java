package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.MultiPlayerGame;
import com.lpoo.bombic.net.commands.MoveCommand;
/**
 Created by pedro on 10/05/2017.
 */

public class Bomber
        extends Player {

    private final MultiPlayerGame mMultiPlayerGame;

    public Bomber(final MultiPlayerGame pMultiPlayerGame, final int pBomberId, final Vector2 pPos) {
        super(pMultiPlayerGame, pBomberId, pPos);
        mMultiPlayerGame = pMultiPlayerGame;
    }

    @Override
    public void move(final int dir) {
        mMultiPlayerGame.sendCommand(new MoveCommand(this.getId(), dir));
    }

    @Override
    public void stop(final int dir) {

    }
}