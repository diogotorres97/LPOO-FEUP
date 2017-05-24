package com.lpoo.bombic.game.world.entities;

import com.lpoo.bombic.game.world.World;

import java.util.Collection;
import java.util.HashMap;

/**
 Created by pedro on 07/05/2017.
 */
public class EntityManager {

    private final HashMap<Integer, BaseEntity> mEntities = new HashMap<>();

    private final World mWorld;

    public EntityManager(final World pWorld) {
        mWorld = pWorld;
    }

    public void doTick() {
        for (final BaseEntity baseEntity : mEntities.values()) {
            baseEntity.doTick();
        }
    }

    public Collection<BaseEntity> getEntities() {
        return mEntities.values();
    }

    public void addEntity(final BaseEntity pEntity) {
        mEntities.put(pEntity.getEntityId(), pEntity);
    }

    public void removeEntity(final BaseEntity pEntity) {
        mEntities.remove(pEntity.getEntityId());
    }
}