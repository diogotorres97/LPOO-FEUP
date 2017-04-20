package com.lpoo.bombic.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.TileObjects.Barrel;

/**
 * Created by Rui Quaresma on 18/04/2017.
 */

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create bushes/rocks bodies/fixtures
        //that map.getLayers().get(2) ----> the index comes frome the tiled app, counting from bottom to top
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Bombic.PPM, (rect.getY() + rect.getHeight() / 2) / Bombic.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Bombic.PPM, rect.getHeight() / 2 / Bombic.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create barrels bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new Barrel(screen, object);
        }
    }
}
