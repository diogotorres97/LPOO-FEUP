package com.lpoo.bombic.game.world.greyball;

import com.lpoo.bombic.game.resources.GreyballResource;
import com.lpoo.bombic.game.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 Created by pedro on 11/05/2017.
 */
public class GreyballManager {

    private final HashMap<Integer, Greyball> mGreyballs = new HashMap<>();

    private final World mWorld;

    public GreyballManager(final World pWorld) {
        mWorld = pWorld;
    }

    public void init(final ArrayList<GreyballResource> pGreyballResources) {
        for (final GreyballResource greyballResource : pGreyballResources) {
            final Greyball greyball = new Greyball(greyballResource, mWorld);
            mGreyballs.put(greyball.getEntityId(), greyball);
            mWorld.getEntityManager()
                  .addEntity(greyball);
        }
    }
}
