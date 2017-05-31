package com.lpoo.bombic.game.world.bomb;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.player.Player;

import java.util.HashMap;

/**
 Created by pedro on 07/05/2017.
 */
public class BombManager {

    private final HashMap<Integer, Bomb> mBombs = new HashMap<>();

    private final World mWorld;

    public BombManager(final World pWorld) {
        mWorld = pWorld;
    }

    public void doTick() {

    }

    public void dropBomb(final Player pPlayer) {
        //        final Bomb bomb = new Bomb(pPlayerEntity);
        //        mBombs.put(bomb.getEntityId(), bomb);
    }


}
