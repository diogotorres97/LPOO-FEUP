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
 * Creates advanced trap
 */

public class AdvancedTrap extends Enemy {
    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public AdvancedTrap(Game game, float x, float y) {
        super(game, x, y);

        setToMove(true);
        setObjectHit(false);

        lives = 2;
        toRedefineBody = false;
        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 3f;
        velocity = new Vector2(0, speed);
    }

    private void reduceSize(){
        setBounds(getX(), getY(), 40 / Constants.PPM, 40 / Constants.PPM);
    }

    protected void createAnimations(){
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    private void createRunDownAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("advancedTrap_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("advancedTrap_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("advancedTrap_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("advancedTrap_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("advancedTrap_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim(){
        standingAnim = new TextureRegion(atlasEnemies.findRegion("advancedTrap_down"), 0, 0, 50, 50);
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


    public void hitObject(){
        setObjectHit(true);
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
