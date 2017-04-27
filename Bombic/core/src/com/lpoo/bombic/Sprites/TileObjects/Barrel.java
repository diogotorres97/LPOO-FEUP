package com.lpoo.bombic.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;

/**
 * Created by Rui Quaresma on 20/04/2017.
 */

public class Barrel extends InteractiveTileObject {
    public Barrel(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Bombic.BARREL_BIT);
    }

    @Override
    public void explode() {
        Gdx.app.log("Barrel", "Collision");
        getCell().setTile(tileSetMap.getTile(BLANK_BURNED_TILE));
        setCategoryFilter(Bombic.DESTROYED_BIT);

    }
}
