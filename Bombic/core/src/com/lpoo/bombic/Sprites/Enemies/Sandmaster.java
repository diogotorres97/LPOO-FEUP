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
 * Creates a sandmaster
 */

public class Sandmaster extends Enemy {

    protected TextureRegion standingAnim, rightAnim, leftAnim, upAnim, downAnim;

    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public Sandmaster(Game game, float x, float y) {
        super(game, x, y);

        lives = 3;
        toRedefineBody = false;
        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 3f;
        velocity = new Vector2(0, speed);
    }

    private void reduceSize(){
        setBounds(getX(), getY(), 40 / Constants.PPM, 40 / Constants.PPM);
    }

    protected void createAnimations() {
        createAnims();
        createDyingAnim();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("sandmaster_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createAnims() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("sandmaster_down"), 0, 0, 50, 50);
        rightAnim = new TextureRegion(atlasEnemies.findRegion("sandmaster_right"), 0, 0, 50, 50);
        leftAnim = new TextureRegion(atlasEnemies.findRegion("sandmaster_left"), 0, 0, 50, 50);
        upAnim = new TextureRegion(atlasEnemies.findRegion("sandmaster_up"), 0, 0, 50, 50);
        downAnim = new TextureRegion(atlasEnemies.findRegion("sandmaster_down"), 0, 0, 50, 50);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    @Override
    public void update(float dt) {

        if (!destroyed) {
            if (toRedefineBody) {
                reduceSize();
                toRedefineBody = false;

            }
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

    public void hitObject(){
    }

    public void hitByFlame() {
        if (!untouchable)
            if (lives > 1) {
                toRedefineBody = true;
                lives--;
                setUntouchable(true);
            } else {
                lives--;
                toDestroy = true;
                Filter filter = new Filter();
                filter.maskBits = Constants.NOTHING_BIT;
                b2body.getFixtureList().get(0).setFilterData(filter);
            }
    }
}
