package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;
import com.lpoo.bombic.Sprites.Items.Item;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Bomb extends Item {

    protected enum State {TICKING, BURNING, DESTROYED}

    ;
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
    protected int numVerticesBomb;

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

    protected boolean redefinedBomb;
    protected boolean contactableBomb;


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
        burningAnimationTiles = new int[7][3];
        previewAnimationTiles = new int[3];

        //Num of tiles that will explode in each direction : UP, RIGHT, DOWN, LEFT
        explodableTiles = new int[4];
        numVerticesBomb = 0;

        //creation of a clean texture region
        cleanRegion = new TextureRegion(screen.getAtlasBombs().findRegion("classicBomb"), 16 * 50, 0, 50, 50);

        //UP             RIGHT             DOWN             LEFT
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        visibleTileID = 0;

        checkFreeTiles(bomber.getFlames());
        createAnimations(bomber);

        redefinedBomb = false;
        contactableBomb = false;

    }

    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //Create bomb shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Bombic.PPM);
        fdef.filter.maskBits = Bombic.BARREL_BIT |
                Bombic.OBJECT_BIT |
                Bombic.GROUND_BIT;
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public void redefineBomb() {
        redefinedBomb = true;
        Vector2 currentPosition = body.getPosition();

        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //Create bomber shape

        redefineBombShape();

        Gdx.app.log("Width", "" + getWidth());
        Gdx.app.log("Height", "" + getHeight());
        Gdx.app.log("X", "" + getX());
        Gdx.app.log("Y", "" + getY());

    }

    private void redefineBombShape() {
        Vector2[] vertices = createVertices();
        int numVertices = vertices.length;
        Gdx.app.log("LENGTH", "" + numVertices);

        for (int i = 0; i < numVertices; i += 4) {
            Gdx.app.log("aa", "bb");
            FixtureDef fdef = new FixtureDef();
            fdef.filter.categoryBits = fixture.getFilterData().categoryBits;
            fdef.filter.maskBits = Bombic.BARREL_BIT |
                    Bombic.BOMBER_BIT |
                    Bombic.OBJECT_BIT |
                    Bombic.GROUND_BIT;




           /* EdgeShape shape = new EdgeShape();

            shape.set(vertices[i], vertices[i+1]);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(fixture.getUserData());

            shape.set(vertices[i+1], vertices[i+2]);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(fixture.getUserData());

            shape.set(vertices[i+2], vertices[i+3]);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(fixture.getUserData());

            shape.set(vertices[i+3], vertices[i]);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(fixture.getUserData());*/

            PolygonShape shape = new PolygonShape();

            Vector2[] aux_v = new Vector2[4];
            aux_v[0] = vertices[i];
            aux_v[1] = vertices[i + 1];
            aux_v[2] = vertices[i + 2];
            aux_v[3] = vertices[i + 3];
            Gdx.app.log("X", "" + vertices[i].x);
            Gdx.app.log("Y", "" + vertices[i].y);

            Gdx.app.log("X", "" + vertices[i+1].x);
            Gdx.app.log("Y", "" + vertices[i+1].y);

            Gdx.app.log("X", "" + vertices[i+2].x);
            Gdx.app.log("Y", "" + vertices[i+2].y);

            Gdx.app.log("X", "" + vertices[i+3].x);
            Gdx.app.log("Y", "" + vertices[i+3].y);

            shape.set(aux_v);
            fdef.isSensor = true;
            fdef.shape = shape;

            body.createFixture(fdef).setUserData(fixture.getUserData());


        }


    }

    private Vector2[] createVertices() {
        int idVertice = 4;
        float xPos = getWidth() / 2 - 0.01f;
        float yPos = getHeight() / 2 - 0.01f;
        Vector2[] vertices = new Vector2[4 + 4 * numVerticesBomb];
        Gdx.app.log("X", "" + getWidth());
        Gdx.app.log("Y", "" + getHeight());
        vertices[0] = new Vector2(-xPos, yPos);
        vertices[1] = new Vector2(xPos, yPos);
        vertices[2] = new Vector2(xPos, -yPos);
        vertices[3] = new Vector2(-xPos, -yPos);

        if (explodableTiles[0] > 0) {
            vertices[idVertice] = new Vector2(-xPos, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos, yPos + explodableTiles[0] * yPos * 2);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos, yPos + explodableTiles[0] * yPos * 2);
            idVertice++;

        }

        if (explodableTiles[1] > 0) {
            vertices[idVertice] = new Vector2(xPos, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos + explodableTiles[1] * xPos * 2, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos + explodableTiles[1] * xPos * 2, yPos);
            idVertice++;

        }

        if (explodableTiles[2] > 0) {
            vertices[idVertice] = new Vector2(xPos, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos, -yPos - explodableTiles[2] * yPos * 2);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos, -yPos - explodableTiles[2] * yPos * 2);
            idVertice++;
        }

        if (explodableTiles[3] > 0) {

            vertices[idVertice] = new Vector2(-xPos, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos - explodableTiles[3] * xPos * 2, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos - explodableTiles[3] * xPos * 2, yPos);
            idVertice++;

        }


        return vertices;
    }

    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    protected abstract void createAnimations(Bomber bomber);

    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        int xPos = (int) ((body.getPosition().x * Bombic.PPM / 50) + xAdd / 50);
        int yPos = (int) (body.getPosition().y * Bombic.PPM / 50 + yAdd / 50);


        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    protected void checkFreeTiles(int range) {

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell[] arrayCellsAux = new TiledMapTileLayer.Cell[range];
            boolean atLeastOne = false;

            for (int j = 1; j < range + 1; j++) {
                TiledMapTileLayer.Cell auxCell = getCell(j * xAddCell[i], j * yAddCell[i]);
                if (auxCell != null) {
                    if (auxCell.getTile().getId() == BLANK_TILE || auxCell.getTile().getId() == BLANK_BURNED_TILE) {
                        arrayCellsAux[j - 1] = getCell(j * xAddCell[i], j * yAddCell[i]);
                        atLeastOne = true;

                    }
                    if (auxCell.getTile().getId() == BLANK_TILE || auxCell.getTile().getId() == BLANK_BURNED_TILE || auxCell.getTile().getId() == BARREL_TILE) {
                        explodableTiles[i]++;
                        numVerticesBomb++;
                    }


                }
            }
            if (atLeastOne)
                freeCells[i] = arrayCellsAux;
            else
                freeCells[i] = null;
        }


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

        //UP, RIGHT, DOWN, LEFT animations
        int[][] idsAnimations = {{4, 5}, {2, 1}, {6, 5}, {0, 1}};

        for (int i = 0; i < freeCells.length; i++) {

            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++) {
                    if (j == freeCells[i].length - 1)
                        freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[idsAnimations[i][0]][visibleTileID]));
                    else
                        freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[idsAnimations[i][1]][visibleTileID]));

                }

        }

        getCell(0, 0).setTile(tileSetFlames.getTile(firstTileSetID + burningAnimationTiles[3][visibleTileID]));

    }

    protected void flashTiles() {

        for (int i = 0; i < 4; i++) {
            if (freeCells[i] != null) {
                for (int j = 0; j < freeCells[i].length; j++) {
                    freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + previewAnimationTiles[visibleTileID]));

                }

            }


        }
    }

    protected void resetFreeTiles() {
        for (int i = 0; i < 4; i++)
            if (freeCells[i] != null)
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
