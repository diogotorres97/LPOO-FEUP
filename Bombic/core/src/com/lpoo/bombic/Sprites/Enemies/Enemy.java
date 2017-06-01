package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.bombic.EnemiesStrategy.Strategy;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Logic.Game.GAMESPEED;

import java.util.HashMap;
import java.util.Random;
/**
 * Interface used to implement the get method of the current animation frames
 */
interface AnimationFrames {
    TextureRegion get();
}
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
     * HashMap relating player state with the animation running frame
     */
    protected HashMap<State, AnimationFrames> animationMultiFramesMap;
    /**
     * HashMap relating player state with the animation standing frame
     */
    protected HashMap<State, AnimationFrames> animationSingleFramesMap;
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
    protected TextureRegion standingAnim, rightAnim, leftAnim, upAnim, downAnim;
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

        initiateAnimationFramesMap();

        atlasEnemies = new TextureAtlas("enemies.atlas");
        defineEnemy();
        b2body.setActive(false);


    }
    /**
     * Initiates animationFramesMap
     */
    private void initiateAnimationFramesMap(){
        animationMultiFramesMap = new HashMap<State, AnimationFrames>();
        animationSingleFramesMap = new HashMap<State, AnimationFrames>();

        initiateAnimationFramesMapRunning();
        initiateAnimationFramesMapStanding();

        animationMultiFramesMap.put(State.DYING, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return dyingAnim.getKeyFrame(stateTime, true);
            }
        });

        animationSingleFramesMap.put(State.DYING, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return dyingAnim.getKeyFrame(stateTime, true);
            }
        });

    }

    /**
     * Initiates animationFramesMap with the multi animations
     */
    private void initiateAnimationFramesMapRunning(){
        animationMultiFramesMap.put(State.RUNNING_LEFT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return runLeftAnim.getKeyFrame(stateTime, true);
            }
        });

        animationMultiFramesMap.put(State.RUNNING_RIGHT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return runRightAnim.getKeyFrame(stateTime, true);
            }
        });

        animationMultiFramesMap.put(State.RUNNING_UP, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return runUpAnim.getKeyFrame(stateTime, true);
            }
        });

        animationMultiFramesMap.put(State.RUNNING_DOWN, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return runDownAnim.getKeyFrame(stateTime, true);
            }
        });

        animationMultiFramesMap.put(State.STANDING, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return standingAnim;
            }
        });
    }
    /**
     * Initiates animationSingleFramesMap with the single animations
     */
    private void initiateAnimationFramesMapStanding(){
        animationSingleFramesMap.put(State.RUNNING_LEFT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return leftAnim;
            }
        });

        animationSingleFramesMap.put(State.RUNNING_RIGHT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return rightAnim;
            }
        });

        animationSingleFramesMap.put(State.RUNNING_UP, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return upAnim;
            }
        });

        animationSingleFramesMap.put(State.RUNNING_DOWN, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return downAnim;
            }
        });

        animationSingleFramesMap.put(State.STANDING, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return standingAnim;
            }
        });
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
     * Default enemy update
     * @param dt
     */
    protected void enemiesUpdate(float dt){
        if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt * speed));
            if (toDestroy) {
                velocity.set(0, 0);
                b2body.setLinearVelocity(velocity);

                if (stateTime >= 0.5f) {
                    world.destroyBody(b2body);
                    destroyed = true;

                }
            } else {
                if (b2body.isActive()) {
                    strategy.move(this);
                    b2body.setLinearVelocity(velocity);
                }
            }

        }
    }

    protected void multipleLivesEnemiesUpdate(float dt){
        if (!destroyed)
            if (toRedefineBody) {
                reduceSize();
                toRedefineBody = false;

            }
        enemiesUpdate(dt);
    }

    protected void reduceSize() {
        setBounds(getX(), getY(), 40 / Constants.PPM, 40 / Constants.PPM);
    }
    /**
     * Gets current animation frame
     * @param dt
     * @return
     */
    protected TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region = animationMultiFramesMap.get(currentState).get();

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
    public void hitByFlame(){
        if (!untouchable)
            if (lives > 1) {
                toRedefineBody = true;
                lives--;
                setUntouchable(true);
            } else {
                lives--;
                toDestroy = true;
                Filter filter = new Filter();
                filter.maskBits = Constants.NOTHING_BIT;
                b2body.getFixtureList().get(0).setFilterData(filter);
            }
    }

    /**
     * Sets objectHit to true
     */
    public void hitObject(){

    }

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
    @Override
    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
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

