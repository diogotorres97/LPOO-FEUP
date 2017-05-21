package com.lpoo.bombic.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.DeadBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 20/04/2017.
 */

public class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Game game;
    protected MapObject object;

    private int bonus;

    protected Fixture fixture;

    public InteractiveTileObject(Game game, MapObject object, int bonus) {
        this.object = object;
        this.game = game;
        this.world = game.getWorld();
        this.map = game.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.bonus = bonus;

        defineTileObject();
    }

    private void defineTileObject() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM, (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Constants.PPM, bounds.getHeight() / 2 / Constants.PPM);
        fdef.filter.categoryBits = Constants.DESTROYABLE_OBJECT_BIT;
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    public void explode() {
        setCategoryFilter(Constants.DESTROYED_BIT);
        if (bonus != 0)
            game.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    getTypeBonus()));
    }

    private Class<?> getTypeBonus() {
        switch (bonus) {
            case Constants.BOMB_BONUS:
                return BombBonus.class;
            case Constants.FLAME_BONUS:
                return FlameBonus.class;
            case Constants.SPEED_BONUS:
                return SpeedBonus.class;
            case Constants.DEAD_BONUS:
                return DeadBonus.class;
            default:
                return null;
        }
    }


}
