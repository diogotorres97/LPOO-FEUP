package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies.DeadBonusStrategy;
import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 21/05/2017.
 */

public class DeadBonus extends Bonus{
    public DeadBonus(float x, float y) {
        super(x, y);
        strategy = new DeadBonusStrategy();

    }
    public void createBonus(){
        super.createBonus();
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 150, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if(!player.isBadBonusActive()) {
            if(visible) {
                player.setBadBonusActive(true);
                player.setBadBonus(this);
                setInvisible();
            }else
                destroy();
        }

        strategy.apply(player);


    }
}
