package com.lpoo.bombic.Tools;

/**
 * Constants used in the game
 */
public class Constants {
    public static final String LEVELFILE = "AvailableLevels";

    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100;


    public static final short NOTHING_BIT = 0;
    public static final short BOMBER_BIT = 1;
    public static final short ENEMY_BIT = 2;
    public static final short DESTROYABLE_OBJECT_BIT = 4;
    public static final short DESTROYED_BIT = 8;
    public static final short OBJECT_BIT = 16;
    public static final short BOMB_BIT = 32;
    public static final short FLAMES_BIT = 64;
    public static final short BONUS_BIT = 128;


    public static final int BOMB_BONUS = 1;
    public static final int FLAME_BONUS = 2;
    public static final int SPEED_BONUS = 3;
    public static final int DEAD_BONUS = 4;
    public static final int DISTANT_EXPLODE = 5;
    public static final int KICKING = 6;
    public static final int MEGABOMB = 7;
    public static final int NAPALM = 8;
    public static final int SENDING = 9;

    public static final int TICKING_SPEED = 128;

    public static final int[] OBJECTS_TILES = new  int[]{20, 12, 13, 14,15, 16,17, 18, 19,41, 42, 43,44, 45, 46, 47, 48, 49, 50,51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,
            64, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80};

    public static int BARREL_TILE = 31;
    public static int BLANK_TILE = 11;
    public static int FIRST_CONTINUOS_FLAME_TILE = 130;
    public static int FIRST_FLAME_TILE = 80;

    public static float MAX_SPEED = 3.9f;
    public static float MIN_SPEED = 0.8f;

}
