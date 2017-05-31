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
 * Creates a clouder
 */
public class Clouder extends Enemy {

    protected TextureRegion standingAnim, rightAnim, leftAnim, upAnim, downAnim;

    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public Clouder(Game game, float x, float y) {
        super(game, x, y);

        variablesInitializer();

        speed = GAMESPEED * 1.1f;
        velocity = new Vector2(0, speed);

    }

    protected void createAnimations() {
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

        enemiesUpdate(dt);

    }

    protected TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = animationSingleFramesMap.get(currentState).get();

        stateTime = currentState == previousState ? stateTime + dt : 0;

        previousState = currentState;

        return region;
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