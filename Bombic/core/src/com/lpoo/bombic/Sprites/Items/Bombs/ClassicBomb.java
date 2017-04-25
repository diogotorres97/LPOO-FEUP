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
        tickingAnimation = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();

        //Creating burning animation
        for(int i = 1 ; i < 4 ; i++)
            frames.add(new TextureRegion(screen.getAtlasFlames().findRegion("flames" + i),150, 0, 50, 50));
        burningAnimation = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();


    }

    @Override
    public void defineItem() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        //Create bomber shape
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(23 / Bombic.PPM);


        fdef.shape = shape;
        body.createFixture(fdef);

    }



    @Override
    public void update(float dt) {
        super.update(dt);
        setRegion(getFrame(dt * Bombic.GAME_SPEED));


        if(tickingStateTime >= 2f / Bombic.GAME_SPEED && tickingStateTime <= 4f / Bombic.GAME_SPEED){
            currentState = State.BURNING;
            setVisibleTileID(dt * Bombic.GAME_SPEED);
            fireUpTiles();
        }else if(tickingStateTime <= 2f / Bombic.GAME_SPEED){
            flashTiles();
            setVisibleTileID(dt * Bombic.GAME_SPEED * 128);
        }else{
            resetFreeTiles();
            dispose();

        }








    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){

            case BURNING:
                region = burningAnimation.getKeyFrame(tickingStateTime, true);
                break;
            case TICKING:
            default:
                region = tickingAnimation.getKeyFrame(tickingStateTime, true);
                break;
        }


        tickingStateTime += dt;

        //update previous state
        previousState = currentState;

        //return our final adjusted frame
        return region;
    }


}
