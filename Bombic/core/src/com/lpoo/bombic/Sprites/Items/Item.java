package com.lpoo.bombic.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Item extends Sprite {
    protected Game game;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    protected boolean visible;

    protected TiledMap map;

    public Item(float x, float y) {

        setPosition(x + 0.25f, y + 0.25f);
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
    }

    public void setGame(Game game) {
        this.game = game;
        this.world = game.getWorld();
        this.map = game.getMap();
    }

    public void setNewPosition(float x, float y) {
        float xPos = centerItem(x);
        float yPos = centerItem(y);
        this.setPosition(xPos + 0.25f, yPos + 0.25f);
        this.setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
    }

    public abstract void defineItem();

    public void update(float dt) {
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;

        }
    }

    public void draw(Batch batch) {
        if (!destroyed && visible)
            super.draw(batch);
    }

    protected float centerItem(float coord) {
        int ret = (int) (coord * Constants.PPM / 50);

        return ret * 50 / Constants.PPM;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void destroy() {
        toDestroy = true;
    }

    public void stop() {

        body.setLinearVelocity(0, 0);
    }

}
