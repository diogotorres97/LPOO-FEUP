package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

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

    protected TextureAtlas atlasEnemies;

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

        atlasEnemies = new TextureAtlas("enemies.atlas");
        defineEnemy();
        b2body.setActive(false);


    }

    protected void defineEnemy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create player shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / Constants.PPM);
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.BOMBER_BIT |
                Constants.FLAMES_BIT;
        fdef.shape = shape;

        fixture = b2body.createFixture(fdef);
    }

    protected void setSpeed(float speedDivider){
        speed = game.getGameSpeed() * speedDivider;
        if(velocity.y > 0)
            velocity.y = speed;
        else
            velocity.y = -speed;
    }

    public abstract void update(float dt);

    public void pause(){
        b2body.setLinearVelocity(0, 0);
    }

    public abstract void hitByFlame(float timeLeft);

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
