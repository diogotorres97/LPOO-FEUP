package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 22/05/2017.
 */

public class KickingBonus extends Bonus{
    public KickingBonus(float x, float y) {
        super(x, y);

    }
    public void createBonus(){
        super.createBonus();
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 400, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        player.setKickingBombs(true);
        destroy();
    }
}