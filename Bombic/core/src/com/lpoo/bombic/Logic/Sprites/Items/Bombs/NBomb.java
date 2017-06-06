package com.lpoo.bombic.Logic.Sprites.Items.Bombs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Creates NBomb
 */
public class NBomb extends Bomb {
    public NBomb(float x, float y) {
        super(x, y);
    }

    /**
     * Constructor
     */
    public void createBomb() {
        super.createBomb();
        fixture.setUserData(this);
        flamesVisibleID = 0;
        nFlames = true;
        endExplosionTime = 12f;
        explosionSound = gam.manager.get("sounds/nBomb.wav", Sound.class);
        setCategoryFilter(Constants.BOMB_BIT);
        cleanRegion = new TextureRegion(atlasBombs.findRegion("NBomb"), 16 * 50, 0, 50, 50);
    }


    @Override
    public void createAnimations() {

        super.createAnimations();

        for (int i = 0; i < nBombFlamesTiles.length; i++)
            nBombFlamesTiles[i] = Constants.FIRST_CONTINUOS_FLAME_TILE + i +1;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(atlasBombs.findRegion("NBomb"), i * 50, 0, 50, 50));
        tickingAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

    }

    protected void setVisibleTileID(float dt) {
        if (burnAndPreviewStateTime >= 0.2f) {
            burnAndPreviewStateTime = 0;
            if (visibleTileID == 2)
                visibleTileID = 0;
            else
                visibleTileID++;


        } else
            burnAndPreviewStateTime += dt;
    }




    protected void fireUpTiles() {

        for (int i = 0; i < freeCells.length; i++) {

            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++) {
                    if (freeCells[i][j] != null)
                        freeCells[i][j].setTile(tileSetNBombFlames.getTile(nBombFlamesTiles[flamesVisibleID]));
                }
        }

        getCell(0, 0).setTile(tileSetNBombFlames.getTile(nBombFlamesTiles[flamesVisibleID]));

    }

    @Override
    public void destroy() {
        super.destroy();
    }


}