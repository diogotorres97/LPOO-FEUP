package com.lpoo.bombic.Sprites.Items.Bonus;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lpoo.bombic.Sprites.Items.Bonus.BonusStrategies.BonusStrategy;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Bonus creator
 */

public abstract class Bonus extends Item {

    Fixture fixture;
    protected BonusStrategy strategy;
    protected TextureAtlas atlasBonus;
    protected boolean active;
    protected int id;

    /**
     * Constructor
     * @param x
     * @param y
     */
    public Bonus(float x, float y) {
        super(x, y);
    }

    /**
     * Create bonus, define body
     */
    public void createBonus() {
        defineItem();
        atlasBonus = new TextureAtlas("bonus.atlas");
        toDestroy = false;
        destroyed = false;
        active = false;
        visible = true;
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
        shape.setRadius(23 / Constants.PPM);
        fdef.filter.categoryBits = Constants.BONUS_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (visible) {
            Filter filter = new Filter();
            filter.maskBits = Constants.NOTHING_BIT;
            body.getFixtureList().get(0).setFilterData(filter);
        }
    }

    public BonusStrategy getStrategy(){
        return strategy;
    }

    public int getId() {
        return id;
    }

    /**
     * Sets its visibility
     */
    protected void setInvisible() {
        visible = false;
        Filter filter = new Filter();
        filter.maskBits = Constants.NOTHING_BIT;
        body.getFixtureList().get(0).setFilterData(filter);

    }
    /**
     * Apply the bonus to the player
     * @param player
     */
    public abstract void apply(Player player);
}
