package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class Slimer extends Enemy {

    private float stateTime, untouchableTime;

    private State currentState;
    private State previousState;

    private int lives;
    private boolean toRedefineBody, startUntouchable;

    protected TextureRegion standingAnim, rightAnim, leftAnim, upAnim, downAnim;

    public Slimer(Game game, float x, float y) {
        super(game, x, y);

        lives = 2;
        untouchableTime = 0;
        toRedefineBody = false;
        startUntouchable = false;

        createAnimations();

        stateTime = 0;
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(standingAnim);
        toDestroy = false;
        destroyed = false;
        currentState = previousState = State.STANDING;

        fixture.setUserData(this);

        speed = game.getGameSpeed() / 3;
        velocity = new Vector2(0, speed);

    }

    private void createAnimations() {
        createAnims();
        createDyingAnim();
    }

    private void redefineBody() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create player shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(19 / Constants.PPM);
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.BOMBER_BIT |
                Constants.FLAMES_BIT;
        fdef.shape = shape;
        setBounds(getX(), getY(), 45 / Constants.PPM, 45 / Constants.PPM);
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        toRedefineBody = false;
    }


    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("slimer_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createAnims() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("slimer_down"), 0, 0, 50, 50);
        rightAnim = new TextureRegion(atlasEnemies.findRegion("slimer_right"), 0, 0, 50, 50);
        leftAnim = new TextureRegion(atlasEnemies.findRegion("slimer_left"), 0, 0, 50, 50);
        upAnim = new TextureRegion(atlasEnemies.findRegion("slimer_up"), 0, 0, 50, 50);
        downAnim = new TextureRegion(atlasEnemies.findRegion("slimer_down"), 0, 0, 50, 50);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }


    @Override
    public void update(float dt) {

        if (untouchableTime != 0)
            if (startUntouchable && untouchableTime <= 4.5 / game.getGameSpeed()) {
                untouchableTime += dt;
            } else {
                startUntouchable = false;
                untouchableTime = 0;
                setUntouchableEnemy();

            }

        if (toRedefineBody) {
            redefineBody();
            setUntouchableEnemy();

        }
        if (lives == 2)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        setRegion(getFrame(dt * speed));
        if (!destroyed) {
            if (toDestroy) {
                velocity.set(0, 0);
                b2body.setLinearVelocity(velocity);
                if (stateTime >= 0.8f) {

                    world.destroyBody(b2body);
                    destroyed = true;
                }
            } else {
                setSpeed();
                b2body.setLinearVelocity(velocity);
            }
        }


    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {

            case RUNNING_LEFT:
                region = leftAnim;
                break;
            case RUNNING_RIGHT:
                region = rightAnim;
                break;
            case RUNNING_UP:
                region = upAnim;
                break;
            case RUNNING_DOWN:
                region = downAnim;
                break;
            case DYING:
                region = dyingAnim.getKeyFrame(stateTime, true);
                break;
            default:
            case STANDING:
                region = standingAnim;
                break;

        }

        stateTime = currentState == previousState ? stateTime + dt : 0;

        previousState = currentState;

        return region;
    }

    public State getState() {
        if (destroyed)
            return State.DEAD;
        else if (toDestroy)
            return State.DYING;
        else if (b2body.getLinearVelocity().x > 0)
            return State.RUNNING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return State.RUNNING_LEFT;
        else if (b2body.getLinearVelocity().y > 0)
            return State.RUNNING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.RUNNING_DOWN;
        else
            return State.STANDING;

    }

    private void setUntouchableEnemy() {
        if (startUntouchable) {
            Filter filter = new Filter();
            filter.maskBits = Constants.GROUND_BIT |
                    Constants.DESTROYABLE_OBJECT_BIT |
                    Constants.OBJECT_BIT |
                    Constants.BOMB_BIT |
                    Constants.BOMBER_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        } else {
            Filter filter = new Filter();
            filter.categoryBits = Constants.ENEMY_BIT;
            filter.maskBits = Constants.GROUND_BIT |
                    Constants.DESTROYABLE_OBJECT_BIT |
                    Constants.OBJECT_BIT |
                    Constants.BOMB_BIT |
                    Constants.BOMBER_BIT |
                    Constants.FLAMES_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);

        }
    }

    public void hitByFlame(float timeLeft) {
        if (lives > 1) {
            toRedefineBody = true;
            lives--;
            untouchableTime = timeLeft;
            startUntouchable = true;
        } else {
            lives--;
            toDestroy = true;
            Filter filter = new Filter();
            filter.maskBits = Constants.NOTHING_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        }
    }


}
