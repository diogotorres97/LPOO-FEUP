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
import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Bomb
 */
public abstract class Bomb extends Item {

    protected float stateTime;
    protected float burnAndPreviewStateTime;
    protected int visibleTileID, flamesVisibleID;
    protected boolean nFlames;

    protected TextureAtlas atlasBombs;

    protected Animation<TextureRegion> tickingAnimation;
    protected TextureRegion cleanRegion;
    protected Player player;

    protected int[][] burningAnimationTiles;
    protected int[] nBombFlamesTiles;
    protected int[] previewAnimationTiles;
    protected int[] explodableTiles;
    protected int numVerticesBomb;

    protected int[] xAddCell;
    protected int[] yAddCell;


    protected static TiledMapTileSet tileSetFlames;
    protected static TiledMapTileSet tileSetNBombFlames;
    protected static TiledMapTileSet tileSetMap;
    protected TiledMapTileLayer.Cell[][] freeCells;

    protected Fixture fixture;

    protected boolean redefinedBomb;
    protected boolean kickBomb;

    protected boolean movingBomb;
    protected boolean redefinedKickableBomb;

    protected boolean onFire;

    protected Vector2 bombVelocity;

    protected int idBomber;

    protected float endExplosionTime;

    /**
     * Bomb constructor
     *
     * @param x
     * @param y
     */
    public Bomb(float x, float y) {

        super(x, y);
    }

    /**
     * Create the bomb
     */
    public void createBomb() {
        atlasBombs = new TextureAtlas("bombs.atlas");

        tileSetFlames = map.getTileSets().getTileSet("flames");
        tileSetMap = map.getTileSets().getTileSet(map.getProperties().get("main_tile_set").toString());
        tileSetNBombFlames = map.getTileSets().getTileSet("flamesNBomb");
        initiateVariables();

        defineItem();
        checkFreeTiles(player.getFlames());
        createAnimations();

        redefinedKickableBomb = true;
        redefinedBomb = false;
        kickBomb = false;
        movingBomb = false;

        idBomber = player.getId();
    }

    /**
     * Initialize some variables
     */
    private void initiateVariables() {
        burningAnimationTiles = new int[7][3];
        previewAnimationTiles = new int[3];
        nBombFlamesTiles = new int[8];

        stateTime = 0;
        visible = true;
        toDestroy = false;
        destroyed = false;

        onFire = false;

        xAddCell = new int[]{0, 50, 0, -50};
        yAddCell = new int[]{50, 0, -50, 0};
        visibleTileID = 0;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void explode() {
        stateTime = 3f / GAMESPEED;
    }

    /**
     * Define body
     */
    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
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

    }

    /**
     * Redefine body when it is kickable
     */
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

    /**
     * Redefine bomb to the exploding one
     */
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

    /**
     * Redefines bomb shape to embrace flames
     */
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

    public boolean isOnFire() {
        return onFire;
    }

    /**
     * Update the bomb
     *
     * @param dt
     */
    @Override
    public void update(float dt) {
        super.update(dt);
        if ((stateTime >= 3f / GAMESPEED && stateTime <= endExplosionTime / GAMESPEED) || onFire) {
            onFire = true;
            setRegion(cleanRegion);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            if (!redefinedBomb) {
                clearTickingTiles();
                checkFreeTiles(player.getFlames());
                redefineBomb();
            }
            if (nFlames)
                setFlamesVisibleID(dt * GAMESPEED);
            else
                setVisibleTileID(dt * GAMESPEED * Constants.TICKING_SPEED);
            fireUpTiles();
        } else if (stateTime < 3f / GAMESPEED) {
            if (!redefinedKickableBomb) {
                redefineKickableBomb();
                fixture.setUserData(this);
                setCategoryFilter(Constants.BOMB_BIT);

            }
            if (kickBomb) {
                body.setLinearVelocity(bombVelocity);
                kickBomb = false;
                movingBomb = true;
            }

            if (movingBomb) {
                clearTickingTiles();
                checkFreeTiles(player.getFlames());

            }

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(getFrame());
            flashTiles();
            setVisibleTileID(dt * GAMESPEED * Constants.TICKING_SPEED);

            if (player.isExplodeBombs())
                stateTime = 3f / GAMESPEED;
        } else {
            if (!toDestroy) {
                resetFreeTiles();
                onFire = false;
                destroy();
            }
        }

        stateTime += dt;

        if (stateTime >= endExplosionTime / GAMESPEED)
            onFire = false;

    }

