package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 22/05/2017.
 */

public class KickingBonus extends Bonus {
    public KickingBonus(float x, float y) {
        super(x, y);

    }

    public void createBonus() {
        super.createBonus();
        id = Constants.KICKING;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 400, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if (!player.isKickingBombs())
            player.setKickingBombs(true);
        destroy();
    }
}