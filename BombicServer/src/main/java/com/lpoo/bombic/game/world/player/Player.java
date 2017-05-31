package com.lpoo.bombic.game.world.player;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.entities.BaseEntity;

/**
 Created by pedro on 07/05/2017.
 */
public class Player
        extends BaseEntity {

    private static final int DEFAULT_SPEED = 3;

    private int mSpeed = DEFAULT_SPEED;

    public Player(final World pWorld) {
        super(pWorld);
    }

    @Override
    public void doTick() {
    }
}
