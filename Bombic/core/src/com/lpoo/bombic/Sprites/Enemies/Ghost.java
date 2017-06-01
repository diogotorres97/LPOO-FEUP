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
 * Creates a ghost
 */

public class Ghost extends Enemy {
    /**
     * Constructor
     *
     * @param game
     * @param x
     * @param y
     */
    public Ghost(Game game, float x, float y) {
        super(game, x, y);

        lives = 3;
        toRedefineBody = false;

        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 3f;
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

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("ghost_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("ghost_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("ghost_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("ghost_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("ghost_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("ghost_down"), 0, 0, 50, 50);
    }

    @Override
    public void update(float dt) {
        multipleLivesEnemiesUpdate(dt);
    }

}
