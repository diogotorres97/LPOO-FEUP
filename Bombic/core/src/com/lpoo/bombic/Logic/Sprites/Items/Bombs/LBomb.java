package com.lpoo.bombic.Logic.Sprites.Items.Bombs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Creates LBomb
 */
public class LBomb extends Bomb {


    private TiledMapTileLayer.Cell[][] square3X3FreeCells;
    private Vector2[][][] square3X3Vertices;

    public LBomb(float x, float y) {
        super(x, y);
    }

    /**
     * Constructor
     */
    public void createBomb() {
        super.createBomb();
        fixture.setUserData(this);
        endExplosionTime = 6.5f;
        explosionSound = gam.manager.get("sounds/lBomb.wav", Sound.class);
        setCategoryFilter(Constants.BOMB_BIT);
        cleanRegion = new TextureRegion(atlasBombs.findRegion("LBomb"), 16 * 50, 0, 50, 50);
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
            frames.add(new TextureRegion(atlasBombs.findRegion("LBomb"), i * 50, 0, 50, 50));
        tickingAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

    }

    protected void redefineBombShape() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (square3X3Vertices[i][j] != null) {
                    FixtureDef fdef = new FixtureDef();
                    fdef.filter.categoryBits = fixture.getFilterData().categoryBits;
                    fdef.filter.maskBits = Constants.DESTROYABLE_OBJECT_BIT |
                            Constants.BOMBER_BIT |
                            Constants.OBJECT_BIT |
                            Constants.BOMB_BIT |
                            Constants.BONUS_BIT |
                            Constants.ENEMY_BIT;


                    PolygonShape shape = new PolygonShape();

                    Vector2[] aux_v = new Vector2[4];
                    aux_v[0] = square3X3Vertices[i][j][0];
                    aux_v[1] = square3X3Vertices[i][j][1];
                    aux_v[2] = square3X3Vertices[i][j][2];
                    aux_v[3] = square3X3Vertices[i][j][3];

                    shape.set(aux_v);
                    fdef.isSensor = true;
                    fdef.shape = shape;


                    fixture = body.createFixture(fdef);
                    fixture.setUserData(this);
                }
            }
        }
    }

    protected Vector2[] createVertices() {

        Vector2[] vertices = new Vector2[0];

        return vertices;
    }

    private void check3X3Tiles() {
        float xPos = getWidth() / 2 - 0.02f;
        float yPos = getHeight() / 2 - 0.02f;

        square3X3Vertices = new Vector2[3][3][4];
        square3X3FreeCells = new TiledMapTileLayer.Cell[3][3];

        for (int i = -1; i < 2; i++) {
            TiledMapTileLayer.Cell[] arrayCellsAux = new TiledMapTileLayer.Cell[3];
            boolean atLeastOne = false;
            for (int j = -1; j < 2; j++) {
                TiledMapTileLayer.Cell auxCell = getCell(i * 50, j * 50);
                if (auxCell != null)
                    if (!isObjectTile(auxCell.getTile().getId())) {
                        arrayCellsAux[j + 1] = auxCell;
                        atLeastOne = true;

                        square3X3Vertices[i + 1][j + 1][0] = new Vector2(-xPos + 0.02f + i * 0.5f, -yPos + 0.02f + j * 0.5f);
                        square3X3Vertices[i + 1][j + 1][1] = new Vector2(-xPos + 0.02f + i * 0.5f, yPos - 0.02f + j * 0.5f);
                        square3X3Vertices[i + 1][j + 1][2] = new Vector2(xPos - 0.02f + i * 0.5f, yPos - 0.02f + j * 0.5f);
                        square3X3Vertices[i + 1][j + 1][3] = new Vector2(xPos - 0.02f + i * 0.5f, -yPos + 0.02f + j * 0.5f);
                    } else
                        square3X3Vertices[i + 1][j + 1] = null;
            }

            if (atLeastOne)
                square3X3FreeCells[i + 1] = arrayCellsAux;
            else
                square3X3FreeCells[i + 1] = null;
        }

    }

    protected void checkFreeTiles(int range) {
        check3X3Tiles();
    }

    protected void fireUpTiles() {

        for (int i = 0; i < square3X3FreeCells.length; i++) {
            if (square3X3FreeCells[i] != null)
                for (int j = 0; j < square3X3FreeCells[i].length; j++) {
                    if (square3X3FreeCells[i][j] != null) {
                        if (!isContinuosFlame(square3X3FreeCells[i][j].getTile().getId()))
                            square3X3FreeCells[i][j].setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + burningAnimationTiles[3][visibleTileID]));
                    }
                }
        }
    }

    protected void flashTiles() {

        for (int i = 0; i < square3X3FreeCells.length; i++) {
            if (square3X3FreeCells[i] != null)
                for (int j = 0; j < square3X3FreeCells[i].length; j++) {
                    if (square3X3FreeCells[i][j] != null) {
                        if (!isContinuosFlame(square3X3FreeCells[i][j].getTile().getId()) && !isFlameTile(square3X3FreeCells[i][j].getTile().getId()) && square3X3FreeCells[i][j].getTile().getId() != Constants.BARREL_TILE)
                            square3X3FreeCells[i][j].setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + previewAnimationTiles[visibleTileID]));
                    }
                }
        }
    }


    protected void resetFreeTiles() {
        for (int i = 0; i < square3X3FreeCells.length; i++)
            if (square3X3FreeCells[i] != null)
                for (int j = 0; j < square3X3FreeCells[i].length; j++)
                    if (square3X3FreeCells[i][j] != null)
                        square3X3FreeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));


    }

    protected void clearTickingTiles() {
        for (int i = 0; i < square3X3FreeCells.length; i++)
            if (square3X3FreeCells[i] != null)
                for (int j = 0; j < square3X3FreeCells[i].length; j++)
                    if (square3X3FreeCells[i][j] != null)
                        if (isTickingTile(square3X3FreeCells[i][j].getTile().getId()))
                            square3X3FreeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));

    }

    @Override
    public void destroy() {
        super.destroy();

    }

}
