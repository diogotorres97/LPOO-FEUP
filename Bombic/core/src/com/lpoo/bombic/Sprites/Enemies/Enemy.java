package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Enemy extends Sprite {
    public enum State {STANDING, RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, DYING, DEAD};
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    protected float speed;

    protected Fixture fixture;

    protected TextureRegion standingAnim;
    protected Animation<TextureRegion> runUpAnim;
    protected Animation<TextureRegion> runDownAnim;
    protected Animation<TextureRegion> runLeftAnim;
    protected Animation<TextureRegion> runRightAnim;
    protected Animation<TextureRegion> dyingAnim;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);

        defineEnemy();
        speed = Bombic.GAME_SPEED / 2;
        velocity = new Vector2(0 , speed);
    }

    protected void defineEnemy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(18 / Bombic.PPM);
        fdef.filter.categoryBits = Bombic.ENEMY_BIT;
        fdef.filter.maskBits = Bombic.GROUND_BIT |
                Bombic.DESTROYABLE_OBJECT_BIT |
                Bombic.OBJECT_BIT |
                Bombic.CLASSIC_BOMB_BIT |
                Bombic.BOMBER_BIT |
                Bombic.FLAMES_BIT;
        fdef.shape = shape;

        fixture = b2body.createFixture(fdef);
    }

    protected void setSpeed(){
        speed = Bombic.GAME_SPEED / 2;
        //velocity.y = speed;
    }

    protected void move(int dir ){
        switch (dir){
            case 0:
                velocity.set(0, speed);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case 1:
                velocity.set(0, -speed);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case 2:
                velocity.set(-speed, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y);
                break;
            case 3:
                velocity.set(speed, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y);

                break;
            default:
                break;

        }
    }


    public abstract void update(float dt);

    public abstract void hitByFlame();

    public void hitObject(){
        reverseVelocity();
    }

    public void reverseVelocity(){
        if(velocity.x != 0)
            velocity.x = -velocity.x;
        else if(velocity.y != 0)
            velocity.y = -velocity.y;

    }
}
