package com.lpoo.bombic.Logic.Sprites.Items.Bonus;

import com.badlogic.gdx.audio.Sound;
import com.lpoo.bombic.Logic.Sprites.Items.Bonus.BonusStrategies.DeadBonusStrategy;
import com.lpoo.bombic.Logic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * DeadBonus
 */

public class DeadBonus extends Bonus {
    public DeadBonus(float x, float y) {
        super(x, y);

    }

    public void createBonus() {
        super.createBonus();
        id = Constants.DEAD_BONUS;
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        setRegion(atlasBonus.findRegion("bonus"), 150, 0, 50, 50);
        fixture.setUserData(this);
        applyBonusSound = gam.manager.get("sounds/badBonus.wav", Sound.class);

    }

    @Override
    public void apply(Player player) {
        super.apply(player);
        if (!active) {
            active = true;
            strategy = new DeadBonusStrategy();
            if (player.isBadBonusActive()) {
                player.getBadBonus().getStrategy().destroyBonus();
                player.getBadBonus().destroy();
            }
            player.setBadBonusActive(true);
            player.setBadBonus(this);
            setInvisible();
        } else {
            if (player.isBadBonusActive())
                strategy.apply(player);
            else {
                destroy();

            }
        }

    }
}
