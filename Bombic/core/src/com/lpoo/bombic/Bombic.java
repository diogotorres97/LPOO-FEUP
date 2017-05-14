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


	@Override
	public void create () {
		batch = new SpriteBatch();

		gsm = new GameScreenManager(this);
		gam = new GameAssetManager();

		gam.create();
		gam.manager.finishLoading();
		gsm.setScreen(GameScreenManager.STATE.MENU);

	}

	public GameAssetManager getGam() {
		return gam;
	}


	@Override
	public void render () {

		super.render();

		/*if(gam.manager.update()){
			gam.done();

		}*/
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		gam.dispose();
		gsm.dispose();
	}
}
