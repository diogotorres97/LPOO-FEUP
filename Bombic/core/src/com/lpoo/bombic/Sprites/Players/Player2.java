package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.bombic.Game;

/**
 * Created by Rui Quaresma on 08/05/2017.
 */

public class Player2 extends Player {
    public Player2(Game game, int id, Vector2 pos) {
        super(game, id, pos);
    }

    @Override
    public void move(int dir) {
        switch (dir) {
            case Input.Keys.W:
                velocity.set(0, game.getGameSpeed() + speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.S:
                velocity.set(0, -game.getGameSpeed() - speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.A:
                velocity.set(-game.getGameSpeed() - speedIncrease, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x, b2body.getPosition().y);
                break;
            case Input.Keys.D:
                velocity.set(game.getGameSpeed() + speedIncrease, 0);
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
