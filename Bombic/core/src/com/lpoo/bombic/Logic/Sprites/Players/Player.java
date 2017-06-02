package com.lpoo.bombic.Logic.Sprites.Players;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Logic.GameLogic.Game;
import com.lpoo.bombic.Logic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Logic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Logic.Sprites.Items.Bombs.LBomb;
import com.lpoo.bombic.Logic.Sprites.Items.Bombs.NBomb;
import com.lpoo.bombic.Logic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Logic.Sprites.Items.Item;
import com.lpoo.bombic.Logic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Tools.Constants;

import java.util.HashMap;

import static com.lpoo.bombic.Bombic.gam;
import static com.lpoo.bombic.Logic.GameLogic.Game.GAMESPEED;

/**
 * Interface used to implement the get method of the current animation frames
 */
interface AnimationFrames {
    TextureRegion get();
}

/**
 * Represents the player character
 */
public class Player extends Sprite {
    /**
     * Player states
     */
    private enum State {
        RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, STANDING_RIGHT, STANDING_LEFT, STANDING_UP, STANDING_DOWN, DYING, DEAD
    }

    /**
     * Current state of the player
     */
    private State currentState;
    /**
     * Previous state of the player
     */
    private State previousState;
    /**
     * World in witch the body will be created
     */
    private World world;
    /**
     * Player body
     */
    private Body b2body;
    /**
     * Game to spawn items to
     */
    private Game game;
    /**
     * Atlas with bombers region
     */
    private TextureAtlas atlasBomber;
    /**
     * Array with the bomber stand regions
     */
    private Array<TextureRegion> bomberStand;
    /**
     * Clean region to be set after dying
     */
    private TextureRegion cleanRegion;
    /**
     * HashMap relating player state with the animation frame
     */
    private HashMap<State, AnimationFrames> animationFramesMap;
    /**
     * Running animations
     */
    private Animation<TextureRegion> bomberRunUp;
    private Animation<TextureRegion> bomberRunDown;
    private Animation<TextureRegion> bomberRunLeft;
    private Animation<TextureRegion> bomberRunRight;
    private Animation<TextureRegion> bomberDying;
    /**
     * State timer increased with dt
     */
    private float stateTimer;
    /**
     * Number of flames, bombs, placedBombs, LBombs, NBombs
     */
    private int nFlames, nBombs, nPlacedBombs, nLBombs, nNBombs;
    /**
     * Increase to the player speed
     */
    private float speedIncrease;
    /**
     * Whether player is dead or not
     */
    private boolean bomberIsDead;
    /**
     * Whether player is to die or not
     */
    private boolean bomberToDie;
    /**
     * Stop player movement
     */
    private boolean stop;
    /**
     * Not allowed to bomb
     */
    private boolean dontBomb;
    /**
     * Keep bombing
     */
    private boolean keepBombing;
    /**
     * Moves to the opposite direction
     */
    private boolean invertWay;
    /**
     * Distant explode activated
     */
    private boolean distantExplode;
    /**
     * To explode bombs
     */
    private boolean explodeBombs;
    /**
     * Kicking bombs
     */
    private boolean kickingBombs;
    /**
     * Sending bombs
     */
    private boolean sendingBombs;
    /**
     * Whether pressed the bomb button or not
     */
    private boolean pressedBombButton;

    /**
     * Hit a bomb
     */
    private boolean hitBomb;
    /**
     * Position of the hit bomb
     */
    private float bombHitX, bombHitY;
    /**
     * Player id
     */
    private int id;
    /**
     * Current velocity
     */
    private Vector2 velocity;
    /**
     * Player position
     */
    private Vector2 pos;
    /**
     * Velocities correspondent to each direction for move
     */
    private HashMap<Integer, Vector2> moveVelocitiesMap;
    /**
     * Velocities correspondent to each direction for stop
     */
    private HashMap<Integer, Vector2> stopVelocitiesMap;
    /**
     * Whether bad bonus is active or not
     */
    private boolean badBonusActive;
    /**
     * Bonus effect ended, so set to destroy
     */
    private boolean destroyBonus;
    /**
     * Bad bonus
     */
    private Bonus badBonus;

