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
 * Creates a sandmaster
 */

public class Sandmaster extends Enemy {


    /**
     * Constructor
     *
     * @param game
     * @param x
     * @param y
     */
    public Sandmaster(Game game, float x, float y) {
        super(game, x, y);
        dieSound = gam.manager.get("sounds/enemyDie3.wav", Sound.class);
        lives = 3;
        toRedefineBody = false;
        untouchable = false;

        variablesInitializer();

        speed = GAMESPEED / 3f;
        velocity = new Vector2(0, speed);
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
