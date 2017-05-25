package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Sprites.Enemies.Enemy;
import com.lpoo.bombic.Sprites.Items.Bonus.Bonus;
import com.lpoo.bombic.Sprites.TileObjects.InteractiveTileObject;

/**
 * Created by Rui Quaresma on 22/04/2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){

            case Constants.BOMBER_BIT | Constants.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BOMB_BIT)
                    ((Player) fixB.getUserData()).kick((Bomb) fixA.getUserData());
                else
                    ((Player) fixA.getUserData()).kick((Bomb) fixB.getUserData());
                break;
            case Constants.BOMBER_BIT | Constants.FLAMES_BIT:
                /*if(fixA.getFilterData().categoryBits == Constants.BOMBER_BIT)
                    ((Player) fixA.getUserData()).die();
                else
                    ((Player) fixB.getUserData()).die();*/
                break;
            case Constants.BOMB_BIT | Constants.BOMB_BIT:
                if(((Bomb) fixA.getUserData()).isMovingBomb())
                    ((Bomb) fixA.getUserData()).stop();
                else
                    ((Bomb) fixB.getUserData()).stop();
                break;
            case Constants.BOMBER_BIT | Constants.BONUS_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BOMBER_BIT)
                    ((Bonus) fixB.getUserData()).apply((Player) fixA.getUserData());
                else
                    ((Bonus) fixA.getUserData()).apply((Player) fixB.getUserData());
                break;
            case Constants.BOMBER_BIT | Constants.ENEMY_BIT:
                /*if(fixA.getFilterData().categoryBits == Constants.BOMBER_BIT)
                    ((Player) fixA.getUserData()).die();
                else
                    ((Player) fixB.getUserData()).die();*/
                break;
            case Constants.FLAMES_BIT | Constants.BONUS_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BONUS_BIT)
                    ((Bonus) fixA.getUserData()).destroy();
                else
                    ((Bonus) fixB.getUserData()).destroy();
                break;
            case Constants.FLAMES_BIT | Constants.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitByFlame();
                else {
                    ((Enemy) fixB.getUserData()).hitByFlame();

                }
                break;
            case Constants.ENEMY_BIT | Constants.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitBomb();
                else
                    ((Enemy) fixB.getUserData()).hitBomb();
                break;
            case Constants.ENEMY_BIT | Constants.DESTROYABLE_OBJECT_BIT:
            case Constants.ENEMY_BIT | Constants.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitObject();
                else
                    ((Enemy) fixB.getUserData()).hitObject();
                break;
            case Constants.FLAMES_BIT | Constants.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BOMB_BIT)
                    ((Bomb) fixA.getUserData()).explode();
                else
                    ((Bomb) fixB.getUserData()).explode();
                break;
            case Constants.BOMB_BIT | Constants.DESTROYABLE_OBJECT_BIT:
            case Constants.BOMB_BIT | Constants.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BOMB_BIT)
                    ((Bomb) fixA.getUserData()).stop();
                else
                    ((Bomb) fixB.getUserData()).stop();
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Constants.FLAMES_BIT | Constants.DESTROYABLE_OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.FLAMES_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).explode();
                else
                    ((InteractiveTileObject) fixA.getUserData()).explode();
                break;
            case Constants.BOMBER_BIT | Constants.BOMB_BIT:
                if(fixA.getFilterData().categoryBits == Constants.BOMB_BIT)
                    ((Player) fixB.getUserData()).setHitBomb(false);
                else
                    ((Player) fixA.getUserData()).setHitBomb(false);
                break;
            case Constants.FLAMES_BIT | Constants.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).setUntouchable(false);
                else {
                    ((Enemy) fixB.getUserData()).setUntouchable(false);

                }
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
