package com.lpoo.bombic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.bombic.Managers.GameAssetManager;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Screens.MenuScreen;

public class Bombic extends Game {


	public SpriteBatch batch;
	public GameScreenManager gsm;
	public GameAssetManager gam;

	private int currentLevel;
	private int numLevels;
	private int availableLevels;

	@Override
	public void create () {
		batch = new SpriteBatch();

		gam = new GameAssetManager();
		gsm = new GameScreenManager(this);


		currentLevel = 1;
		availableLevels = 3;
		numLevels = 3;

		setScreen(new MenuScreen(this));
	}

	public void setCurrentLevel(int lvl){
		currentLevel = lvl;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setAvailableLevels(int num){
		availableLevels = num;
	}

	public int getAvailableLevels() {
		return availableLevels;
	}

	public int getNumLevel() {
		return numLevels;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		gam.dispose();
		gsm.dispose();
	}
}
