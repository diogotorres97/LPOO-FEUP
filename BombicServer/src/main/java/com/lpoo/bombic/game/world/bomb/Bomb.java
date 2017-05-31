package com.lpoo.bombic.game.world.bomb;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.entities.BaseEntity;
import com.lpoo.bombic.game.world.player.Player;
import com.lpoo.bombic.game.scheduler.GameTimer;

/**
 Created by pedro on 07/05/2017.
 */
public class Bomb
        extends BaseEntity {

    private static final int BOMB_TIME_OUT = 3000;

    private final Player mPlayer;

    private int mTickTime;

    public Bomb(final Player pPlayer, final World pWorld) {
        super(pWorld);
        mPlayer = pPlayer;
    }

    @Override
    public void doTick() {
        if ((mTickTime += GameTimer.DELTA) >= BOMB_TIME_OUT) {
            mTickTime = 0;
            this.explode();
            mToRemove = true;
        }
    }

    private void explode() {

    }
}
