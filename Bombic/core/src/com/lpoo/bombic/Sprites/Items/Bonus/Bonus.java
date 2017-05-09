package com.lpoo.bombic.Sprites.Items.Bonus;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Players.Bomber;
import com.lpoo.bombic.Sprites.Items.Item;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Bonus extends Item {

    Fixture fixture;
    public Bonus(Game game, float x, float y) {

        super(game, x, y);

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
        shape.setRadius(23 / Bombic.PPM);
        fdef.filter.categoryBits = Bombic.BONUS_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }

    @Override
    public void destroy() {
        super.destroy();
        Filter filter = new Filter();
        filter.maskBits = Bombic.NOTHING_BIT;
        body.getFixtureList().get(0).setFilterData(filter);
    }

    public abstract void apply(Bomber bomber);
}