    /**
     * Create flames shape vertices
     *
     * @return
     */
    protected Vector2[] createVertices() {
        int idVertice = 4;
        float xPos = getWidth() / 2 - 0.02f;
        float yPos = getHeight() / 2 - 0.02f;
        Vector2[] vertices = new Vector2[4 + 4 * numVerticesBomb];

        vertices[0] = new Vector2(-xPos + 0.02f, yPos - 0.02f);
        vertices[1] = new Vector2(xPos - 0.02f, yPos - 0.02f);
        vertices[2] = new Vector2(xPos - 0.02f, -yPos + 0.02f);
        vertices[3] = new Vector2(-xPos + 0.02f, -yPos + 0.02f);

        if (explodableTiles[0] > 0) {
            vertices[idVertice] = new Vector2(-xPos + 0.02f, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos - 0.02f, yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos - 0.02f, yPos + explodableTiles[0] * getHeight() - 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos + 0.02f, yPos + explodableTiles[0] * getHeight() - 0.02f);
            idVertice++;

        }

        if (explodableTiles[1] > 0) {
            vertices[idVertice] = new Vector2(xPos, yPos - 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos, -yPos + 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos + explodableTiles[1] * getWidth() - 0.02f, -yPos + 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos + explodableTiles[1] * getWidth() - 0.02f, yPos - 0.02f);
            idVertice++;

        }

        if (explodableTiles[2] > 0) {
            vertices[idVertice] = new Vector2(xPos - 0.02f, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos + 0.02f, -yPos);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos + 0.02f, -yPos - explodableTiles[2] * getHeight() + 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(xPos - 0.02f, -yPos - explodableTiles[2] * getHeight() + 0.02f);
            idVertice++;
        }

        if (explodableTiles[3] > 0) {

            vertices[idVertice] = new Vector2(-xPos, yPos - 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos, -yPos + 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos - explodableTiles[3] * getWidth() + 0.02f, -yPos + 0.02f);
            idVertice++;
            vertices[idVertice] = new Vector2(-xPos - explodableTiles[3] * getWidth() + 0.02f, yPos - 0.02f);
            idVertice++;

        }


        return vertices;
    }

    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    /**
     * Create animatios
     */
    protected void createAnimations() {
        for (int i = 0; i < 3; i++)
            previewAnimationTiles[i] = (i + 1) * 10 - 2;
    }

