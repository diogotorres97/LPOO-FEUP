package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;
import com.lpoo.bombic.Sprites.Items.Item;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Bomb extends Item {

    protected enum State {TICKING, BURNING, DESTROYED};
    protected State currentState;
    protected State previousState;
    protected float tickingStateTime;
    protected float burnAndPreviewStateTime;
    protected int visibleTileID;

    protected Animation<TextureRegion> tickingAnimation;
    protected TextureRegion cleanRegion;
    protected Bomber bomber;

    protected int[][] burningAnimationTiles;
    protected int[] previewAnimationTiles;
    protected int[] explodableTiles;

    protected int[] xAddCell; //used to determinate if there are any free cells around the bomb (up, right, down, left) in the X axis
    protected int[] yAddCell; //used to determinate if there are any free cells around the bomb (up, right, down, left) in the Y axis
    protected TiledMap map;

    protected static TiledMapTileSet tileSetFlames;
    protected static TiledMapTileSet tileSetMap;
    protected int firstTileSetID;
    protected TiledMapTileLayer.Cell[][] freeCells;

    protected final int BLANK_TILE = 11;
    protected final int BARREL_TILE = 31;
    protected final int BLANK_BURNED_TILE = 36;

    protected Fixture fixture;



    public Bomb(PlayScreen screen, float x, float y, Bomber bomber) {

        super(screen, x, y);
        this.map = screen.getMap();
        this.bomber = bomber;
        tileSetFlames = map.getTileSets().getTileSet("flames");
        firstTileSetID = Integer.parseInt(tileSetFlames.getProperties().get("firstID").toString()) - 1;
        tileSetMap = map.getTileSets().getTileSet(map.getProperties().get("main_tile_set").toString());

        //each Array<TiledMapTileLayer.Cell> represents a direction : UP, RIGHT, DOWN, LEFT
        freeCells = new TiledMapTileLayer.Cell[4][bomber.getFlames()];

        //each Array<Integer> contains the ids of the tiles that represent different directions : UP, RIGHT, DOWN, LEFT, MIDDLE, MIDDLE_UP_DOWN, MIDDLE_RIGHT_LEFT
        burningAnimationTiles= new int[7][3];
        previewAnimationTiles = new int[3];

        //Num of tiles that will explode in each direction : UP, RIGHT, DOWN, LEFT
        explodableTiles = new int[4];

        //creation of a clean texture region
        cleanRegion = new TextureRegion(screen.getAtlasBombs().findRegion("classicBomb"),16 * 50, 0, 50, 50);

        //UP             RIGHT             DOWN             LEFT
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        visibleTileID = 0;

        checkFreeTiles(bomber.getFlames());
        createAnimations(bomber);



    }

    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(23 / Bombic.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    protected void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    protected abstract void createAnimations(Bomber bomber);

    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd){

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        int xPos = (int)((body.getPosition().x * Bombic.PPM / 50) + xAdd / 50);
        int yPos = (int)(body.getPosition().y * Bombic.PPM / 50 + yAdd / 50);


        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    protected void checkFreeTiles(int range){

        for(int i = 0 ; i < 4; i++) {
            TiledMapTileLayer.Cell[] arrayCellsAux = new TiledMapTileLayer.Cell[range];
            boolean atLeastOne = false;

            for (int j = 1; j < range + 1; j++) {
                TiledMapTileLayer.Cell auxCell = getCell(j * xAddCell[i], j * yAddCell[i]);
                if(auxCell != null) {
                    if (auxCell.getTile().getId() == BLANK_TILE || auxCell.getTile().getId() == BLANK_BURNED_TILE) {
                        arrayCellsAux[j - 1] = getCell(j * xAddCell[i], j * yAddCell[i]);
                        atLeastOne = true;

                    }
                    if (auxCell.getTile().getId() == BLANK_TILE || auxCell.getTile().getId() == BLANK_BURNED_TILE || auxCell.getTile().getId() == BARREL_TILE ) {
                        explodableTiles[i]++;
                    }


                }
            }
            if(atLeastOne)
                freeCells[i] = arrayCellsAux;
            else
                freeCells[i] = null;
        }


    }
    protected void setVisibleTileID(float dt){
        if(burnAndPreviewStateTime >= 0.2f) {
            burnAndPreviewStateTime = 0;
            if(visibleTileID == 2)
                visibleTileID = 0;
            else
                visibleTileID++;
        }
        else
            burnAndPreviewStateTime += dt;

    }

    protected void fireUpTiles(){

        //UP, RIGHT, DOWN, LEFT animations
        int [][] idsAnimations = {{4, 5}, {2, 1}, {6, 5}, {0, 1}};

        for(int i = 0 ; i < freeCells.length ; i++) {

            if(freeCells[i] != null)
                 for (int j = 0; j < freeCells[i].length; j++) {
                    if(j == freeCells[i].length - 1)
                        freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[idsAnimations[i][0]][visibleTileID]));
                    else
                        freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[idsAnimations[i][1]][visibleTileID]));

                 }

        }

        getCell(0, 0).setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[3][visibleTileID]));

    }

    protected void flashTiles(){

        for(int i = 0 ; i < 4 ; i++) {
            if(freeCells[i] != null) {
                for (int j = 0; j < freeCells[i].length; j++) {
                    freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + previewAnimationTiles[visibleTileID]));

                }

            }


        }
    }

    protected void resetFreeTiles(){
        for(int i = 0 ; i < 4 ; i++)
            if(freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++)
                    freeCells[i][j].setTile(tileSetMap.getTile(BLANK_TILE));
        getCell(0, 0).setTile(tileSetMap.getTile(BLANK_TILE));
    }

    @Override
    public void destroy() {
        super.destroy();
        bomber.setBombs(1);
    }

}
