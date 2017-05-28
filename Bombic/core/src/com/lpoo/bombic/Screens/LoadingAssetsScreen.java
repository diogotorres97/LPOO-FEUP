package com.lpoo.bombic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Logic.Game;

/**
 * Created by Rui Quaresma on 27/05/2017.
 */

public class LoadingAssetsScreen extends AbstractScreen {

    private Image backgroundImg, loadingLabelImage;

    public LoadingAssetsScreen(final Bombic bombicGame) {
        super(bombicGame);
    }

    @Override
    public void show() {
        backgroundImg = new Image(new Texture(Gdx.files.internal("background.png")));
        backgroundImg.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        loadingLabelImage = new Image(new Texture(Gdx.files.internal("menus/labels/labelLoadingAssets.png")));
        loadingLabelImage.setSize(gamePort.getWorldWidth() / 3, loadingLabelImage.getHeight());

        stage.addActor(backgroundImg);
        stage.addActor(loadingLabelImage);
    }


    @Override
    public void setAvailableLevels(int level) {

    }

    @Override
    public void setNumLevel(int num) {

    }

    @Override
    public void setNumPlayers(int numPlayers) {

    }

    @Override
    public void setCurrentLevel(int level) {

    }

    @Override
    public void setMapId(int map_id) {

    }

    @Override
    public void setMonsters(boolean monsters) {

    }

    @Override
    public void setNumBonus(int numBonus) {

    }

    @Override
    public void setMaxVictories(int maxVictories) {

    }

    @Override
    public void setCurrentVictories(int[] currentVictories) {

    }

    @Override
    public void setGame(Game game) {

    }

    @Override
    public void update(float delta) {

    }
}
