package com.lpoo.bombic.Sprites.Items.Bonus;

import com.badlogic.gdx.Gdx;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 24/05/2017.
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

        player.setnLBombs(2);
        destroy();
    }
}
