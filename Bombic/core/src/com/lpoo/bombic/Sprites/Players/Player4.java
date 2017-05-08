package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;

/**
 * Created by Rui Quaresma on 08/05/2017.
 */

public class Player4 extends Bomber {
    public Player4(World world, PlayScreen screen, int id) {
        super(world, screen, id);
    }

    @Override
    public void move(int dir) {
        switch (dir) {
            case Input.Keys.NUMPAD_8:
                velocity.set(0, Bombic.GAME_SPEED + speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.NUMPAD_5:
                velocity.set(0, -Bombic.GAME_SPEED - speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.NUMPAD_4:
                velocity.set(-Bombic.GAME_SPEED - speedIncrease, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.NUMPAD_6:
                velocity.set(Bombic.GAME_SPEED + speedIncrease, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            default:
                break;

        }
    }

    @Override
    public void stop(int dir) {
        switch (dir) {
            case Input.Keys.NUMPAD_8:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.NUMPAD_5:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.NUMPAD_4:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.NUMPAD_6:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            default:
                break;

        }
    }
}
