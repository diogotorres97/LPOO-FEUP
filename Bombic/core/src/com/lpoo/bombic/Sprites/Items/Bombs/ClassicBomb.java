package com.lpoo.bombic.Sprites.Items.Bombs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.lpoo.bombic.Game;
import com.lpoo.bombic.Sprites.Players.Player;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class ClassicBomb extends Bomb {
    public ClassicBomb(Game game, float x, float y, Player player) {
        super(game, x, y, player);
        currentState = previousState = State.TICKING;
        fixture.setUserData(this);
        setCategoryFilter(Constants.CLASSIC_BOMB_BIT);
        cleanRegion = new TextureRegion(atlasBombs.findRegion("classicBomb"), 16 * 50, 0, 50, 50);
    }


    @Override
    public void createAnimations(Player player) {

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
            frames.add(new TextureRegion(atlasBombs.findRegion("classicBomb"),i*50, 0, 50, 50));
        tickingAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

    }


    @Override
    public void update(float dt) {
        super.update(dt);

        if(tickingStateTime >= 3f / game.getGameSpeed() && tickingStateTime <= 4.5f / game.getGameSpeed()){


            setRegion(cleanRegion);
            setPosition(body.getPosition().x - getWidth() / 2 -  getWidth() * ((explodableTiles[1] != 0 ? explodableTiles[1] : 0) + (explodableTiles[3] != 0 ? explodableTiles[3] : 0)),
                    body.getPosition().y - getHeight() / 2 -  getHeight() * ((explodableTiles[0] != 0 ? explodableTiles[0] : 0) + (explodableTiles[2] != 0 ? explodableTiles[2] : 0)));
            if(!redefinedBomb){
                redefineBomb();
            }
            currentState = State.BURNING;
            setVisibleTileID(dt * game.getGameSpeed());
            fireUpTiles();
        }else if(tickingStateTime <= 3f / game.getGameSpeed()){

            if(!contactableBomb){

                if(player.getX() > getX() + getWidth() || player.getX() + player.getWidth() < getX() || player.getY() - player.getHeight()> getY() || player.getY() + player.getHeight() < getY())
                {
                    fixture.setSensor(false);
                    contactableBomb = true;
                }

            }

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(dt * game.getGameSpeed()));
            flashTiles();
            setVisibleTileID(dt * game.getGameSpeed() * 128);
        }else{

            if(!toDestroy){
                resetFreeTiles();
                destroy();
            }



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
