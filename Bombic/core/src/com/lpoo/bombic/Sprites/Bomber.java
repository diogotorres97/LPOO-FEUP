package com.lpoo.bombic.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Bomber extends Sprite{
    public World world;
    public Body b2body;

    public Vector2 velocity;

    public Bomber(World world){
        this.world = world;
        defineBomber();
    }

    public void defineBomber(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(75 / Bombic.PPM , 475 / Bombic.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-23, 20).scl(1 / Bombic.PPM);
        vertice[1] = new Vector2(23, 20).scl(1 / Bombic.PPM);
        vertice[2] = new Vector2(-23, 3).scl(1 / Bombic.PPM);
        vertice[3] = new Vector2(23, 3).scl(1 / Bombic.PPM);
        shape.set(vertice);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        velocity = new Vector2(0, 0);
    }
    public void move(int dir ){
        switch (dir){
            case Input.Keys.UP:
                velocity.set(velocity.x, Bombic.GAME_SPEED);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case Input.Keys.DOWN:
                velocity.set(velocity.x, -Bombic.GAME_SPEED);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case Input.Keys.LEFT:
                velocity.set(-Bombic.GAME_SPEED, velocity.y);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y);
                break;
            case Input.Keys.RIGHT:
                velocity.set(Bombic.GAME_SPEED, velocity.y);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y);

                break;
            default:
                break;

        }
    }

    public void stop(int dir){
        switch (dir){
            case Input.Keys.UP:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.DOWN:
                velocity.set(velocity.x, 0);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.LEFT:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            case Input.Keys.RIGHT:
                velocity.set(0, velocity.y);
                b2body.setLinearVelocity(velocity);
                break;
            default:
                break;

        }

    }
}
