package com.lpoo.bombic.game.world.player;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.block.Block;
import com.lpoo.bombic.game.world.entities.BaseEntity;
import com.lpoo.bombic.game.world.entities.EntityManager;
import com.lpoo.bombic.game.world.greyball.Greyball;

import java.util.HashMap;

/**
 Created by pedro on 11/05/2017.
 */
public class PlayerManager {

    private final HashMap<Integer, Player> mPlayers = new HashMap<>();

    private final World         mWorld;
    private final EntityManager mEntityManager;

    public PlayerManager(final World pWorld) {
        mWorld = pWorld;
        mEntityManager = mWorld.getEntityManager();
    }

    public void doTick() {
        for (final Player player : mPlayers.values()) {
            for (final BaseEntity entity : mEntityManager.getEntities()) {
                if (player == entity) {
                    continue;
                }
                if (entity instanceof Block) {
                    //TODO add collision detection!
                } else if (entity instanceof Greyball) {
                    this.killPlayer(player);
                }
            }
        }
    }

    private void killPlayer(final Player pPlayer) {
        //TODO send commands!
        mPlayers.remove(pPlayer.getEntityId());
        pPlayer.setToRemove();
    }
}
