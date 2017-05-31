package com.lpoo.bombic.game.world.block;

import com.lpoo.bombic.game.resources.BlockResource;
import com.lpoo.bombic.game.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 Created by pedro on 11/05/2017.
 */
public class BlockManager {

    private final HashMap<Integer, Block> mBlocks = new HashMap<>();

    private final World mWorld;

    public BlockManager(final World pWorld) {
        mWorld = pWorld;
    }

    public void init(final ArrayList<BlockResource> pBlockResources) {
        for (final BlockResource blockResource : pBlockResources) {
            final Block block = new Block(blockResource, mWorld);
            mBlocks.put(block.getEntityId(), block);
            mWorld.getEntityManager()
                  .addEntity(block);
        }
    }
}
