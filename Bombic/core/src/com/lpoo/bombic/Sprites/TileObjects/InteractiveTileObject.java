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
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Items.Bonus.BombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.ItemDef;

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

    private float stateTimer;

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
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Bombic.PPM, (bounds.getY() + bounds.getHeight() / 2) / Bombic.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Bombic.PPM, bounds.getHeight() / 2 / Bombic.PPM);
        fdef.filter.categoryBits = Bombic.DESTROYABLE_OBJECT_BIT;
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
        setCategoryFilter(Bombic.DESTROYED_BIT);
        if (bonus != 0)
            game.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    getTypeBonus()));
    }

    private Class<?> getTypeBonus() {
        switch (bonus) {
            case Bombic.BOMB_BONUS:
                Gdx.app.log("BOMB", "BOMB");
                return BombBonus.class;
            case Bombic.FLAME_BONUS:
                Gdx.app.log("Flame", "Flame");
                return FlameBonus.class;
            case Bombic.SPEED_BONUS:
                return SpeedBonus.class;
            default:
                return null;
        }
    }


}
