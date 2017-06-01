package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Logic.Game;

import static com.lpoo.bombic.Logic.Game.GAMESPEED;

/**
 * Created by Rui Quaresma on 31/05/2017.
 */

public abstract class AbstractTrap extends Enemy {
    public AbstractTrap(Game game, float x, float y) {
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
    @Override
    protected void createAnimations(){
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    protected abstract void createRunDownAnim();
    protected abstract void createRunUpAnim();
    protected abstract void createRunRightAnim();
    protected abstract void createRunLeftAnim();
    protected abstract void createDyingAnim();
    protected abstract void createStandingAnim();


    @Override
    public void update(float dt) {
        multipleLivesEnemiesUpdate(dt);
    }

    public void hitObject(){
        setObjectHit(true);
    }

}
