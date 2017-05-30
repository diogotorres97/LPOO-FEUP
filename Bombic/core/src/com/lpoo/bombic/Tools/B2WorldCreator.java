package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.EnemiesStrategy.AdvancedTrapStrategy;
import com.lpoo.bombic.EnemiesStrategy.ClouderStrategy;
import com.lpoo.bombic.EnemiesStrategy.GhostStrategy;
import com.lpoo.bombic.EnemiesStrategy.GreyBallStrategy;
import com.lpoo.bombic.EnemiesStrategy.MoonerStrategy;
import com.lpoo.bombic.EnemiesStrategy.RedBallStrategy;
import com.lpoo.bombic.EnemiesStrategy.SandmasterStrategy;
import com.lpoo.bombic.EnemiesStrategy.SlimerStrategy;
import com.lpoo.bombic.EnemiesStrategy.TrapStrategy;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Sprites.Enemies.AdvancedTrap;
import com.lpoo.bombic.Sprites.Enemies.Clouder;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Enemies.Ghost;
import com.lpoo.bombic.Sprites.Enemies.GreyBall;
import com.lpoo.bombic.Sprites.Enemies.Mooner;
import com.lpoo.bombic.Sprites.Enemies.RedBall;
import com.lpoo.bombic.Sprites.Enemies.Sandmaster;
import com.lpoo.bombic.Sprites.Enemies.Slimer;
import com.lpoo.bombic.Sprites.Enemies.Trap;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;

import java.util.Random;

/**
 * Creates the physics world, getting the objects from the map.
 */
public class B2WorldCreator {
    /**
     * Array with the created enemies
     */
    private Array<Enemy> enemies;
    /**
     * Number of bonus per type
     */
    private int[] numBonusType;
    /**
     * Number of bonus types
     */
    private int numTypesBonus;
    /**
     * Type of each bonus
     */
    private int[] typesBonus;
    /**
     * Number of explodable objects in the map
     */
    private int numExplodableObjects;
    /**
     * Total number of bonus
     */
    private int numBonusTotal;
    /**
     * Range for the random number generation
     */
    private int randRange;
    /**
     * Number of players
     */
    private int numPlayers;
    /**
     * Game map
     */
    private TiledMap map;
    /**
     * Number of enemies
     */
    private int numEnemies;
    /**
     * Game
     */
    private Game game;

    /**
     * Constructor
     *
     * @param game
     */
    public B2WorldCreator(Game game) {
        this.map = game.getMap();
        this.game = game;
        this.numPlayers = game.getNumPlayers();

        numEnemies = Integer.parseInt(map.getProperties().get("num_enemies").toString());

        numTypesBonus = Integer.parseInt(map.getProperties().get("num_types_bonus").toString());
        numExplodableObjects = Integer.parseInt(map.getProperties().get("num_explodable_items").toString());
        numBonusTotal = Integer.parseInt(map.getProperties().get("num_bonus_total").toString()) * (1 + numPlayers / 4);
        numBonusType = new int[numTypesBonus];
        typesBonus = new int[numTypesBonus];
        getBonusTypes(map);
        randRange = numTypesBonus * 2 + 1;

        createObjects();

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

            int bonus = generateBonus();
            new InteractiveTileObject(game, object, bonus);

        }

    }

    /**
     * Creates the static objects
     */
    private void createObjects() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);

            body = game.getWorld().createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Constants.PPM, rect.getHeight() / 2 / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.OBJECT_BIT;
            body.createFixture(fdef);
        }
    }

    /**
     * Sets number of types bonus
     *
     * @param numTypesBonus
     */
    public void setNumBonus(int numTypesBonus) {
        this.numTypesBonus = numTypesBonus;
    }

    /**
     * Start creation of the enemies
     */
    public void startEnemyCreation() {
        enemies = new Array<Enemy>();
        for (int i = 1; i < numEnemies + 1; i++)
            createEnemies(Integer.parseInt(map.getProperties().get("enemy" + i).toString()));
    }

    /**
     * Creates the enemies
     * @param enemieId
     */
    private void createEnemies(int enemieId) {
        switch (enemieId) {
            case 1:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new GreyBall(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new GreyBallStrategy());
                }
                break;
            case 2:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Slimer(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new SlimerStrategy());
                }
                break;
            case 3:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Mooner(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new MoonerStrategy());
                }
                break;
            case 4:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Ghost(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new GhostStrategy());
                }
                break;
            case 5:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Clouder(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new ClouderStrategy());
                }
                break;
            case 6:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Trap(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new TrapStrategy());
                }
                break;
            case 7:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new RedBall(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new RedBallStrategy());
                }
                break;
            case 8:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new AdvancedTrap(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new AdvancedTrapStrategy());
                }
                break;
            case 9:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new Sandmaster(game, (rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getWidth() / 2) / Constants.PPM));
                    enemies.get(enemies.size - 1).setStrategy(new SandmasterStrategy());
                }
                break;
            default:
                break;
        }

    }

    /**
     * Returns the array of enemies
     * @return
     */
    public Array<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Initializes both arrays
     * @param map
     */
    private void getBonusTypes(TiledMap map) {
        for (int i = 0; i < numTypesBonus; i++) {
            numBonusType[i] = Integer.parseInt(map.getProperties().get("num_bonus" + (i + 1)).toString()) * (1 + numPlayers / 4);
            typesBonus[i] = Integer.parseInt(map.getProperties().get("bonus" + (i + 1)).toString());
        }
    }

    /**
     * Generates the random bonus based on the types and number of bonus
     * @return
     */
    private int generateBonus() {
        Random rand = new Random();
        int randNum;
        int ret = 0;
        if (randRange > numTypesBonus && numExplodableObjects == numBonusTotal && randRange > 1) {
            randRange -= (numTypesBonus + 1);
        }
        if (randRange == 0)
            randRange++;
        randNum = rand.nextInt(randRange);

        if (randNum < numTypesBonus) {
            numBonusTotal--;
            if (numTypesBonus == numBonusType.length) {
                numBonusType[randNum]--;
                if (numBonusType[randNum] == 0) {
                    randRange--;
                    numTypesBonus--;
                }
                ret = typesBonus[randNum];
            } else {
                int firstPos = 0;
                boolean found = false;
                for (int i = 0; i < numBonusType.length; i++) {
                    if (numBonusType[i] != 0 && !found) {

                        if (firstPos == randNum) {
                            found = true;
                            numBonusType[i]--;
                            if (numBonusType[i] == 0) {
                                randRange--;
                                numTypesBonus--;
                            }
                            ret = typesBonus[i];
                        } else {
                            firstPos++;
                        }
                    }
                }
            }

        }
        numExplodableObjects--;
        return ret;
    }
}
