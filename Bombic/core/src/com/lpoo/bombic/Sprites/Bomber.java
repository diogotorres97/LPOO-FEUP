package com.lpoo.bombic.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.ItemDef;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Bomber extends Sprite{
    public enum State {RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, STANDING_RIGHT, STANDING_LEFT, STANDING_UP, STANDING_DOWN, DEAD};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public PlayScreen screen;
    private Array<TextureRegion> bomberStand;
    private Animation<TextureRegion> bomberRunUp;
    private Animation<TextureRegion> bomberRunDown;
    private Animation<TextureRegion> bomberRunLeft;
    private Animation<TextureRegion> bomberRunRight;
    private float stateTimer;

    private int nFlames;
    private int nBombs;
    private float speedIncrease;
    private boolean bomberIsDead;

    public Vector2 velocity;




    public Bomber(World world, PlayScreen screen){
        this.world = world;
        this.screen = screen;
        currentState = State.STANDING_DOWN;
        previousState = State.STANDING_DOWN;
        stateTimer = 0;

        speedIncrease = 0;
        nFlames = nBombs = 1;
        bomberIsDead = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Creating running right animation
        for(int i = 0 ; i < 9 ; i++)
            frames.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_right"),i*50, 0, 50, 50));
        bomberRunRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running left animation
        for(int i = 0 ; i < 9 ; i++)
            frames.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_left"),i*50, 0, 50, 50));
        bomberRunLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running up/down animation
        for(int i = 0 ; i < 9 ; i++)
            frames.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_up"),i*50, 0, 50, 50));
        bomberRunUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating running up/down animation
        for(int i = 0 ; i < 9 ; i++)
            frames.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_down"),i*50, 0, 50, 50));
        bomberRunDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        bomberStand = new Array<TextureRegion>();

        bomberStand.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_down"),0, 0, 50, 50 ));
        bomberStand.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_up"),0, 0, 50, 50 ));
        bomberStand.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_left"),0, 0, 50, 50 ));
        bomberStand.add(new TextureRegion(screen.getAtlasBomber().findRegion("bomber0_right"),0, 0, 50, 50 ));

        defineBomber();

        setBounds(0, 0, 50 / Bombic.PPM, 50 / Bombic.PPM);
        setRegion(bomberStand.get(0));
    }

    public void defineBomber(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(75 / Bombic.PPM , 475 / Bombic.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(23 / Bombic.PPM);
        fdef.filter.categoryBits = Bombic.BOMBER_BIT;
        fdef.filter.maskBits = Bombic.GROUND_BIT |
                Bombic.DESTROYABLE_OBJECT_BIT |
                Bombic.OBJECT_BIT |
                Bombic.CLASSIC_BOMB_BIT |
                Bombic.FLAMES_BIT |
                Bombic.BONUS_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);


        velocity = new Vector2(0, 0);
    }
    public void move(int dir ){
        switch (dir){
            case Input.Keys.UP:
                velocity.set(0, Bombic.GAME_SPEED + speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case Input.Keys.DOWN:
                velocity.set(0, -Bombic.GAME_SPEED + speedIncrease);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y );
                break;
            case Input.Keys.LEFT:
                velocity.set(-Bombic.GAME_SPEED + speedIncrease, 0);
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x , b2body.getPosition().y);
                break;
            case Input.Keys.RIGHT:
                velocity.set(Bombic.GAME_SPEED + speedIncrease, 0);
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

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt * (Bombic.GAME_SPEED + speedIncrease)));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;

        switch (currentState){

            case RUNNING_LEFT:
                region = bomberRunLeft.getKeyFrame(stateTimer,true);
                break;
            case RUNNING_RIGHT:
                region = bomberRunRight.getKeyFrame(stateTimer,true);
                break;
            case RUNNING_UP:
                region = bomberRunUp.getKeyFrame(stateTimer,true);
                break;
            case RUNNING_DOWN:
                region = bomberRunDown.getKeyFrame(stateTimer,true);
                break;
            case STANDING_UP:
                region = bomberStand.get(1);
                break;
            case STANDING_LEFT:
                region = bomberStand.get(2);
                break;
            case STANDING_RIGHT:
                region = bomberStand.get(3);
                break;
            default:
            case STANDING_DOWN:
                region = bomberStand.get(0);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        previousState = currentState;

        return region;
    }

    public State getState(){
        if(bomberIsDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().x > 0)
            return State.RUNNING_RIGHT;
        else if(b2body.getLinearVelocity().x < 0)
            return State.RUNNING_LEFT;
        else if(b2body.getLinearVelocity().y > 0)
            return State.RUNNING_UP;
        else if(b2body.getLinearVelocity().y < 0)
            return State.RUNNING_DOWN;

        switch (previousState) {
            case RUNNING_UP:
                return State.STANDING_UP;
            case RUNNING_LEFT:
                return State.STANDING_LEFT;
            case RUNNING_RIGHT:
                return State.STANDING_RIGHT;
            case RUNNING_DOWN:
                return State.STANDING_DOWN;
            default:
                return previousState;
            }
    }

    public int getFlames() {
        return nFlames;
    }

    public int getBombs() {
        return nBombs;
    }

    public void setFlames(int nFlames) {
        this.nFlames += nFlames;
    }

    public void setBombs(int nBombs) {
        this.nBombs += nBombs;
    }

    public float getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(float speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public void placeBomb() {

        if(getBombs() > 0) {
            screen.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x , b2body.getPosition().y),
                    ClassicBomb.class));
            setBombs(-1);
        }

    }

    public boolean isDead(){
        return bomberIsDead;
    }

    public void die(){
        if(!isDead()){
            bomberIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Bombic.NOTHING_BIT;

            //CALL DIE ANIMATION
        }

    }

    public float getStateTimer(){
        return stateTimer;
    }

}
