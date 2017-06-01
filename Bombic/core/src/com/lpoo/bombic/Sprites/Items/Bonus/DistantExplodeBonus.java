package com.lpoo.bombic.Sprites.Items.Bonus;

import com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies.DeadBonusStrategy;
import com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies.DistantExplodeBonusStrategy;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * DistantExplodeBonus
 */

public class DistantExplodeBonus extends Bonus{
    public DistantExplodeBonus(float x, float y) {
        super(x, y);


    }
    public void createBonus(){
        super.createBonus();
        id = Constants.DISTANT_EXPLODE;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 200, 0, 50, 50);
        fixture.setUserData(this);
    }

    @Override
    public void apply(Player player) {
        if(!active){
            active = true;
            strategy = new DistantExplodeBonusStrategy();
            if (player.isBadBonusActive()) {
                player.getBadBonus().getStrategy().destroyBonus();
                player.getBadBonus().destroy();
            }
            player.setBadBonusActive(true);
            player.setBadBonus(this);
            setInvisible();
        }else{
            if(player.isBadBonusActive())
                strategy.apply(player);
            else
                destroy();
        }


    }
}
