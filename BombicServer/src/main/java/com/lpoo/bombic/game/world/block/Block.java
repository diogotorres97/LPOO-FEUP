package com.lpoo.bombic.game.world.block;

import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.entities.BaseEntity;
import com.lpoo.bombic.game.resources.BlockResource;

/**
 Created by pedro on 11/05/2017.
 */
public class Block
        extends BaseEntity {

    public static final int BARREL_TYPE_ID = 1;
    public static final int ROCK_TYPE_ID   = 2;

    private static final int BLOCK_WIDTH  = 50;
    private static final int BLOCK_HEIGHT = 50;

    private final BlockResource mBlockResource;

    public Block(final BlockResource pBlockResource, final World pWorld) {
        super(pWorld);
        mBlockResource = pBlockResource;
    }

    @Override
    public void doTick() {

    }

    public int getBlockType() {
        return mBlockResource.getBlockType();
    }
}
