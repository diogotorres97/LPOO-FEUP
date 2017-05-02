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
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Enemies.GreyBall;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;

import java.util.Random;

/**
 * Created by Rui Quaresma on 18/04/2017.
 */

public class B2WorldCreator {

    private Array<Enemy> enemies;


    private int[] numBonusType;
    private int numTypesBonus;
    private int[] typesBonus;
    private int numExplodableObjects;
    private int numBonusTotal;
    private int randRange;

    private int numPlayers;

    private PlayScreen screen;
    private TiledMap map;


    public B2WorldCreator(PlayScreen screen) {
        this.screen = screen;
        World world = screen.getWorld();
        map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        int numEnemies = Integer.parseInt(map.getProperties().get("num_enemies").toString());

        numPlayers = screen.getNumPlayers();

        numTypesBonus = Integer.parseInt(map.getProperties().get("num_types_bonus").toString());
        numExplodableObjects = Integer.parseInt(map.getProperties().get("num_explodable_items").toString());
        numBonusTotal = Integer.parseInt(map.getProperties().get("num_bonus_total").toString()) * (1 + numPlayers / 4);
        numBonusType = new int[numTypesBonus];
        typesBonus = new int[numTypesBonus];
        getBonusTypes(screen.getMap());
        randRange = numTypesBonus + 4;
        //create bushes/rocks bodies/fixtures
        //that map.getLayers().get(2) ----> the index comes frome the tiled app, counting from bottom to top
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Bombic.PPM, (rect.getY() + rect.getHeight() / 2) / Bombic.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Bombic.PPM, rect.getHeight() / 2 / Bombic.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Bombic.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create barrels bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

            int bonus = generateBonus();
            new InteractiveTileObject(screen, object, bonus);

        }

        //create all enemies
        enemies = new Array<Enemy>();
        for (int i = 1; i < numEnemies + 1; i++) {
            createEnemies(Integer.parseInt(map.getProperties().get("enemy" + i).toString()));

        }

    }

    private void createEnemies(int enemieId) {
        switch (enemieId) {
            case 1:
                for (MapObject object : map.getLayers().get(enemieId + 3).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    enemies.add(new GreyBall(screen, (rect.getX() + rect.getWidth() / 2) / Bombic.PPM, (rect.getY() + rect.getWidth() / 2) / Bombic.PPM));
                }
                break;
            default:
                break;
        }

    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }

    private void getBonusTypes(TiledMap map) {
        for (int i = 0; i < numTypesBonus; i++) {
            numBonusType[i] = Integer.parseInt(map.getProperties().get("num_bonus" + (i + 1)).toString()) * (1 + numPlayers / 4);
            typesBonus[i] = Integer.parseInt(map.getProperties().get("bonus" + (i + 1)).toString());
        }
    }

    private int generateBonus() {
        Random rand = new Random();
        int randNum;
        int ret = 0;
        if (randRange > numTypesBonus && numExplodableObjects == numBonusTotal && randRange > 1) {
            randRange -= 4;
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
        /*Gdx.app.log("Bombs", "" + numBonusType[0] );
        Gdx.app.log("Flames", "" + numBonusType[1] );
        Gdx.app.log("Speed", "" + numBonusType[2] );
        Gdx.app.log("RANGE", "" + randRange);
        Gdx.app.log("RET", "" + ret);
        Gdx.app.log("              ", "                ");*/
        numExplodableObjects--;
        return ret;
    }
}
