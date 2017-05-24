package com.lpoo.bombic.game.world;

import com.lpoo.bombic.game.resources.ResourcesManager;
import com.lpoo.bombic.game.world.block.BlockManager;
import com.lpoo.bombic.game.world.bomb.BombManager;
import com.lpoo.bombic.game.world.entities.EntityManager;
import com.lpoo.bombic.game.world.greyball.GreyballManager;

/**
 Created by pedro on 11/05/2017.
 */
public class World {

    private static final int WIDTH_BLOCKS  = 9;
    private static final int HEIGHT_BLOCKS = 14;

    private final BlockManager    mBlockManager    = new BlockManager(this);
    private final BombManager     mBombManager     = new BombManager(this);
    private final EntityManager   mEntityManager   = new EntityManager(this);
    private final GreyballManager mGreyballManager = new GreyballManager(this);

    private int mWidth  = WIDTH_BLOCKS;
    private int mHeight = HEIGHT_BLOCKS;

    public void init(final ResourcesManager pResourcesManager) {
        mBlockManager.init(pResourcesManager.getBlockResources());
        mGreyballManager.init(pResourcesManager.getGreyballResources());
    }

    public void doTick() {
        mBombManager.doTick();
        mEntityManager.doTick();
    }

    public BlockManager getBlockManager() {
        return mBlockManager;
    }

    public BombManager getBombManager() {
        return mBombManager;
    }

    public EntityManager getEntityManager() {
        return mEntityManager;
    }

    public GreyballManager getGreyballManager() {
        return mGreyballManager;
    }
}
