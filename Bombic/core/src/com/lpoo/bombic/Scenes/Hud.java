package com.lpoo.bombic.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lpoo.bombic.Bombic;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public Hud(SpriteBatch sb){
        viewport = new FitViewport(Bombic.V_WIDTH, Bombic.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //table to input the bombers info
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //create textureRegions (Images) 1 at each corner, showing only
        //the number of players, and has to has labels for the numbers
        //bonus as well

        //which means, only add to the table certain number of actors (images)
    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
