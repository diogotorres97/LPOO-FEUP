package com.lpoo.bombic.game.world.greyball;

import com.lpoo.bombic.game.resources.GreyballResource;
import com.lpoo.bombic.game.world.World;
import com.lpoo.bombic.game.world.entities.BaseEntity;

/**
 Created by pedro on 07/05/2017.
 */
public class Greyball
        extends BaseEntity {

    private final GreballAI mGreballAI = new GreballAI(this);

    private final GreyballResource mGreyballResource;

    public Greyball(final GreyballResource pGreyballResource, final World pWorld) {
        super(pWorld);
        mGreyballResource = pGreyballResource;
    }

    @Override
    public void doTick() {
        mGreballAI.doTick();
    }
}
