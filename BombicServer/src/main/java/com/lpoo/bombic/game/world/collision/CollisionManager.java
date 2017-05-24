package com.lpoo.bombic.game.world.collision;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.entities.BaseEntity;
import com.lpoo.bombic.game.world.entities.EntityManager;

/**
 Created by pedro on 11/05/2017.
 */
public class CollisionManager {

    private final World         mWorld;
    private final EntityManager mEntityManager;

    public CollisionManager(final World pWorld) {
        mWorld = pWorld;
        mEntityManager = mWorld.getEntityManager();
    }

    public void doTick() {
        for (final BaseEntity entity1 : mEntityManager.getEntities()) {
            for (final BaseEntity entity2 : mEntityManager.getEntities()) {
                if (entity1 == entity2) {
                    continue;
                }

            }
        }
    }
}
