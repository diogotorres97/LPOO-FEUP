package com.lpoo.bombic.Logic.Sprites.Items.Bonus;

import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * FlameBonus
 */

public class FlameBonus extends Bonus{
    public FlameBonus(float x, float y) {
        super(x, y);

    }
    public void createBonus(){
        super.createBonus();
        id = Constants.FLAME_BONUS;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 0, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        super.apply(player);
        if(player.getFlames()<9)
            player.setFlames(1);
        destroy();
    }
}
