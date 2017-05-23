package com.lpoo.bombic.Tools;

/**
 * Created by Rui Quaresma on 07/05/2017.
 */

public class Constants {
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
    public static final int SENDING = 7;
    public static final int MEGABOMB = 8;
    public static final int NAPALM = 9;
    public static final int SHIELD = 10;

    public static final int LIVE = 11;
    public static final int GREEN_DEAD = 12;
    public static final int FIRE_MAN = 13;

    public static final int TICKING_SPEED = 128;

    public static final float DIST_TRAVELED_VEL_1 = 0.0238095f ;
    public static final float AVG_DT = 0.0165346f;

}
