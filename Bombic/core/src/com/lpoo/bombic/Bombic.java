package com.lpoo.bombic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.bombic.Managers.GameAssetManager;
import com.lpoo.bombic.Managers.GameScreenManager;

/**
 * Game main class
 */
public class Bombic extends Game {
    public SpriteBatch batch;
    public GameScreenManager gsm;
    public static boolean soundsOn, isAndroid, hasJoystick, hasAccelerometer;
    public static GameAssetManager gam;

    /**
     * Creates the game. Initializes the sprite batch, the asset manager, the screen manager.
     * Also after the game asset manager finishes loading the Assets, sets the screen to the MenuScreen.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();


        gam = new GameAssetManager();
        gam.create();
        gam.manager.finishLoading();

        createStaticVariables();

        gsm = new GameScreenManager(this);
        gsm.setScreen(GameScreenManager.STATE.MENU);

    }

    /**
     * Creates static variables (some of them depend on whether the game is running on Android or Desktop.
     */
    private void createStaticVariables(){
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            isAndroid = true;
            hasJoystick = true;
            hasAccelerometer = true;
        }
        else {
            isAndroid = false;
            hasJoystick = false;
            hasAccelerometer = false;
        }

        soundsOn = true;
    }

    /**
     * Returns the GameAssetManager used in the project
     *
     * @return gam - GameAssetManager
     */
    public GameAssetManager getGam() {
        return gam;
    }

    /**
     * Disposes of the created resources
     */
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gam.dispose();
        gsm.dispose();
    }
}
