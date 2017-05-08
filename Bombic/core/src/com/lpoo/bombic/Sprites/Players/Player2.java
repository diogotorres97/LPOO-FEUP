package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;

/**
 * Created by Rui Quaresma on 08/05/2017.
 */

public class Player2 extends Bomber {
    public Player2(World world, PlayScreen screen, int id) {
        super(world, screen, id);
    }

    @Override
    public void move(int dir) {
        switch (dir) {
            case Input.Keys.W:
                velocity.set(0, Bombic.GAME_SPEED + speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.S:
                velocity.set(0, -Bombic.GAME_SPEED - speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.A:
                velocity.set(-Bombic.GAME_SPEED - speedIncrease, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.D:
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
            case Input.Keys.W:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.S:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.A:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.D:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            default:
                break;

        }
    }
}
