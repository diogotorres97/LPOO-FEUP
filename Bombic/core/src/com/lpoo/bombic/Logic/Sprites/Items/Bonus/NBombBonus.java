package com.lpoo.bombic.Logic.Sprites.Items.Bonus;

import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * NBombBonus
 */

public class NBombBonus extends Bonus {
    public NBombBonus(float x, float y) {
        super(x, y);
    }

    public void createBonus(){
        super.createBonus();
        id = Constants.NAPALM;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 300, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        super.apply(player);
        player.setnNBombs(2);
        destroy();
    }
}