package com.lpoo.bombic.Sprites.TileObjects;

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
import com.lpoo.bombic.Sprites.Items.Bonus.DistantExplodeBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.FlameBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.KickingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.LBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.NBombBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SendingBonus;
import com.lpoo.bombic.Sprites.Items.Bonus.SpeedBonus;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Tools.Constants;

import java.util.HashMap;

/**
 * Interactive  Tile Object (barrels, boxes...)
 */
public class InteractiveTileObject {
    /**
     * Physics World
     */
    protected World world;
    /**
     * Map
     */
    protected TiledMap map;
    /**
     * Body bounds
     */
    protected Rectangle bounds;
    /**
     * Object body
     */
    protected Body body;
    /**
     * Game to spawn bonus
     */
    protected Game game;
    /**
     * MapObject
     */
    protected MapObject object;

    /**
     * Bonus placed in the object
     */
    private int bonus;
    /**
     * Body fixture
     */
    private Fixture fixture;

    private boolean exploded;

    private HashMap<Integer, Class<?>> bonusMap;

    /**
     * Constructor
     * @param game - to spawn bonus
     * @param object - object to create
     * @param bonus - bonus of the object
     */
    public InteractiveTileObject(Game game, MapObject object, int bonus) {
        this.object = object;
        this.game = game;
        this.world = game.getWorld();
        this.map = game.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.bonus = bonus;
        bonusMap = new HashMap<Integer, Class<?>>();
        createBonusMap();
        defineTileObject();
    }

    /**
     * Body creation
     */
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

    /**
     * Alters category bits
     * @param filterBit
     */
    protected void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    /**
     * Sets object to destroy and spawns bonus
     */
    public void explode() {
        exploded = true;
        setCategoryFilter(Constants.DESTROYED_BIT);
        if (bonus != 0)
            game.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y),
                    bonusMap.get(bonus)));

        game.setObjectsToDestroy(this);
    }

    /**
     * Destroys body
     */
    public void destroy() {
        world.destroyBody(body);
    }

    /**
     * Creates the bonusMap
      */
    private void createBonusMap(){
        bonusMap.put(Constants.BOMB_BONUS, BombBonus.class);
        bonusMap.put(Constants.FLAME_BONUS, FlameBonus.class);
        bonusMap.put(Constants.SPEED_BONUS, SpeedBonus.class);
        bonusMap.put(Constants.DEAD_BONUS, DeadBonus.class);
        bonusMap.put(Constants.DISTANT_EXPLODE, DistantExplodeBonus.class);
        bonusMap.put(Constants.KICKING, KickingBonus.class);
        bonusMap.put(Constants.MEGABOMB, LBombBonus.class);
        bonusMap.put(Constants.NAPALM, NBombBonus.class);
        bonusMap.put(Constants.SENDING, SendingBonus.class);
    }


}
