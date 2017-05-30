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
 * Creates the enemy
 */
public abstract class Enemy extends Sprite {
    /**
     * Enemy states
     */
    protected enum State {STANDING, RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, DYING, DEAD}

    /**
     * Number of lives of enemy
     */
    protected int lives;
    /**
     * Whether its to redefine enemy body or not
     */
    protected boolean toRedefineBody;
    /**
     * Current enemy state
     */
    protected State currentState;
    /**
     * Previous enemy state
     */
    protected State previousState;

    protected World world;

    protected Game game;
    public Body b2body;
    public Vector2 velocity;
    protected float speed;
    /**
     * Enemy movement strategy
     */
    protected Strategy strategy;
    /**
     * Whether has hit an object or not
     */
    private boolean objectHit;
    /**
     * Atlas with enemies regions
     */
    protected TextureAtlas atlasEnemies;
    /**
     * Previous square x in witch enemy was in
     */
    protected float lastSquareX;
    /**
     * Previous square y in witch enemy was in
     */
    protected float lastSquareY;
    protected float stateTime;
    /**
     * Whether the enemy can be affected by flames or not
     */
    protected boolean untouchable;

    protected Fixture fixture;
    /**
     * If its for enemy to move or not
     */
    private boolean toMove;

    protected boolean toDestroy;
    protected boolean destroyed;
    /**
     * Enemy animations
     */
    protected TextureRegion standingAnim;
    protected Animation<TextureRegion> runUpAnim;
    protected Animation<TextureRegion> runDownAnim;
    protected Animation<TextureRegion> runLeftAnim;
    protected Animation<TextureRegion> runRightAnim;
    protected Animation<TextureRegion> dyingAnim;

    /**
     * Constructor
     * @param game
     * @param x
     * @param y
     */
    public Enemy(Game game, float x, float y) {
        this.world = game.getWorld();
        this.game = game;
        setPosition(x, y);

        atlasEnemies = gam.manager.get("enemies.atlas");
        defineEnemy();
        b2body.setActive(false);


    }
    /**
     * Defines enemy body
     */
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

        protected void variablesInitializer(){
        createAnimations();

        stateTime = 0;
        setBounds(getX(), getY(), 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(standingAnim);
        toDestroy = false;
        destroyed = false;
        currentState = previousState = State.STANDING;

        fixture.setUserData(this);

        lastSquareX = 0;
        lastSquareY = 0;
    }

    /**
     * Creates animations
     */
    protected abstract void createAnimations();

    /**
     * Gets current animation frame
     * @param dt
     * @return
     */
    protected TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState) {

            case RUNNING_LEFT:
                region = runLeftAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_RIGHT:
                region = runRightAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_UP:
                region = runUpAnim.getKeyFrame(stateTime, true);
                break;
            case RUNNING_DOWN:
                region = runDownAnim.getKeyFrame(stateTime, true);
                break;
            case DYING:
                region = dyingAnim.getKeyFrame(stateTime, true);
                break;
            default:
            case STANDING:
                region = standingAnim;
                break;

        }

        stateTime = currentState == previousState ? stateTime + dt : 0;

        previousState = currentState;

        return region;
    }

    /**
     * Gets the current enemy state
     * @return
     */
    protected State getState(){
        if (destroyed)
            return State.DEAD;
        else if (toDestroy)
            return State.DYING;
        else if (b2body.getLinearVelocity().x > 0)
            return State.RUNNING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return State.RUNNING_LEFT;
        else if (b2body.getLinearVelocity().y > 0)
            return State.RUNNING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.RUNNING_DOWN;
        else
            return State.STANDING;
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

    /**
     * Dies or looses a life
     */
    public abstract void hitByFlame();

    /**
     * Sets objectHit to true
     */
    public abstract void hitObject();

    /**
     * If enemy hit a bomb alters its velocity, and resets lastSquare
     */
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

    /**
     * Check whether a player is near
     * @return player's square coordinates
     */
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

