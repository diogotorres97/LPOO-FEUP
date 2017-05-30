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
import com.lpoo.bombic.EnemiesStrategy.Strategy;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Logic.Game.GAMESPEED;

import java.util.Random;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public abstract class Enemy extends Sprite {
    public enum State {STANDING, RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, DYING, DEAD}

    protected World world;

    protected Game game;
    public Body b2body;
    public Vector2 velocity;
    protected float speed;

    protected Strategy strategy;

    private boolean objectHit;

    protected TextureAtlas atlasEnemies;

    protected float lastSquareX;
    protected float lastSquareY;
    protected float stateTime;
    protected float untouchableTime;

    protected boolean untouchable;

    protected Fixture fixture;

    private boolean toMove;

    protected boolean toDestroy;
    protected boolean destroyed;

    protected TextureRegion standingAnim;
    protected Animation<TextureRegion> runUpAnim;
    protected Animation<TextureRegion> runDownAnim;
    protected Animation<TextureRegion> runLeftAnim;
    protected Animation<TextureRegion> runRightAnim;
    protected Animation<TextureRegion> dyingAnim;

    public Enemy(Game game, float x, float y) {
        this.world = game.getWorld();
        this.game = game;
        setPosition(x, y);

        atlasEnemies = gam.manager.get("enemies.atlas");
        defineEnemy();
        b2body.setActive(false);


    }

    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / Constants.PPM);
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits = Constants.BOMB_BIT |
                Constants.OBJECT_BIT |
                Constants.DESTROYABLE_OBJECT_BIT |
                Constants.BOMBER_BIT |
                Constants.FLAMES_BIT;
        fdef.shape = shape;

        fixture = b2body.createFixture(fdef);

    }

    public float getLastSquareX() {
        return lastSquareX;
    }

    public float getLastSquareY() {
        return lastSquareY;
    }

    public void setLastSquareX(float lastSquareX) {
        this.lastSquareX = lastSquareX;
    }

    public void setLastSquareY(float lastSquareY) {
        this.lastSquareY = lastSquareY;
    }

    public Game getGame() {
        return game;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setSpeed(float speedDivider) {
        speed = GAMESPEED * speedDivider;

    }

    public boolean isToMove() {
        return toMove;
    }

    public void setToMove(boolean toMove) {
        this.toMove = toMove;
    }

    public boolean isObjectHit() {
        return objectHit;
    }

    public void setObjectHit(boolean objectHit) {
        this.objectHit = objectHit;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public abstract void update(float dt);

    public void pause() {
        b2body.setLinearVelocity(0, 0);
    }

    public abstract void hitByFlame();

    public abstract void hitObject();

    public void hitBomb() {
        if (velocity.y < 0)
            setLastSquareY((int) ((b2body.getPosition().y - 0.5) * Constants.PPM / 50));
        else if (velocity.y > 0)
            setLastSquareY((int) ((b2body.getPosition().y + 0.5) * Constants.PPM / 50));
        else if (velocity.x < 0) {
            setLastSquareX((int) ((b2body.getPosition().x - 0.5) * Constants.PPM / 50));
        }
        else if (velocity.x > 0) {
            setLastSquareX((int) ((b2body.getPosition().x + 0.5) * Constants.PPM / 50));
        }
        reverseVelocity();


    }

    public void setUntouchable(boolean untouchable){
        this.untouchable = untouchable;
    }

    public boolean getDestroyed() {
        return destroyed;
    }

    public void reverseVelocity() {

        if (velocity.x != 0)
            velocity.x = -velocity.x;
        else if (velocity.y != 0)
            velocity.y = -velocity.y;
    }

    private float getSquare(float value) {
        int ret = (int) (value * Constants.PPM / 50);

        return ret;
    }

    public float[] checkPlayerNear(){
        Random rand = new Random();
        float[] playerPosition = new float[2];
        int [] nearPlayers = new int[game.getPlayers().length];
        int i=0;
        boolean found = false;
        int numPlayers = 0;

        for(Player player : game.getPlayers()){
            if((Math.abs(getSquare(player.getPosition().x)  - getLastSquareX()) <= 2) && (Math.abs(getSquare(player.getPosition().y) - getLastSquareY()) <= 2)) {
                nearPlayers[i] = i;
                numPlayers++;
                found = true;
            }
            i++;
        }

        if(found){
            int playerChoosen = rand.nextInt(numPlayers);
            playerPosition[0] = getSquare(game.getPlayers()[nearPlayers[playerChoosen]].getPosition().x);
            playerPosition[1] = getSquare(game.getPlayers()[nearPlayers[playerChoosen]].getPosition().y);
        }

        return playerPosition;

    }
}

