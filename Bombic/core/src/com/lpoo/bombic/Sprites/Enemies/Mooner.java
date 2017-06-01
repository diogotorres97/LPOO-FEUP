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
 * Creates a mooner
 */

public class Mooner extends Enemy {
    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public Mooner(Game game, float x, float y) {

        super(game, x, y);

        variablesInitializer();
        lives = 1;
        speed = GAMESPEED * 1.1f;
        velocity = new Vector2(0, speed);

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

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("mooner_down"), i * 50, 0, 50, 50));
        runDownAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("mooner_up"), i * 50, 0, 50, 50));
        runUpAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("mooner_right"), i * 50, 0, 50, 50));
        runRightAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 5; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("mooner_left"), i * 50, 0, 50, 50));
        runLeftAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(atlasEnemies.findRegion("mooner_dying"), i * 50, 0, 50, 50));
        dyingAnim = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim(){
        standingAnim = new TextureRegion(atlasEnemies.findRegion("mooner_down"), 0, 0, 50, 50);
    }

    @Override
    public void update(float dt) {

        enemiesUpdate(dt);

    }


}
