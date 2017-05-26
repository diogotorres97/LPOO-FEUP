package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class BombBonus extends Bonus {
    public BombBonus(float x, float y) {
        super(x, y);


    }

    public void createBonus(){
        super.createBonus();
        id = Constants.BOMB_BONUS;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 50, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if(player.getBombs()<9)
            player.setBombs(1);
        destroy();
    }
}
