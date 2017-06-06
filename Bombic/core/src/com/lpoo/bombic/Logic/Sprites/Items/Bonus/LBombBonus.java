package com.lpoo.bombic.Logic.Sprites.Items.Bonus;

import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * LBombBonus
 */

public class LBombBonus extends Bonus {
    public LBombBonus(float x, float y) {
        super(x, y);
    }

    public void createBonus(){
        super.createBonus();
        id = Constants.MEGABOMB;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 250, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        super.apply(player);
        player.setnLBombs(2);
        destroy();
    }
}
