package com.lpoo.bombic.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lpoo.bombic.Logic.Game;
import com.lpoo.bombic.Tools.Constants;

/**
 * Created by Rui Quaresma on 21/04/2017.
 */

public class Slimer2 extends Enemy implements Steerable<Vector2> {
    Body body;
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcellaration;
    float maxAngularSpeed, maxAngularAcellaration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;

    public Slimer2(Game game, float x, float y){
        super(game,x,y);
        body=this.b2body;
        tagged= false;
        boundingRadius=22 / Constants.PPM;
        maxLinearSpeed= this.speed;
        maxLinearAcellaration=5000;
        maxAngularSpeed=30;
        maxAngularAcellaration=5;

        this.steeringOutput= new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    @Override
    public void update(float dt) {
        if(behavior!=null){
            behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }
    }

    private void applySteering(float dt){
        boolean anyAccelarations = false;

        if(!steeringOutput.linear.isZero()){
            Gdx.app.log("a", "b");
            Vector2 force = steeringOutput.linear.scl(dt);
            body.applyForceToCenter(force,true);
            anyAccelarations=true;
        }

        if(anyAccelarations){

            Vector2 velocity=body.getLinearVelocity();
            float currentSpeedSquare=velocity.len2();
            if(currentSpeedSquare>=maxLinearSpeed*maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed/(float)Math.sqrt(currentSpeedSquare)));
            }

        }

    }

    @Override
    public void hitByFlame() {

    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed=maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcellaration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxAngularAcellaration=maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed=maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcellaration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcellaration=maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public Body getBody(){
        return body;
    }
    public void setBehavior(SteeringBehavior<Vector2>behavior){
        this.behavior=behavior;
    }
    public SteeringBehavior getBehavior(){
        return behavior;
    }
}
