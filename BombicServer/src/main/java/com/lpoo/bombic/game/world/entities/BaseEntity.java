package com.lpoo.bombic.game.world.entities;

import com.lpoo.bombic.game.world.World;

import java.util.concurrent.atomic.AtomicInteger;

/**
 Created by pedro on 07/05/2017.
 */
public abstract class BaseEntity {

    private static final float DEFAULT_POS_X = 0.0F;
    private static final float DEFAULT_POS_Y = 0.0F;

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int mEntityId;

    protected final World mWorld;

    private float mPosX;
    private float mPosY;

    protected boolean mToRemove = false;

    public BaseEntity(final World pWorld) {
        mEntityId = ID_GENERATOR.getAndIncrement();
        mWorld = pWorld;
        mPosX = DEFAULT_POS_X;
        mPosY = DEFAULT_POS_Y;
    }

    public abstract void doTick();

    public int getEntityId() {
        return mEntityId;
    }

    public float getPosX() {
        return mPosX;
    }

    public void setPosX(final float pPosX) {
        mPosX = pPosX;
    }

    public float getPosY() {
        return mPosY;
    }

    public void setPosY(final float pPosY) {
        mPosY = pPosY;
    }

    public void setToRemove() {
        mToRemove = true;
    }
}
