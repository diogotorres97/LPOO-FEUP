package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class GreyBall extends Enemy {

    private float stateTime;

    private State currentState;
    private State previousState;

    public GreyBall(Game game, float x, float y) {
        super(game, x, y);

        createAnimations();

        stateTime = 0;
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(standingAnim);
        toDestroy = false;
        destroyed = false;
        currentState = previousState = State.STANDING;

        fixture.setUserData(this);

        speed = game.getGameSpeed() / 2;
        velocity = new Vector2(0 , speed);

    }

    private void createAnimations(){
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    private void createRunDownAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunUpAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunRightAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunLeftAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createDyingAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_die"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createStandingAnim(){
        standingAnim = new TextureRegion(atlasEnemies.findRegion("greyball_down"), 0, 0, 50, 50);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }


    @Override
    public void update(float dt) {

        /*Random rand = new Random();
            move(rand.nextInt(4));*/


        if(!destroyed) {
            if (toDestroy) {
                velocity.set(0, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(getFrame(dt * speed));
                if (stateTime >= 0.8f) {
                    world.destroyBody(b2body);
                    destroyed = true;

                }
            } else {
                setSpeed();
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(getFrame(dt * speed));
            }

        }

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {

            case RUNNING_LEFT:
                region = runLeftAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_RIGHT:
                region = runRightAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_UP:
                region = runUpAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_DOWN:
                region = runDownAnim.getKeyFrame(stateTime, true);
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

    public void hitByFlame() {
        toDestroy = true;
        Filter filter = new Filter();
        filter.maskBits = Constants.NOTHING_BIT;
        b2body.getFixtureList().get(0).setFilterData(filter);
    }



}
