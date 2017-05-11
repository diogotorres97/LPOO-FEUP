package com.lpoo.bombic.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Item extends Sprite{
    protected Game game;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    protected TiledMap map;

    public Item(Game game, float x, float y){
        this.game = game;
        this.world = game.getWorld();
        toDestroy = false;
        destroyed = false;

        float xPos = centerBombX(x);
        float yPos = centerBombY(y);

        this.map = game.getMap();

        setPosition(xPos +0.25f, yPos + 0.25f);
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
        defineItem();
    }

    public abstract void defineItem();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    private float centerBombX(float x){
        int xPos = (int) (x * Constants.PPM / 50);

        return xPos * 50 / Constants.PPM;
    }

    private float centerBombY(float y){
        int yPos = (int) (y * Constants.PPM / 50);

        return yPos * 50 / Constants.PPM;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

    public void destroy(){
        toDestroy = true;
    }

    public void stop(boolean x, boolean y){
        if(x)
            velocity.x = 0;
        if(y)
            velocity.y = 0;
    }

}
