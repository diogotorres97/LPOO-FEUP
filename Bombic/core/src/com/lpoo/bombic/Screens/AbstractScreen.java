package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Managers.GameScreenManager;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 12/05/2017.
 */

public abstract class AbstractScreen implements Screen {

    protected Bombic bombicGame;
    protected Stage stage;
    protected Viewport gamePort;

    public AbstractScreen(Bombic bombicGame) {
        this.bombicGame = bombicGame;
        gamePort = new FitViewport(Constants.V_WIDTH , Constants.V_HEIGHT);
        stage = new Stage(gamePort, bombicGame.batch);
    }

    @Override
    public void show() {

    }

    public abstract void setNumPlayers(int numPlayers);

    public abstract void setCurrentLevel(int level);

    public abstract void setMapId(int map_id);

    public abstract void setMonsters(boolean monsters);

    public abstract void setNumBonus(int numBonus);

    public abstract void setMaxVictories(int maxVictories);

    public abstract void setCurrentVictories(int[] currentVictories);

    public abstract void setGame(Game game);

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
