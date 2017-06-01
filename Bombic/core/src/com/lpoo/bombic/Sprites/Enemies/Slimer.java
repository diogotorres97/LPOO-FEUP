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

import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Creates a slimer
 */

public class Slimer extends Enemy {

    /**
     * Constructor
     *
     * @param game
     * @param x
     * @param y
     */
    public Slimer(Game game, float x, float y) {
        super(game, x, y);

        lives = 2;
        toRedefineBody = false;
        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 4f;
        velocity = new Vector2(0, speed);

    }

    protected void createAnimations() {
        createAnims();
        createDyingAnim();
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

    @Override
    public void update(float dt) {
        multipleLivesEnemiesUpdate(dt);
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = animationSingleFramesMap.get(currentState).get();
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

}
