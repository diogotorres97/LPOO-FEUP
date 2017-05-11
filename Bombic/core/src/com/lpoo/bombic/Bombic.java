package com.lpoo.bombic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.bombic.Screens.MenuScreen;
import com.lpoo.bombic.Screens.PlayScreen;

public class Bombic extends Game {


	public SpriteBatch batch;

	private int currentLevel;
	private int numLevels;
	private int availableLevels;

	private com.lpoo.bombic.Game game;

	@Override
	public void create () {
		batch = new SpriteBatch();

		currentLevel = 1;
		availableLevels = 3;
		numLevels = 3;

		setScreen(new MenuScreen(this));
	}

	public void setGame(com.lpoo.bombic.Game game){
		this.game = game;
	}

	public com.lpoo.bombic.Game getGame(){
		return game;
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
		batch.dispose();
	}
}
