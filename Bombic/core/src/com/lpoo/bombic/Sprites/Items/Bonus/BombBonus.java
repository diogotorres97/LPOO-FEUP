package com.lpoo.bombic.Sprites.Items.Bonus;

import com.badlogic.gdx.Gdx;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class BombBonus extends Bonus {
    public BombBonus(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        setRegion(screen.getAtlasBonus().findRegion("bonus"), 50, 0, 50, 50);
        fixture.setUserData(this);

    }

    @Override
    public void apply(Bomber bomber) {
        if(bomber.getBombs()<9)
            bomber.setBombs(1);
        destroy();
    }
}
