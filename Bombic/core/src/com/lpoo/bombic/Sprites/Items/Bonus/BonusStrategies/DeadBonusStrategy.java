package com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies;

import com.badlogic.gdx.Gdx;
import com.lpoo.bombic.Sprites.Players.Player;

/**
 * Created by Rui Quaresma on 21/05/2017.
 */

public class DeadBonusStrategy implements BonusStrategy {
    private Player player;
    private float timeLeft = 200;

    private int effect = 2;

    @Override
    public void apply(Player player) {
        this.player = player;
        timeLeft -= player.getGame().getGameSpeed() / 2;

        activateEffect();
        if (timeLeft <= 0) {
            player.setBadBonusActive(false);
            player.setDestroyBonus(true);
            deactivateEffect();
        }
    }

    private void activateEffect() {
        switch (effect) {
            case 0:
                if (!player.isStop())
                    player.setStop(true);
                break;
            case 1:
                if (!player.isDontBomb())
                    player.setDontBomb(true);
                break;
            case 2:
                if (!player.isKeepBombing())
                    player.setKeepBombing(true);
                break;
            default:
                break;
        }
    }

    private void deactivateEffect() {
        switch (effect) {
            case 0:
                player.setStop(false);
                break;
            case 1:
                player.setDontBomb(false);
                break;
            case 2:
                player.setKeepBombing(false);
                break;
            default:
                break;
        }
    }
}
