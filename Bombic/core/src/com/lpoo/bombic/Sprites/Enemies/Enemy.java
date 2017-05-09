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
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Screens.PlayScreen;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Enemy extends Sprite {
    public enum State {STANDING, RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, DYING, DEAD};
    protected World world;
    protected Game game;
    public Body b2body;
    public Vector2 velocity;
    protected float speed;

    protected Fixture fixture;

    protected boolean toDestroy;
    protected boolean destroyed;

    protected TextureRegion standingAnim;
    protected Animation<TextureRegion> runUpAnim;
    protected Animation<TextureRegion> runDownAnim;
    protected Animation<TextureRegion> runLeftAnim;
    protected Animation<TextureRegion> runRightAnim;
    protected Animation<TextureRegion> dyingAnim;

    public Enemy(Game game, float x, float y){
        this.world = game.getWorld();
        this.game = game;
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
        shape.setRadius(22 / Bombic.PPM);
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
        if(velocity.y > 0)
            velocity.y = speed;
        else
            velocity.y = -speed;
    }

    public abstract void update(float dt);

    public abstract void hitByFlame();

    public void hitObject(){
        reverseVelocity();
    }

    public boolean getDestroyed(){
        return destroyed;
    }

    public void reverseVelocity(){
        if(velocity.x != 0)
            velocity.x = -velocity.x;
        else if(velocity.y != 0)
            velocity.y = -velocity.y;


    }
}
