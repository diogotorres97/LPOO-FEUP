package com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies;

import com.lpoo.bombic.Sprites.Players.Player;

import java.util.Random;

/**
 * Bonus strategy for bad bonus, which are timed
 */
public interface BonusStrategy {
    Random rand = new Random();

    /**
     * Applies bonus to the player
     * @param player
     */
    public abstract void apply(Player player);
    public abstract void destroyBonus();

    public abstract float getTimeLeft();
}
