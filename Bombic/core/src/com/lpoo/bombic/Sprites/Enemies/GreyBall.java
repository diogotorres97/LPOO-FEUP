package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Creates a greyball
 */
public class GreyBall extends Enemy {
    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public GreyBall(Game game, float x, float y) {
        super(game, x, y);

        variablesInitializer();

        lives = 1;

        speed = GAMESPEED / 2;
        velocity = new Vector2(0, speed);


    }

    protected void createAnimations() {
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    private void createRunDownAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunUpAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunRightAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createRunLeftAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("greyball_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
    }

    private void createStandingAnim() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("greyball_down"), 0, 0, 50, 50);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }


    @Override
    public void update(float dt) {

        enemiesUpdate(dt);

    }

}
