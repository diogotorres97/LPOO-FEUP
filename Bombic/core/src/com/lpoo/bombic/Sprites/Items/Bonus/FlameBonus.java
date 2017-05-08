package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Players.Bomber;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class FlameBonus extends Bonus{
    public FlameBonus(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        setRegion(screen.getAtlasBonus().findRegion("bonus"), 0, 0, 50, 50);
        fixture.setUserData(this);

    }

    @Override
    public void apply(Bomber bomber) {
        if(bomber.getFlames()<9)
            bomber.setFlames(1);
        destroy();
    }
}
