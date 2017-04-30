package com.lpoo.bombic.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Item extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    protected TiledMap map;

    public Item(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;

        float xPos = centerBombX(x);
        float yPos = centerBombY(y);

        this.map = screen.getMap();

        setPosition(xPos +0.25f, yPos + 0.25f);
        setBounds(getX(), getY(), 50 / Bombic.PPM, 50 / Bombic.PPM);
        defineItem();
        /*float newX = 0;
        float newY = 0;
        Gdx.app.log("BOMBX", "" + x);
        Gdx.app.log("BOMBY", "" + y);
        //TODO: 0.75, 1.25, 1.75 meter nestes valores

        float temp =x / 0.5f;
        if(x>1)
           temp= (int)temp * 0.75f-0.25f;
        else
            temp= (int)temp * 0.75f;

        //float tmp2 =(int)( x % 0.5f)+0.5f;
        //tmp2+=0.25f;


        setBounds(temp, y, 50 / Bombic.PPM, 50 / Bombic.PPM);*/

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
        int xPos = (int) (x * Bombic.PPM / 50);

        return xPos * 50 / Bombic.PPM;
    }

    private float centerBombY(float y){
        int yPos = (int) (y * Bombic.PPM / 50);

        return yPos * 50 / Bombic.PPM;
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
