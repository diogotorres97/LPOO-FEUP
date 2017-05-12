package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class FlameBonus extends Bonus{
    public FlameBonus(float x, float y) {
        super(x, y);

    }
    public void createBonus(){
        super.createBonus();
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 0, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if(player.getFlames()<9)
            player.setFlames(1);
        destroy();
    }
}
