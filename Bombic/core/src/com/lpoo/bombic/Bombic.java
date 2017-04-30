package com.lpoo.bombic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.bombic.Screens.PlayScreen;

public class Bombic extends Game {
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 600;
	public static final float PPM = 100;
	public static float GAME_SPEED = 2;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short BOMBER_BIT = 2;
	public static final short DESTROYABLE_OBJECT_BIT = 4;
	public static final short DESTROYED_BIT = 8;
    public static final short OBJECT_BIT = 16;
	public static final short CLASSIC_BOMB_BIT = 32;
	public static final short FLAMES_BIT = 64;
	public static final short BONUS_BIT = 128;

	public static final int BOMB_BONUS = 1;
	public static final int FLAME_BONUS = 2;
	public static final int SPEED_BONUS = 3;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//Alter to MenuScreen, in witch we are able to access  other screens
		setScreen(new PlayScreen(this, 1));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
