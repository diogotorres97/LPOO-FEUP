package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Tools.Constants;
import static com.lpoo.bombic.Bombic.gam;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Bomb extends Item {

    protected float stateTime;
    protected float burnAndPreviewStateTime;
    protected int visibleTileID;

    protected TextureAtlas atlasBombs;

    protected Animation<TextureRegion> tickingAnimation;
    protected TextureRegion cleanRegion;
    protected Player player;

    protected int[][] burningAnimationTiles;
    protected int[] nBombFlamesTiles;
    protected int[] previewAnimationTiles;
    protected int[] explodableTiles;
    protected int numVerticesBomb;

    protected int[] xAddCell; //used to determinate if there are any free cells around the bomb (up, right, down, left) in the X axis
    protected int[] yAddCell; //used to determinate if there are any free cells around the bomb (up, right, down, left) in the Y axis


    protected static TiledMapTileSet tileSetFlames;
    protected static TiledMapTileSet tileSetNBombFlames;
    protected static TiledMapTileSet tileSetMap;
    protected int firstTileSetID;
    protected int firstNBombFlamesSetID;
    protected TiledMapTileLayer.Cell[][] freeCells;

    protected Fixture fixture;

    protected boolean redefinedBomb;
    protected boolean kickBomb;

    protected boolean movingBomb;
    protected boolean redefinedKickableBomb;

    protected boolean onFire;

    protected Vector2 bombVelocity;

    protected int idBomber;

    public Bomb(float x, float y) {

        super(x, y);
    }

    public void createBomb() {
        atlasBombs = gam.manager.get("bombs.atlas");

        tileSetFlames = map.getTileSets().getTileSet("flames");
        firstTileSetID = Integer.parseInt(tileSetFlames.getProperties().get("firstID").toString()) - 1;
        tileSetMap = map.getTileSets().getTileSet(map.getProperties().get("main_tile_set").toString());
        tileSetNBombFlames = map.getTileSets().getTileSet("flamesNBomb");
        firstNBombFlamesSetID = Integer.parseInt(tileSetNBombFlames.getProperties().get("firstID").toString()) - 1;


        //each Array<Integer> contains the ids of the tiles that represent different directions : UP, RIGHT, DOWN, LEFT, MIDDLE, MIDDLE_UP_DOWN, MIDDLE_RIGHT_LEFT
        burningAnimationTiles = new int[7][3];
        previewAnimationTiles = new int[3];
        nBombFlamesTiles = new int[8];

        //Num of tiles that will explode in each direction : UP, RIGHT, DOWN, LEFT

        stateTime = 0;
        visible = true;
        toDestroy = false;
        destroyed = false;

        onFire = false;


        //UP             RIGHT             DOWN             LEFT
        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        visibleTileID = 0;
        defineItem();
        checkFreeTiles(player.getFlames());
        createAnimations();


        redefinedKickableBomb = true;
        redefinedBomb = false;
        kickBomb = false;
        movingBomb = false;

        idBomber = player.getId();


    }

    public float getStateTime() {
        return stateTime;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void explode() {
        stateTime = 3f / game.getGameSpeed();
    }

    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        //Create bomb shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Constants.PPM);
        fdef.filter.maskBits = Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.ENEMY_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);

    }

    protected void redefineKickableBomb() {
        Vector2 currentPosition = body.getPosition();

        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / Constants.PPM);
        fdef.filter.maskBits = Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.ENEMY_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);

        redefinedKickableBomb = true;


    }

    public Vector2 getBodyPositions() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    protected void redefineBomb() {
        redefinedBomb = true;
        Vector2 currentPosition = new Vector2(centerItem(body.getPosition().x) + getWidth() / 2, centerItem(body.getPosition().y) + getHeight() / 2);

        world.destroyBody(body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        setCategoryFilter(Constants.FLAMES_BIT);
        redefineBombShape();

    }

    protected void redefineBombShape() {
        Vector2[] vertices = createVertices();
        int numVertices = vertices.length;

        for (int i = 0; i < numVertices; i += 4) {

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
            aux_v[0] = vertices[i];
            aux_v[1] = vertices[i + 1];
            aux_v[2] = vertices[i + 2];
            aux_v[3] = vertices[i + 3];

            shape.set(aux_v);
            fdef.isSensor = true;
            fdef.shape = shape;


            fixture = body.createFixture(fdef);
            fixture.setUserData(this);


        }


    }

    protected abstract Vector2[] createVertices();

    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    protected void createAnimations() {
        for (int i = 0; i < 3; i++)
            previewAnimationTiles[i] = (i + 1) * 10 - 2;
    }

    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        int xPos = (int) ((body.getPosition().x) * Constants.PPM / 50 + xAdd / 50);
        int yPos = (int) ((body.getPosition().y) * Constants.PPM / 50 + yAdd / 50);
        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    protected abstract void checkFreeTiles(int range);

    protected boolean isFlameTile(int id) {
        int aux_id = firstTileSetID;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (id == aux_id)
                    return true;
                aux_id++;
            }
            aux_id += 3;
        }
        return false;
    }

    protected boolean isContinuosFlame(int id) {
        int aux_id = firstNBombFlamesSetID;
        for (int i = 0; i < 8; i++) {

            if (id == aux_id)
                return true;
            aux_id++;
        }
        return false;
    }

    protected boolean isTickingTile(int id) {
        int aux_id = firstTileSetID + 8;
        for (int i = 0; i < 3; i++) {
            if (id == aux_id)
                return true;
            aux_id += 10;
        }
        return false;
    }

    protected boolean isObjectTile(int id){
        for(int i = 0 ; i < Constants.OBJECTS_TILES.length; i++){
            if(id == Constants.OBJECTS_TILES[i])
                return true;
        }
        return false;

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

    protected abstract void fireUpTiles();

    protected void flashTiles() {

        for (int i = 0; i < 4; i++) {
            if (freeCells[i] != null) {
                for (int j = 0; j < freeCells[i].length; j++) {

                    if (freeCells[i][j] != null)
                        if (!isContinuosFlame(freeCells[i][j].getTile().getId()) && !isFlameTile(freeCells[i][j].getTile().getId()) && freeCells[i][j].getTile().getId() != Constants.BARREL_TILE) {
                            freeCells[i][j].setTile(tileSetFlames.getTile(firstTileSetID + previewAnimationTiles[visibleTileID]));
                        }
                }
            }
        }

        getCell(0, 0).setTile(tileSetFlames.getTile(firstTileSetID + previewAnimationTiles[visibleTileID]));
    }


    protected void resetFreeTiles() {
        for (int i = 0; i < 4; i++)
            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++)
                    if (freeCells[i][j] != null) {
                        freeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));
                    }
        getCell(0, 0).setTile(tileSetMap.getTile(Constants.BLANK_TILE));

    }

    protected void clearTickingTiles() {
        for (int i = 0; i < 4; i++)
            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++)
                    if (freeCells[i][j] != null)
                        if (isTickingTile(freeCells[i][j].getTile().getId()))
                            freeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));
        getCell(0, 0).setTile(tileSetMap.getTile(Constants.BLANK_TILE));
    }

    public void kick(int playerOrientation) {

        redefinedKickableBomb = false;
        kickBomb = true;
        setBombVelocity(playerOrientation);

    }

    public boolean isMovingBomb() {
        return movingBomb;
    }

    protected void setBombVelocity(int playerOrientation) {
        switch (playerOrientation) {
            case 0:
                bombVelocity = new Vector2(0, game.getGameSpeed() / 0.7f);
                break;
            case 1:
                bombVelocity = new Vector2(game.getGameSpeed() / 0.7f, 0);
                break;
            case 2:
                bombVelocity = new Vector2(0, -game.getGameSpeed() / 0.7f);
                break;
            case 3:
                bombVelocity = new Vector2(-game.getGameSpeed() / 0.7f, 0);
                break;
            default:
                break;

        }
    }

    protected TextureRegion getFrame() {
        TextureRegion region;


        region = tickingAnimation.getKeyFrame(stateTime * game.getGameSpeed(), true);

        //return our final adjusted frame
        return region;
    }

    @Override
    public void stop() {
        super.stop();
        movingBomb = false;
    }

    @Override
    public void destroy() {
        super.destroy();
        Filter filter = new Filter();
        filter.maskBits = Constants.NOTHING_BIT;


    }

}
