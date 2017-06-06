package com.lpoo.bombic.Logic.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Logic.GameLogic.Game;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Logic.GameLogic.Game.GAMESPEED;

/**
 * Creates a TV
 */

public class TV extends Enemy {
    /**
     * Constructor
     *
     * @param game
     * @param x
     * @param y
     */
    public TV(Game game, float x, float y) {
        super(game, x, y);
        dieSound = gam.manager.get("sounds/ghostDie.wav", Sound.class);
        lives = 3;
        toRedefineBody = false;

        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 4f;
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
            frames.add(new TextureRegion(atlasEnemies.findRegion("tv_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("tv_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("tv_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("tv_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("tv_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim() {
        standingAnim = new TextureRegion(atlasEnemies.findRegion("tv_down"), 0, 0, 50, 50);
    }

    @Override
    public void update(float dt) {
        multipleLivesEnemiesUpdate(dt);
    }

}