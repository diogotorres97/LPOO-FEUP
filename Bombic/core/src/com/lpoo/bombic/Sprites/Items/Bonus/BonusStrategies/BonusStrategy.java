package com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies;

import com.lpoo.bombic.Sprites.Players.Player;

import java.util.Random;

/**
 * Created by Rui Quaresma on 21/05/2017.
 */

public interface BonusStrategy {
    Random rand = new Random();
    public abstract void apply(Player player);
    public abstract void destroyBonus();
}
