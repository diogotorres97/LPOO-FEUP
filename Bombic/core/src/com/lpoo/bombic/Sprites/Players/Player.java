package com.lpoo.bombic.Sprites.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Items.Bombs.ClassicBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.LBomb;
import com.lpoo.bombic.Sprites.Items.Bombs.NBomb;
import com.lpoo.bombic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Sprites.Items.Item;
import com.lpoo.bombic.Sprites.Items.ItemDef;
import com.lpoo.bombic.Tools.Constants;

import static com.lpoo.bombic.Bombic.gam;

/**
 * Created by Rui Quaresma on 17/04/2017.
 */

public class Player extends Sprite {
    private enum State {RUNNING_LEFT, RUNNING_RIGHT, RUNNING_UP, RUNNING_DOWN, STANDING_RIGHT, STANDING_LEFT, STANDING_UP, STANDING_DOWN, DYING, DEAD}

    private State currentState;
    private State previousState;
    private World world;
    private Body b2body;

    private Game game;

    private TextureAtlas atlasBomber;

    private Array<TextureRegion> bomberStand;
    private TextureRegion cleanRegion;
    private Animation<TextureRegion> bomberRunUp;
    private Animation<TextureRegion> bomberRunDown;
    private Animation<TextureRegion> bomberRunLeft;
    private Animation<TextureRegion> bomberRunRight;
    private Animation<TextureRegion> bomberDying;
    private float stateTimer;

    private int bonus;
    private int nFlames;
    private int nBombs, nPlacedBombs, nLBombs, nNBombs;
    private float speedIncrease;
    private boolean bomberIsDead;
    private boolean bomberToDie;

    private boolean stop;
    private boolean dontBomb;
    private boolean keepBombing;
    private boolean invertWay;
    private boolean distantExplode;
    private boolean explodeBombs;
    private boolean kickingBombs;
    private boolean sendingBombs;

    private boolean pressedBombButton;

    private boolean hitBomb;

    private float bombHitX, bombHitY;

    private int id;

    private Vector2 velocity;
    private Vector2 pos;

    private boolean badBonusActive, destroyBonus;
    private Bonus badBonus;

    public Player(Game game, int id, Vector2 pos) {
        this.id = id;
        this.world = game.getWorld();
        this.game = game;
        this.pos = pos;

        atlasBomber = gam.manager.get("player.atlas");

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
        bonus = 0;

        createAnimations();

        defineBomber();

        setBounds(0, 0, 50 / Constants.PPM, 50 / Constants.PPM);
        setRegion(bomberStand.get(0));
    }

    private void createAnimations() {
        createRunDownAnim();
        createRunUpAnim();
        createRunRightAnim();
        createRunLeftAnim();
        createDyingAnim();

        createStandingAnim();
    }

    private void createRunDownAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), i * 50, 0, 50, 50));
        bomberRunDown = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunUpAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_up"), i * 50, 0, 50, 50));
        bomberRunUp = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunRightAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_right"), i * 50, 0, 50, 50));
        bomberRunRight = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createRunLeftAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_left"), i * 50, 0, 50, 50));
        bomberRunLeft = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createDyingAnim() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_dying"), i * 50, 0, 50, 50));
        bomberDying = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }

    private void createStandingAnim() {
        bomberStand = new Array<TextureRegion>();

        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_up"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_left"), 0, 0, 50, 50));
        bomberStand.add(new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_right"), 0, 0, 50, 50));

        cleanRegion = new TextureRegion(atlasBomber.findRegion("player" + (getId() - 1) + "_down"), 0, 300, 50, 50);
    }

    public Game getGame() {
        return game;
    }

    public int getId() {
        return id;
    }


    public void defineBomber() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(pos.x / Constants.PPM, pos.y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Create player shape
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


        velocity = new Vector2(0, 0);
    }

    public void move(int dir) {
        if (!stop) {
            switch (dir) {
                case Input.Keys.UP:
                case Input.Keys.W:
                case Input.Keys.I:
                case Input.Keys.NUMPAD_8:
                    velocity.set(0, game.getGameSpeed() + speedIncrease);
                    break;
                case Input.Keys.DOWN:
                case Input.Keys.S:
                case Input.Keys.K:
                case Input.Keys.NUMPAD_5:
                    velocity.set(0, -game.getGameSpeed() - speedIncrease);
                    break;
                case Input.Keys.LEFT:
                case Input.Keys.A:
                case Input.Keys.J:
                case Input.Keys.NUMPAD_4:
                    velocity.set(-game.getGameSpeed() - speedIncrease, 0);
                    break;
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                case Input.Keys.L:
                case Input.Keys.NUMPAD_6:
                    velocity.set(game.getGameSpeed() + speedIncrease, 0);
                    break;
                default:
                    break;
            }
            b2body.setLinearVelocity(velocity);
        }
        if (invertWay) {
            velocity.set(-velocity.x, -velocity.y);
            b2body.setLinearVelocity(velocity);
        }

        if (hitBomb) {
            currentState = getState();
            allowdToMove();
            b2body.setLinearVelocity(velocity);

        }

    }


    public void stop(int dir) {
        switch (dir) {
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.I:
            case Input.Keys.NUMPAD_8:
                velocity.set(velocity.x, 0);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
            case Input.Keys.K:
            case Input.Keys.NUMPAD_5:
                velocity.set(velocity.x, 0);
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
            case Input.Keys.J:
            case Input.Keys.NUMPAD_4:
                velocity.set(0, velocity.y);
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
            case Input.Keys.L:
            case Input.Keys.NUMPAD_6:
                velocity.set(0, velocity.y);
                break;
            default:
                break;

        }
    }

    public void update(float dt) {

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt * (game.getGameSpeed() + speedIncrease)));
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

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {

            case RUNNING_LEFT:
                region = bomberRunLeft.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_RIGHT:
                region = bomberRunRight.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_UP:
                region = bomberRunUp.getKeyFrame(stateTimer, true);
                break;
            case RUNNING_DOWN:
                region = bomberRunDown.getKeyFrame(stateTimer, true);
                break;
            case DYING:
                region = bomberDying.getKeyFrame(stateTimer, true);
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

    public State getState() {
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

    public void kick(Bomb bomb) {
        if (centerBody(b2body.getPosition().x) != centerBody(bomb.getBodyPositions().x) || centerBody(b2body.getPosition().y) != centerBody(bomb.getBodyPositions().y)) {
            setHitBomb(true);
            bombHitX = bomb.getBodyPositions().x;
            bombHitY = bomb.getBodyPositions().y;
            if (isKickingBombs() && isMoving())
                bomb.kick(getOrientation());

        }

    }

    private void allowdToMove() {
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
                if ((centerBody(b2body.getPosition().y) - getHeight()) == centerBody(bombHitY)) {
                    velocity.set(0, 0);

                }
                break;
            case 3:
                if ((centerBody(b2body.getPosition().x) - getWidth()) == centerBody(bombHitX))
                    velocity.set(0, 0);
                break;
            default:
                break;
        }
    }


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

    private boolean freeSpot() {
        for (Item item : game.getItems()) {
            if (item instanceof Bomb) {
                if (centerBody(item.getX()) == centerBody(b2body.getPosition().x) && centerBody(item.getY()) == centerBody(b2body.getPosition().y))
                    return false;
            }

        }
        return true;
    }

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

    public void die() {
        if (!isDead()) {
            b2body.setLinearVelocity(0, 0);
            bomberToDie = true;
            Filter filter = new Filter();
            filter.maskBits = Constants.NOTHING_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);
        }

    }

}
