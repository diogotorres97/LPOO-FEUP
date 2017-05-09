package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.ItemDef;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public abstract class Bomber extends Sprite {
    public enum State {RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, STANDING_RIGHT, STANDING_LEFT, STANDING_UP, STANDING_DOWN, DYING, DEAD}

    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public Game game;
    private Array<TextureRegion> bomberStand;
    private TextureRegion cleanRegion;
    private Animation<TextureRegion> bomberRunUp;
    private Animation<TextureRegion> bomberRunDown;
    private Animation<TextureRegion> bomberRunLeft;
    private Animation<TextureRegion> bomberRunRight;
    private Animation<TextureRegion> bomberDying;
    private float stateTimer;

    private int bonus;
    private int nFlames;
    private int nBombs, nPlacedBombs;
    protected float speedIncrease;
    private boolean bomberIsDead;
    private boolean bomberToDie;

    private int id;

    protected Vector2 velocity;
    protected Vector2 pos;


    public Bomber(Game game, int id, Vector2 pos) {
        this.id = id;
        this.world = game.getWorld();
        this.game = game;
        this.pos = pos;
        currentState = State.STANDING_DOWN;
        previousState = State.STANDING_DOWN;
        stateTimer = 0;

        speedIncrease = 0;
        nFlames = nBombs = 1;
        nPlacedBombs = 0;
        bomberToDie = bomberIsDead = false;

        bonus = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Creating running right animation
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_right"), i * 50, 0, 50, 50));
        bomberRunRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running left animation
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_left"), i * 50, 0, 50, 50));
        bomberRunLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running up/down animation
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_up"), i * 50, 0, 50, 50));
        bomberRunUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running up/down animation
        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_down"), i * 50, 0, 50, 50));
        bomberRunDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating dying animation
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_dying"), i * 50, 0, 50, 50));
        bomberDying = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        bomberStand = new Array<TextureRegion>();

        bomberStand.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_down"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_up"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_left"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_right"), 0, 0, 50, 50));

        cleanRegion = new TextureRegion(game.getAtlasBomber().findRegion("bomber" + (getId() - 1) + "_down"), 0, 300, 50, 50);

        defineBomber();

        setBounds(0, 0, 50 / Bombic.PPM, 50 / Bombic.PPM);
        setRegion(bomberStand.get(0));
    }

    public int getId() {
        return id;
    }

    public int getBonus() {
        return bonus;
    }

    public void defineBomber() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x / Bombic.PPM, pos.y / Bombic.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(23 / Bombic.PPM);
        fdef.filter.categoryBits = Bombic.BOMBER_BIT;
        fdef.filter.maskBits = Bombic.GROUND_BIT |
                Bombic.DESTROYABLE_OBJECT_BIT |
                Bombic.OBJECT_BIT |
                Bombic.CLASSIC_BOMB_BIT |
                Bombic.FLAMES_BIT |
                Bombic.BONUS_BIT |
                Bombic.ENEMY_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);


        velocity = new Vector2(0, 0);
    }

    public abstract void move(int dir);

    public abstract void stop(int dir);





    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt * (Bombic.GAME_SPEED + speedIncrease)));
        if (bomberToDie && !bomberIsDead) {
            if (stateTimer >= 0.8f) {
                bomberIsDead = true;
                setRegion(cleanRegion);
                world.destroyBody(b2body);
            }
        }
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {

            case RUNNING_LEFT:
                region = bomberRunLeft.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_RIGHT:
                region = bomberRunRight.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_UP:
                region = bomberRunUp.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_DOWN:
                region = bomberRunDown.getKeyFrame(stateTimer, true);
                break;
            case DYING:
                region = bomberDying.getKeyFrame(stateTimer, true);
                break;
            case STANDING_UP:
                region = bomberStand.get(1);
                break;
            case STANDING_LEFT:
                region = bomberStand.get(2);
                break;
            case STANDING_RIGHT:
                region = bomberStand.get(3);
                break;
            default:
            case STANDING_DOWN:
                region = bomberStand.get(0);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        previousState = currentState;

        return region;
    }

    public State getState() {
        if (bomberIsDead)
            return State.DEAD;
        else if (bomberToDie)
            return State.DYING;
        else if (b2body.getLinearVelocity().x > 0)
            return State.RUNNING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return State.RUNNING_LEFT;
        else if (b2body.getLinearVelocity().y > 0)
            return State.RUNNING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.RUNNING_DOWN;

        switch (previousState) {
            case RUNNING_UP:
                return State.STANDING_UP;
            case RUNNING_LEFT:
                return State.STANDING_LEFT;
            case RUNNING_RIGHT:
                return State.STANDING_RIGHT;
            case RUNNING_DOWN:
                return State.STANDING_DOWN;
            default:
                return previousState;
        }
    }

    public int getFlames() {
        return nFlames;
    }

    public int getBombs() {
        return nBombs;
    }

    public void setFlames(int nFlames) {
        this.nFlames += nFlames;
    }

    public void setBombs(int nBombs) {
        this.nBombs += nBombs;
    }

    public void setPlacedBombs(int nPlacedBombs) {
        this.nPlacedBombs += nPlacedBombs;
    }

    public int getPlacedBombs() {
        return nPlacedBombs;
    }

    public float getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(float speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public void placeBomb() {

        if (getPlacedBombs() < getBombs()) {
            game.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x, b2body.getPosition().y),
                    ClassicBomb.class));
            setPlacedBombs(1);
        }

    }

    public boolean isDead() {
        return bomberIsDead;
    }

    public boolean isDying() {
        return bomberToDie;
    }

    public void die() {
        if (!isDead()) {
            bomberToDie = true;
            Filter filter = new Filter();
            filter.maskBits = Bombic.NOTHING_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        }

    }

    public float getStateTimer() {
        return stateTimer;
    }

}