    /**
     * Get a cell
     *
     * @param xAdd
     * @param yAdd
     * @return
     */
    protected TiledMapTileLayer.Cell getCell(int xAdd, int yAdd) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        int xPos = (int) ((body.getPosition().x) * Constants.PPM / 50 + xAdd / 50);
        int yPos = (int) ((body.getPosition().y) * Constants.PPM / 50 + yAdd / 50);
        return ((xPos >= 0 && yPos >= 0) ? layer.getCell(xPos, yPos) : null);
    }

    /**
     * Check the free tiles within a certain range
     *
     * @param range
     */
    protected void checkFreeTiles(int range) {
        explodableTiles = new int[4];
        numVerticesBomb = 0;
        freeCells = new TiledMapTileLayer.Cell[4][player.getFlames()];

        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer.Cell[] arrayCellsAux = new TiledMapTileLayer.Cell[range];
            boolean atLeastOne = false;
            boolean noMore = false;
            for (int j = 1; j < range + 1; j++) {
                TiledMapTileLayer.Cell auxCell = getCell(j * xAddCell[i], j * yAddCell[i]);

                if ((auxCell != null) && !noMore) {
                    if (!isObjectTile(auxCell.getTile().getId())) {
                        arrayCellsAux[j - 1] = auxCell;
                        atLeastOne = true;
                        explodableTiles[i]++;
                    }
                    if (auxCell.getTile().getId() == Constants.BARREL_TILE || isObjectTile(auxCell.getTile().getId())) {
                        noMore = true;
                    }
                }
            }
            if (explodableTiles[i] != 0)
                numVerticesBomb++;
            if (atLeastOne)
                freeCells[i] = arrayCellsAux;
            else
                freeCells[i] = null;
        }
    }

    /**
     * Whether certain tile is a flame
     *
     * @param id
     * @return
     */
    protected boolean isFlameTile(int id) {
        int aux_id = Constants.FIRST_FLAME_TILE;
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

    /**
     * Whether certain tile is a continuous flame
     *
     * @param id
     * @return
     */
    protected boolean isContinuosFlame(int id) {

        int aux_id = Constants.FIRST_CONTINUOS_FLAME_TILE;
        for (int i = 0; i < 8; i++) {
            if (id == aux_id)
                return true;
            aux_id++;
        }
        return false;
    }

    /**
     * Whether certain tile is a ticking tile
     *
     * @param id
     * @return
     */
    protected boolean isTickingTile(int id) {
        int aux_id = Constants.FIRST_FLAME_TILE + 8;
        for (int i = 0; i < 3; i++) {
            if (id == aux_id)
                return true;
            aux_id += 10;
        }
        return false;
    }

    /**
     * Whether certain tile is an object
     *
     * @param id
     * @return
     */
    protected boolean isObjectTile(int id) {
        for (int i = 0; i < Constants.OBJECTS_TILES.length; i++) {
            if (id == Constants.OBJECTS_TILES[i])
                return true;
        }
        return false;

    }

    /**
     * Set the visible tile id
     *
     * @param dt
     */
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

    private void setFlamesVisibleID(float dt) {
        if (burnAndPreviewStateTime >= 0.1f) {
            burnAndPreviewStateTime = 0;
            if (flamesVisibleID == 7)
                flamesVisibleID = 0;
            else
                flamesVisibleID++;
        } else
            burnAndPreviewStateTime += dt;
    }

    protected abstract void fireUpTiles();

    /**
     * Set tiles to the ticking animation
     */
    protected void flashTiles() {

        for (int i = 0; i < 4; i++) {
            if (freeCells[i] != null) {
                for (int j = 0; j < freeCells[i].length; j++) {

                    if (freeCells[i][j] != null)
                        if (!isContinuosFlame(freeCells[i][j].getTile().getId()) && !isFlameTile(freeCells[i][j].getTile().getId()) && freeCells[i][j].getTile().getId() != Constants.BARREL_TILE) {
                            freeCells[i][j].setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + previewAnimationTiles[visibleTileID]));
                        }
                }
            }
        }

        getCell(0, 0).setTile(tileSetFlames.getTile(Constants.FIRST_FLAME_TILE + previewAnimationTiles[visibleTileID]));
    }

    /**
     * Reset tiles to blank ones
     */
    protected void resetFreeTiles() {
        for (int i = 0; i < 4; i++)
            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++)
                    if (freeCells[i][j] != null) {
                        freeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));
                    }
        getCell(0, 0).setTile(tileSetMap.getTile(Constants.BLANK_TILE));

    }

    /**
     * Clears ticking tiles to blank
     */
    protected void clearTickingTiles() {
        for (int i = 0; i < 4; i++)
            if (freeCells[i] != null)
                for (int j = 0; j < freeCells[i].length; j++)
                    if (freeCells[i][j] != null)
                        if (isTickingTile(freeCells[i][j].getTile().getId()))
                            freeCells[i][j].setTile(tileSetMap.getTile(Constants.BLANK_TILE));
        getCell(0, 0).setTile(tileSetMap.getTile(Constants.BLANK_TILE));
    }

    /**
     * Kick
     *
     * @param playerOrientation
     */
    public void kick(int playerOrientation) {

        redefinedKickableBomb = false;
        kickBomb = true;
        setBombVelocity(playerOrientation);

    }

    public Body getBody(){
        return body;
    }

    public boolean isMovingBomb() {
        return movingBomb;
    }

    protected void setBombVelocity(int playerOrientation) {
        switch (playerOrientation) {
            case 0:
                bombVelocity = new Vector2(0, GAMESPEED / 0.7f);
                break;
            case 1:
                bombVelocity = new Vector2(GAMESPEED / 0.7f, 0);
                break;
            case 2:
                bombVelocity = new Vector2(0, -GAMESPEED / 0.7f);
                break;
            case 3:
                bombVelocity = new Vector2(-GAMESPEED / 0.7f, 0);
                break;
            default:
                break;

        }
    }

    protected TextureRegion getFrame() {
        TextureRegion region;

        region = tickingAnimation.getKeyFrame(stateTime * GAMESPEED, true);

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
