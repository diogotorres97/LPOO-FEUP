package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class NBomb extends Bomb {
    private int flamesVisibleID;
    public NBomb(float x, float y) {
        super(x, y);
    }

    public void createBomb() {
        super.createBomb();
        fixture.setUserData(this);
        flamesVisibleID = 0;
        setCategoryFilter(Constants.BOMB_BIT);
        cleanRegion = new TextureRegion(atlasBombs.findRegion("NBomb"), 16 * 50, 0, 50, 50);
    }


    @Override
    public void createAnimations() {

        super.createAnimations();

        //Creation of burning tiles animations
        for (int i = 0; i < nBombFlamesTiles.length; i++)
            nBombFlamesTiles[i] = firstNBombFlamesSetID + i +1;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Creating ticking animation
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

    private void setFlamesVisibleID(float dt){
        if (burnAndPreviewStateTime >= 0.1f) {
            burnAndPreviewStateTime = 0;
            if(flamesVisibleID == 7)
                flamesVisibleID = 0;
            else
                flamesVisibleID++;
        }else
            burnAndPreviewStateTime += dt;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if ((stateTime >= 3f / game.getGameSpeed() && stateTime <= 12 / game.getGameSpeed()) || onFire) {
            onFire = true;
            setRegion(cleanRegion);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            if (!redefinedBomb) {
                clearTickingTiles();
                checkFreeTiles(player.getFlames());
                redefineBomb();
            }
            setFlamesVisibleID(dt * game.getGameSpeed());
            fireUpTiles();
        } else if (stateTime < 3f / game.getGameSpeed()) {
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
            setVisibleTileID(dt * game.getGameSpeed() * Constants.TICKING_SPEED);

            if (player.isExplodeBombs())
                stateTime = 3f / game.getGameSpeed();
        } else {
            if (!toDestroy) {
                resetFreeTiles();
                onFire = false;
                destroy();
            }
        }

        stateTime += dt;

        if (stateTime >= 12f / game.getGameSpeed())
            onFire = false;

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
                    if (auxCell.getTile().getId() == Constants.BLANK_TILE ||
                            isFlameTile(auxCell.getTile().getId()) || isTickingTile(auxCell.getTile().getId()) || auxCell.getTile().getId() == Constants.BARREL_TILE) {
                        arrayCellsAux[j - 1] = auxCell;
                        atLeastOne = true;
                        explodableTiles[i]++;
                    }
                    if (auxCell.getTile().getId() == Constants.BARREL_TILE || auxCell.getTile().getId() == Constants.ROCK_TILE) {
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