    /**
     * Constructor
     *
     * @param game - game to spawn bombs to
     * @param id   - player id
     * @param pos  - position of the body
     */
    public Player(Game game, int id, Vector2 pos) {
        this.id = id;
        this.world = game.getWorld();
        this.game = game;
        this.pos = pos;

        initiateVariables();

        initiateAnimationFramesMap();

        createMoveVelocitiesMap();

        createStopVelocitiesMap();

        createAnimations();

        defineBomber();

        setBounds(0, 0, 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(bomberStand.get(0));
    }

    /**
     * Initiate variables
     */
    private void initiateVariables() {
        moveVelocitiesMap = new HashMap<Integer, Vector2>();
        stopVelocitiesMap = new HashMap<Integer, Vector2>();
        atlasBomber = new TextureAtlas("player.atlas");

        velocity = new Vector2(0, 0);

        currentState = State.STANDING_DOWN;
        previousState = State.STANDING_DOWN;
        stateTimer = 0;

        stop = dontBomb = keepBombing = invertWay = distantExplode = explodeBombs = false;
        kickingBombs = sendingBombs = false;
        badBonusActive = false;
        destroyBonus = false;

        speedIncrease = 0;
        nFlames = nBombs = 1;
        nLBombs = nNBombs = 0;
        nPlacedBombs = 0;
        bomberToDie = bomberIsDead = false;

        bombHitX = bombHitY = 0;
    }

    /**
     * Initiates animationFramesMap
     */
    private void initiateAnimationFramesMap() {
        animationFramesMap = new HashMap<State, AnimationFrames>();

        initiateAnimationFramesMapRunning();
        initiateAnimationFramesMapStanding();

        animationFramesMap.put(State.DYING, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberDying.getKeyFrame(stateTimer, true);
            }
        });


    }

    /**
     * Initiates animationFramesMap with the running states
     */
    private void initiateAnimationFramesMapRunning() {
        animationFramesMap.put(State.RUNNING_LEFT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberRunLeft.getKeyFrame(stateTimer, true);
            }
        });

        animationFramesMap.put(State.RUNNING_RIGHT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberRunRight.getKeyFrame(stateTimer, true);
            }
        });

        animationFramesMap.put(State.RUNNING_UP, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberRunUp.getKeyFrame(stateTimer, true);
            }
        });

        animationFramesMap.put(State.RUNNING_DOWN, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberRunDown.getKeyFrame(stateTimer, true);
            }
        });
    }

    /**
     * Initiates animationFramesMap with the standing states
     */
    private void initiateAnimationFramesMapStanding() {
        animationFramesMap.put(State.STANDING_DOWN, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberStand.get(0);
            }
        });

        animationFramesMap.put(State.STANDING_UP, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberStand.get(1);
            }
        });

        animationFramesMap.put(State.STANDING_LEFT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberStand.get(2);
            }
        });

        animationFramesMap.put(State.STANDING_RIGHT, new AnimationFrames() {
            @Override
            public TextureRegion get() {
                return bomberStand.get(3);
            }
        });
    }

    /**
     * Creates the various animations
     */
    private void createAnimations() {
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    /**
     * Creates running down animation
     */
    private void createRunDownAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), i * 50, 0, 50, 50));
        bomberRunDown = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    /**
     * Creates running up animation
     */
    private void createRunUpAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_up"), i * 50, 0, 50, 50));
        bomberRunUp = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    /**
     * Creates running right animation
     */
    private void createRunRightAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_right"), i * 50, 0, 50, 50));
        bomberRunRight = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    /**
     * Creates running left animation
     */
    private void createRunLeftAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_left"), i * 50, 0, 50, 50));
        bomberRunLeft = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    /**
     * Creates running dying animation
     */
    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_dying"), i * 50, 0, 50, 50));
        bomberDying = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    /**
     * Creates running standing animation
     */
    private void createStandingAnim() {
        bomberStand = new Array<TextureRegion>();

        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_up"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_left"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_right"), 0, 0, 50, 50));

        cleanRegion = new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), 0, 300, 50, 50);
    }

    /**
     * Returns player id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Defines players body
     */
    public void defineBomber() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x / Constants.PPM, pos.y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(23 / Constants.PPM);
        fdef.filter.categoryBits = Constants.BOMBER_BIT;
        fdef.filter.maskBits = Constants.DESTROYABLE_OBJECT_BIT |
                Constants.OBJECT_BIT |
                Constants.BOMB_BIT |
                Constants.FLAMES_BIT |
                Constants.BONUS_BIT |
                Constants.ENEMY_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    private void createMoveVelocitiesMap() {
        moveVelocitiesMap.put(Input.Keys.UP, new Vector2(0, GAMESPEED + speedIncrease));
        moveVelocitiesMap.put(Input.Keys.W, new Vector2(0, GAMESPEED + speedIncrease));
        moveVelocitiesMap.put(Input.Keys.I, new Vector2(0, GAMESPEED + speedIncrease));
        moveVelocitiesMap.put(Input.Keys.NUMPAD_8, new Vector2(0, GAMESPEED + speedIncrease));

        moveVelocitiesMap.put(Input.Keys.DOWN, new Vector2(0, -GAMESPEED - speedIncrease));
        moveVelocitiesMap.put(Input.Keys.S, new Vector2(0, -GAMESPEED - speedIncrease));
        moveVelocitiesMap.put(Input.Keys.K, new Vector2(0, -GAMESPEED - speedIncrease));
        moveVelocitiesMap.put(Input.Keys.NUMPAD_5, new Vector2(0, -GAMESPEED - speedIncrease));

        moveVelocitiesMap.put(Input.Keys.LEFT, new Vector2(-GAMESPEED - speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.A, new Vector2(-GAMESPEED - speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.J, new Vector2(-GAMESPEED - speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.NUMPAD_4, new Vector2(-GAMESPEED - speedIncrease, 0));

        moveVelocitiesMap.put(Input.Keys.RIGHT, new Vector2(GAMESPEED + speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.D, new Vector2(GAMESPEED + speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.L, new Vector2(GAMESPEED + speedIncrease, 0));
        moveVelocitiesMap.put(Input.Keys.NUMPAD_6, new Vector2(GAMESPEED + speedIncrease, 0));
    }

    private void createStopVelocitiesMap() {
        stopVelocitiesMap.put(Input.Keys.UP, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.W, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.I, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.NUMPAD_8, new Vector2(velocity.x, 0));

        stopVelocitiesMap.put(Input.Keys.DOWN, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.S, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.K, new Vector2(velocity.x, 0));
        stopVelocitiesMap.put(Input.Keys.NUMPAD_5, new Vector2(velocity.x, 0));

        stopVelocitiesMap.put(Input.Keys.LEFT, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.A, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.J, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.NUMPAD_4, new Vector2(0, velocity.y));

        stopVelocitiesMap.put(Input.Keys.RIGHT, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.D, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.L, new Vector2(0, velocity.y));
        stopVelocitiesMap.put(Input.Keys.NUMPAD_6, new Vector2(0, velocity.y));
    }

    /**
     * Changes player velocity
     *
     * @param dir - direction to move
     */
    public void move(int dir) {
        createMoveVelocitiesMap();
        if (!stop) {
            velocity.set(moveVelocitiesMap.get(dir));
            b2body.setLinearVelocity(velocity);
        }
        if (invertWay) {
            velocity.set(-velocity.x, -velocity.y);
            b2body.setLinearVelocity(velocity);
        }
        if (hitBomb) {
            currentState = getState();
            stopMove();
            b2body.setLinearVelocity(velocity);

        }
    }

    /**
     * Stops player movement to a certain direction
     *
     * @param dir
     */
    public void stop(int dir) {
        createStopVelocitiesMap();
        velocity.set(stopVelocitiesMap.get(dir));
    }

    /**
     * Updates player
     *
     * @param dt
     */
    public void update(float dt) {

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt * (GAMESPEED + speedIncrease)));
        if (!bomberIsDead) {
            if (bomberToDie) {
                if (stateTimer >= 0.8f) {
                    bomberIsDead = true;
                    setRegion(cleanRegion);
                    world.destroyBody(b2body);
                }
            } else {
                if (stop) {
                    velocity.set(0, 0);
                }
                if (keepBombing) {
                    setPressedBombButton(false);
                    placeBomb();

                }

                if (badBonusActive) {
                    badBonus.apply(this);
                } else if (destroyBonus) {
                    badBonus.apply(this);
                    destroyBonus = false;
                }
                b2body.setLinearVelocity(velocity);
            }
        }
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public Body getBody(){
        return b2body;
    }
    /**
     * Gets animations frames
     *
     * @param dt
     * @return
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = animationFramesMap.get(currentState).get();

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    /**
     * Get player state
     *
     * @return state
     */
    private State getState() {
        if (bomberIsDead)
            return State.DEAD;
        else if (bomberToDie)
            return State.DYING;
        else if (b2body.getLinearVelocity().x > 0)
            return State.RUNNING_RIGHT;
        else if (b2body.getLinearVelocity().x < 0)
            return State.RUNNING_LEFT;
        else if (b2body.getLinearVelocity().y > 0)
            return State.RUNNING_UP;
        else if (b2body.getLinearVelocity().y < 0)
            return State.RUNNING_DOWN;


        return getStandingState();
    }

    private State getStandingState() {
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

    public void setPlacedBombs(int nPlacedBombs) {
        this.nPlacedBombs += nPlacedBombs;
    }

    public int getPlacedBombs() {
        return nPlacedBombs;
    }

    public float getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(float speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public boolean isBadBonusActive() {
        return badBonusActive;
    }

    public void setBadBonusActive(boolean active) {
        badBonusActive = active;
    }

    public void setDestroyBonus(boolean destroyBonus) {
        this.destroyBonus = destroyBonus;
    }

    public void setBadBonus(Bonus badBonus) {
        this.badBonus = badBonus;
    }

    public Bonus getBadBonus() {
        return badBonus;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isHitBomb() {
        return hitBomb;
    }

    public boolean isDontBomb() {
        return dontBomb;
    }

    public boolean isKeepBombing() {
        return keepBombing;
    }

    public void setKeepBombing(boolean keepBombing) {
        this.keepBombing = keepBombing;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setDontBomb(boolean dontBomb) {
        this.dontBomb = dontBomb;
    }

    public boolean isInvertWay() {
        return invertWay;
    }

    public void setInvertWay(boolean invertWay) {
        this.invertWay = invertWay;
    }

    public boolean isDistantExplode() {
        return distantExplode;
    }

    public void setDistantExplode(boolean distantExplode) {
        this.distantExplode = distantExplode;
    }

    public boolean isExplodeBombs() {
        return explodeBombs;
    }

    public void setExplodeBombs(boolean explodeBombs) {
        this.explodeBombs = explodeBombs;
    }

    public boolean isKickingBombs() {
        return kickingBombs;
    }

    public void setKickingBombs(boolean kickingBombs) {
        this.kickingBombs = kickingBombs;
    }

    public boolean isSendingBombs() {
        return sendingBombs;
    }

    public void setSendingBombs(boolean sendingBombs) {
        this.sendingBombs = sendingBombs;
    }

    public void setHitBomb(boolean hit) {
        hitBomb = hit;
    }

    public int getnLBombs() {
        return nLBombs;
    }

    public void setnLBombs(int nLBombs) {
        this.nLBombs += nLBombs;
    }

    public int getnNBombs() {
        return nNBombs;
    }

    public void setnNBombs(int nNBombs) {
        this.nNBombs += nNBombs;
    }

    public boolean isPressedBombButton() {
        return pressedBombButton;
    }

    public void setPressedBombButton(boolean pressedBombButton) {
        this.pressedBombButton = pressedBombButton;
    }

    public Vector2 getPosition() {
        return new Vector2(b2body.getPosition().x, b2body.getPosition().y);
    }

    public boolean isMoving() {
        if (currentState == State.RUNNING_LEFT || currentState == State.RUNNING_RIGHT || currentState == State.RUNNING_UP || currentState == State.RUNNING_DOWN)
            return true;
        return false;
    }

    /**
     * Get player orientation
     *
     * @return
     */
    public int getOrientation() {
        switch (currentState) {
            case RUNNING_UP:
            case STANDING_UP:
                return 0;
            case RUNNING_RIGHT:
            case STANDING_RIGHT:
                return 1;
            default:
            case RUNNING_DOWN:
            case STANDING_DOWN:
                return 2;
            case RUNNING_LEFT:
            case STANDING_LEFT:
                return 3;

        }
    }

    /**
     * Kick a bomb
     *
     * @param bomb
     */
    public void kick(Bomb bomb) {
        if (centerBody(b2body.getPosition().x) != centerBody(bomb.getBodyPositions().x) || centerBody(b2body.getPosition().y) != centerBody(bomb.getBodyPositions().y)) {
            setHitBomb(true);
            bombHitX = bomb.getBodyPositions().x;
            bombHitY = bomb.getBodyPositions().y;
            if (isKickingBombs() && isMoving())
                bomb.kick(getOrientation());
        }
    }

    /**
     * Stop movement
     */
    private void stopMove() {
        switch (getOrientation()) {
            case 0:
                if ((centerBody(b2body.getPosition().y) + getHeight()) == centerBody(bombHitY))
                    velocity.set(0, 0);
                break;
            case 1:
                if ((centerBody(b2body.getPosition().x) + getWidth()) == centerBody(bombHitX))
                    velocity.set(0, 0);
                break;
            case 2:
                if ((centerBody(b2body.getPosition().y) - getHeight()) == centerBody(bombHitY))
                    velocity.set(0, 0);
                break;
            case 3:
                if ((centerBody(b2body.getPosition().x) - getWidth()) == centerBody(bombHitX))
                    velocity.set(0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * Place a bomb
     */
    public void placeBomb() {
        if (freeSpot() && !dontBomb) {
            if (getnNBombs() > 0) {
                game.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x, b2body.getPosition().y),
                        NBomb.class));
                setnNBombs(-1);
                setPressedBombButton(true);
            } else if (getnLBombs() > 0) {
                game.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x, b2body.getPosition().y),
                        LBomb.class));
                setnLBombs(-1);
                setPressedBombButton(true);
            } else if (getPlacedBombs() < getBombs()) {
                game.spawnItem(new ItemDef(new Vector2(b2body.getPosition().x, b2body.getPosition().y),
                        ClassicBomb.class));
                setPlacedBombs(1);
                setPressedBombButton(true);
            }
        }


    }

    /**
     * Position free
     *
     * @return
     */
    private boolean freeSpot() {
        for (Item item : game.getItems()) {
            if (item instanceof Bomb) {
                if (centerBody(item.getX()) == centerBody(b2body.getPosition().x) && centerBody(item.getY()) == centerBody(b2body.getPosition().y))
                    return false;
            }

        }
        return true;
    }

    /**
     * Pause movement
     */
    public void pause() {
        b2body.setLinearVelocity(0, 0);
    }

    private float centerBody(float value) {
        int ret = (int) (value * Constants.PPM / 50);

        return ret * 50 / Constants.PPM;
    }

    public boolean isDead() {
        return bomberIsDead;
    }

    public boolean isDying() {
        return bomberToDie;
    }

    /**
     * Die, destroy body
     */
    public void die() {
        if (!isDead()) {
            Sound dieSound = gam.manager.get("sounds/playerDie.wav", Sound.class);
            dieSound.play();
            b2body.setLinearVelocity(0, 0);
            bomberToDie = true;
            Filter filter = new Filter();
            filter.maskBits = Constants.NOTHING_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        }

    }

}
