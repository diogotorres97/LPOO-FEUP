package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Creates an abstract screen used to implement the other
 */

public abstract class AbstractScreen implements Screen {

    protected final Bombic bombicGame;
    protected Stage stage;
    protected Viewport gamePort;
    protected Sound soundEnter, soundEscape;

    /**
     * Constructor
     * @param bombicGame
     */
    public AbstractScreen(final Bombic bombicGame) {
        this.bombicGame = bombicGame;
        gamePort = new FitViewport(Constants.V_WIDTH , Constants.V_HEIGHT);
        stage = new Stage(gamePort, bombicGame.batch);
        soundEnter = gam.manager.get("sounds/menuEnter.wav", Sound.class);
        soundEscape = gam.manager.get("sounds/menuEscape.wav", Sound.class);
    }

    @Override
    public void show() {

    }


    /**
     * Sets the number of available levels
     * @param level
     */
    public abstract void setAvailableLevels(int level);

    /**
     * Sets whether the game is multiDevices or not
     * @param multiGame
     */
    public abstract void setMultiGame(boolean multiGame);

    /**
     * Sets the number of levels
     * @param num
     */
    public abstract void setNumLevel(int num);
    /**
     * Sets the number of players
     * @param numPlayers
     */
    public abstract void setNumPlayers(int numPlayers);

    /**
     * Sets the current level
     * @param level
     */
    public abstract void setCurrentLevel(int level);

    /**
     * Sets the map id
     * @param map_id
     */
    public abstract void setMapId(int map_id);

    /**
     * Sets whether there are monsters or not
     * @param monsters
     */
    public abstract void setMonsters(boolean monsters);

    /**
     * Sets the number of bonus
     * @param numBonus
     */
    public abstract void setNumBonus(int numBonus);

    /**
     * Sets the maximum number of victories
     * @param maxVictories
     */
    public abstract void setMaxVictories(int maxVictories);

    /**
     * Sets the number of current players each player has
     * @param currentVictories
     */
    public abstract void setCurrentVictories(int[] currentVictories);

    /**
     * Sets the game
     * @param game
     */
    public abstract void setGame(Game game);

    /**
     * Update game
     * @param delta
     */
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
