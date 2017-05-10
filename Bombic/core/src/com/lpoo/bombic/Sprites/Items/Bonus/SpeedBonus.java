package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Game;
import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class SpeedBonus extends Bonus{

    public SpeedBonus(Game game, float x, float y) {
        super(game, x, y);
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 150, 0, 50, 50);
        fixture.setUserData(this);

    }

    @Override
    public void apply(Player player) {
        if(player.getSpeedIncrease()<1.6f)
            player.setSpeedIncrease(0.4f);
        destroy();
    }
}
