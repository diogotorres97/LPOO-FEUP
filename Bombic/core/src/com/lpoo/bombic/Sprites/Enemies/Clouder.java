package com.lpoo.bombic.Sprites.Enemies;

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

import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Created by Rui Quaresma on 20/05/2017.
 */

public class Clouder extends Enemy {

    private State currentState;
    private State previousState;

    protected TextureRegion standingAnim, rightAnim, leftAnim, upAnim, downAnim;


    public Clouder(Game game, float x, float y) {
        super(game, x, y);

        createAnimations();

        stateTime = 0;
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(standingAnim);
        toDestroy = false;
        destroyed = false;
        currentState = previousState = State.STANDING;

        fixture.setUserData(this);

        lastSquareX = 0;
        lastSquareY = 0;

        speed = GAMESPEED * 1.1f;
        velocity = new Vector2(0, speed);

    }

    private void createAnimations() {
        createAnims();
        createDyingAnim();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("clouder_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createAnims() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("clouder_down"), 0, 0, 50, 50);
        rightAnim = new TextureRegion(atlasEnemies.findRegion("clouder_right"), 0, 0, 50, 50);
        leftAnim = new TextureRegion(atlasEnemies.findRegion("clouder_left"), 0, 0, 50, 50);
        upAnim = new TextureRegion(atlasEnemies.findRegion("clouder_up"), 0, 0, 50, 50);
        downAnim = new TextureRegion(atlasEnemies.findRegion("clouder_down"), 0, 0, 50, 50);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }


    @Override
    public void update(float dt) {

        if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt * speed));
            if (toDestroy) {
                velocity.set(0, 0);
                b2body.setLinearVelocity(velocity);

                if (stateTime >= 0.5f) {
                    world.destroyBody(b2body);
                    destroyed = true;

                }
            } else {
                if (b2body.isActive()) {
                    strategy.move(this);
                    b2body.setLinearVelocity(velocity);
                }
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

    public void hitObject(){
    }

    @Override
    public void hitByFlame() {
        toDestroy = true;
        Filter filter = new Filter();
        filter.maskBits = Constants.NOTHING_BIT;
        b2body.getFixtureList().get(0).setFilterData(filter);
    }
}