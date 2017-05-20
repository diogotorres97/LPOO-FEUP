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
 * Created by Rui Quaresma on 20/05/2017.
 */

public class RedBall extends Enemy {

    private Enemy.State currentState;
    private Enemy.State previousState;

    private int lives;
    private boolean toRedefineBody;

    public RedBall(Game game, float x, float y) {
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
        currentState = previousState = Enemy.State.STANDING;

        fixture.setUserData(this);

        speed = game.getGameSpeed() / 2f;
        velocity = new Vector2(0, speed);
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
        shape.setRadius((19) / Constants.PPM);
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits =Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.BOMBER_BIT |
                Constants.FLAMES_BIT;
        fdef.shape = shape;
        setBounds(getX(), getY(), (45) / Constants.PPM, (45) / Constants.PPM);
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        toRedefineBody = false;
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
            frames.add(new TextureRegion(atlasEnemies.findRegion("redball_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("redball_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("redball_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("redball_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("redball_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim(){
        standingAnim = new TextureRegion(atlasEnemies.findRegion("redball_down"), 0, 0, 50, 50);
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
            toRedefineBody = false;

        }

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

    public Enemy.State getState() {
        if (destroyed)
            return Enemy.State.DEAD;
        else if (toDestroy)
            return Enemy.State.DYING;
        else if (b2body.getLinearVelocity().x > 0)
            return Enemy.State.RUNNING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return Enemy.State.RUNNING_LEFT;
        else if (b2body.getLinearVelocity().y > 0)
            return Enemy.State.RUNNING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return Enemy.State.RUNNING_DOWN;
        else
            return Enemy.State.STANDING;

    }

    private void setUntouchableEnemy() {
        if (startUntouchable) {
            Filter filter = new Filter();
            filter.categoryBits = Constants.ENEMY_BIT;
            filter.maskBits =    Constants.DESTROYABLE_OBJECT_BIT |
                    Constants.OBJECT_BIT |
                    Constants.BOMB_BIT |
                    Constants.BOMBER_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        } else {
            Filter filter = new Filter();
            filter.categoryBits = Constants.ENEMY_BIT;
            filter.maskBits =    Constants.DESTROYABLE_OBJECT_BIT |
                    Constants.OBJECT_BIT |
                    Constants.BOMB_BIT |
                    Constants.BOMBER_BIT |
                    Constants.FLAMES_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        }
    }

    public void hitBomb() {
        if (velocity.y < 0)
            setLastSquareY((int) ((b2body.getPosition().y - 0.5) * Constants.PPM / 50));
        else if (velocity.y > 0)
            setLastSquareY((int) ((b2body.getPosition().y + 0.5) * Constants.PPM / 50));
        else if (velocity.x < 0) {
            setLastSquareX((int) ((b2body.getPosition().x - 0.5) * Constants.PPM / 50));
        }
        else if (velocity.x > 0) {
            setLastSquareX((int) ((b2body.getPosition().x + 0.5) * Constants.PPM / 50));
        }
        super.hitBomb();

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