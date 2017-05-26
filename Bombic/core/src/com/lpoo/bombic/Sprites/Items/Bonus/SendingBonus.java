package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 22/05/2017.
 */

public class SendingBonus extends Bonus {
    public SendingBonus(float x, float y) {
        super(x, y);

    }

    public void createBonus() {
        super.createBonus();
        id = Constants.SENDING;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 500, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if (!player.isSendingBombs())
            player.setSendingBombs(true);
        destroy();
    }
}