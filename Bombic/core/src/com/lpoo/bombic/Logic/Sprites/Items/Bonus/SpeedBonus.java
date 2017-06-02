package com.lpoo.bombic.Logic.Sprites.Items.Bonus;

import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * SpeedBonus
 */

public class SpeedBonus extends Bonus{

    public SpeedBonus(float x, float y) {
        super(x, y);


    }
    public void createBonus(){
        super.createBonus();
        id = Constants.SPEED_BONUS;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 100, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        super.apply(player);
        if(player.getSpeedIncrease()<1.6f)
            player.setSpeedIncrease(0.4f);
        destroy();
    }
}
