package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 25/05/2017.
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

        player.setnNBombs(2);
        destroy();
    }
}