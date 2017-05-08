package com.lpoo.bombic.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Sprites.Players.Bomber;
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

            case Bombic.BOMBER_BIT | Bombic.CLASSIC_BOMB_BIT:
                Gdx.app.log("BOMBER", "CLASSIC_BOMB");
                break;
            case Bombic.BOMBER_BIT | Bombic.FLAMES_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.BOMBER_BIT)
                    ((Bomber) fixA.getUserData()).die();
                else
                    ((Bomber) fixB.getUserData()).die();
                break;
            case Bombic.BOMBER_BIT | Bombic.BONUS_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.BOMBER_BIT)
                    ((Bonus) fixB.getUserData()).apply((Bomber) fixA.getUserData());
                else
                    ((Bonus) fixA.getUserData()).apply((Bomber) fixB.getUserData());
                break;
            case Bombic.BOMBER_BIT | Bombic.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.BOMBER_BIT)
                    ((Bomber) fixA.getUserData()).die();
                else
                    ((Bomber) fixB.getUserData()).die();
                break;
            case Bombic.FLAMES_BIT | Bombic.BONUS_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.BONUS_BIT)
                    ((Bonus) fixA.getUserData()).destroy();
                else
                    ((Bonus) fixB.getUserData()).destroy();
                break;
            case Bombic.FLAMES_BIT | Bombic.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitByFlame();
                else
                    ((Enemy) fixB.getUserData()).hitByFlame();
                break;
            case Bombic.ENEMY_BIT | Bombic.CLASSIC_BOMB_BIT:
            case Bombic.ENEMY_BIT | Bombic.OBJECT_BIT:
            case Bombic.ENEMY_BIT | Bombic.DESTROYABLE_OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).hitObject();
                else
                    ((Enemy) fixB.getUserData()).hitObject();
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Bombic.FLAMES_BIT | Bombic.DESTROYABLE_OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Bombic.FLAMES_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).explode();
                else
                    ((InteractiveTileObject) fixA.getUserData()).explode();
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
