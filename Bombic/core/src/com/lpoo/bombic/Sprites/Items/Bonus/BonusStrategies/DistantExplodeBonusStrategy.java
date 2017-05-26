package com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies;

import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 21/05/2017.
 */

public class DistantExplodeBonusStrategy implements BonusStrategy {

    private float timeLeft = 100;
    private Player player;

    @Override
    public void apply(Player player) {
        this.player = player;
        timeLeft -= player.getGame().getGameSpeed() / 6;

        player.setDistantExplode(true);

        if (timeLeft <= 0) {
            destroyBonus();
        }
    }

    public void destroyBonus(){
        player.setBadBonusActive(false);
        player.setDestroyBonus(true);
        player.setDistantExplode(false);
        player.setExplodeBombs(false);
    }

    public float getTimeLeft() {
        return timeLeft;
    }



}