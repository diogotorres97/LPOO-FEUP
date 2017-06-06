package com.lpoo.bombic.Logic.Sprites.Items.Bonus.BonusStrategies;

import com.lpoo.bombic.Logic.Sprites.Players.Player;

import static com.lpoo.bombic.Logic.GameLogic.Game.GAMESPEED;

public class DistantExplodeBonusStrategy implements BonusStrategy {

    private float timeLeft = 100;
    private Player player;

    @Override
    public void apply(Player player) {
        this.player = player;
        timeLeft -= GAMESPEED / 6;

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