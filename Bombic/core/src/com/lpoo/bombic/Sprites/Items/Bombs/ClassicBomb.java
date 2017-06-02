package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Creates a classic bomb
 */
public class ClassicBomb extends Bomb {
    public ClassicBomb(float x, float y) {
        super(x, y);
    }

    /**
     * Constructor
     */
    public void createBomb() {
        super.createBomb();
        fixture.setUserData(this);

        endExplosionTime = 4.5f;

        setCategoryFilter(Constants.BOMB_BIT);
        cleanRegion = new TextureRegion(atlasBombs.findRegion("classicBomb"), 16 * 50, 0, 50, 50);
    }


    @Override
    public void createAnimations() {

        super.createAnimations();

        for (int i = 0; i < burningAnimationTiles.length; i++) {
            for (int j = 0; j < 3; j++) {
                burningAnimationTiles[i][j] = j * 10 + 1 + i;
            }
        }
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(atlasBombs.findRegion("classicBomb"), i * 50, 0, 50, 50));
        tickingAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

    }

    protected void fireUpTiles() {

        //UP, RIGHT, DOWN, LEFT animations
        int[][] idsAnimations = {{4, 5}, {2, 1}, {6, 5}, {0, 1}};

        for (int i = 0; i < freeCells.length; i++) {

            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++) {
                    if (freeCells[i][j] != null && !isContinuosFlame(freeCells[i][j].getTile().getId())) {
                        if (j == freeCells[i].length - 1)
                            freeCells[i][j].setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + burningAnimationTiles[idsAnimations[i][0]][visibleTileID]));
                        else
                            freeCells[i][j].setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + burningAnimationTiles[idsAnimations[i][1]][visibleTileID]));

                    }
                }

        }
        getCell(0, 0).setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + burningAnimationTiles[3][visibleTileID]));

    }

    @Override
    public void destroy() {
        super.destroy();
        player.setPlacedBombs(-1);

    }


}

