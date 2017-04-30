package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Bombic;
import com.lpoo.bombic.Screens.PlayScreen;
import com.lpoo.bombic.Sprites.Bomber;
import com.lpoo.bombic.Sprites.Items.Bombs.Bomb;
import com.lpoo.bombic.Sprites.Items.ItemDef;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class ClassicBomb extends Bomb {
    public ClassicBomb(PlayScreen screen, float x, float y, Bomber bomber) {
        super(screen, x, y, bomber);
        currentState = previousState = State.TICKING;
        fixture.setUserData(this);
    }


    @Override
    public void createAnimations(Bomber bomber) {

        //Creation of burning tiles animations
        for(int i = 0 ; i < burningAnimationTiles.length ; i++){
            for(int j = 0; j < 3 ; j++){
                burningAnimationTiles[i][j] = j * 10 + 1 + i;
            }
        }

        //Creation of preview tiles animation

        for(int i = 0 ; i < 3 ; i++)
            previewAnimationTiles[i] = (i + 1) * 10 - 2;



        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Creating ticking animation
        for(int i = 0 ; i < 7 ; i++)
            frames.add(new TextureRegion(screen.getAtlasBombs().findRegion("classicBomb"),i*50, 0, 50, 50));
        tickingAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

    }


    @Override
    public void update(float dt) {
        super.update(dt);




        if(tickingStateTime >= 2f / Bombic.GAME_SPEED && tickingStateTime <= 4f / Bombic.GAME_SPEED){


            setRegion(cleanRegion);
            setPosition(body.getPosition().x - getWidth() / 2 -  getWidth() * ((explodableTiles[1] != 0 ? explodableTiles[1] : 0) + (explodableTiles[3] != 0 ? explodableTiles[3] : 0)),
                    body.getPosition().y - getHeight() / 2 -  getHeight() * ((explodableTiles[0] != 0 ? explodableTiles[0] : 0) + (explodableTiles[2] != 0 ? explodableTiles[2] : 0)));
            if(!redefinedBomb){
                setCategoryFilter(Bombic.FLAMES_BIT);
                redefineBomb();
            }
            currentState = State.BURNING;
            setVisibleTileID(dt * Bombic.GAME_SPEED);
            fireUpTiles();
        }else if(tickingStateTime <= 2f / Bombic.GAME_SPEED){

            if(!contactableBomb){

                if(bomber.getX() > getX() + getWidth() || bomber.getX() + bomber.getWidth() < getX() || bomber.getY() - bomber.getHeight()> getY() || bomber.getY() + bomber.getHeight() < getY())
                {
                    setCategoryFilter(Bombic.CLASSIC_BOMB_BIT);
                    contactableBomb = true;
                }

            }

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt * Bombic.GAME_SPEED));
            flashTiles();
            setVisibleTileID(dt * Bombic.GAME_SPEED * 128);
        }else{
            resetFreeTiles();
            if(!toDestroy)
                destroy();

        }


        tickingStateTime += dt;








    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case TICKING:
            default:
                region = tickingAnimation.getKeyFrame(tickingStateTime, true);
                break;
        }




        //update previous state
        previousState = currentState;

        //return our final adjusted frame
        return region;
    }


}
