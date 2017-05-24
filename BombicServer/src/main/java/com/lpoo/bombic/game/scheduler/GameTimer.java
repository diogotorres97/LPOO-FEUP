package com.lpoo.bombic.game.scheduler;

/**
 Created by pedro on 03/03/2017.
 */
public class GameTimer {

    public static final long TICKS_PER_SECOND = 30;
    public static final long TIME_PER_TICK    = 1000 / TICKS_PER_SECOND;

    public static long CURRENT_TIME = System.nanoTime();

    /**
     Delta Time in milliseconds
     */
    public static float DELTA = 0F;

    public static void updateTime() {
        DELTA = GameTimer.getNewDelta();
        CURRENT_TIME = System.nanoTime();
    }

    public static long getElapsedTime() {
        return System.nanoTime() - CURRENT_TIME;
    }

    public static float getNewDelta() {
        return GameTimer.getElapsedTime() / 1000000F;
    }

}