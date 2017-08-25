package com.kenji.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

/**
 * Created by Waffaru on 23.6.2017.
 */

public class Bird {


    private Body body;
    private TextureAtlas birdAtlas;
    private Animation animation;
    private float speed = 0;
    private float originX = 0;
    private float originY = 0;
    public static ArrayList<Bird> birdList = new ArrayList<Bird>();
    private float jerk = 0;
    private boolean jerkSwitch = false;



    public Bird(Body body) {

        birdAtlas = new TextureAtlas(Gdx.files.internal("bird.pack"));
        animation = new Animation(1/5f, birdAtlas.getRegions());

        body.getFixtureList().get(0).setSensor(true);
        this.body = body;
        this.originX = this.body.getPosition().x;
        this.originY = this.body.getPosition().y;


        if(this.body.getUserData().equals("left")) {
            this.speed = -0.4f;
        }
        else if(this.body.getUserData().equals("right")) {
            this.speed = 0.4f;
        }
    }


    //TODO: Implement warping back to starting point
    public void move() {
        if(jerk <= 0.0f) {
            jerkSwitch = true;
        }
        if(jerkSwitch) {
            jerk = jerk + 0.05f;
        }
        if(!jerkSwitch) {
            jerk = jerk - 0.05f;
        }

        if(jerk > 0.2f) {
            jerkSwitch = false;
        }
        if(this.getBody().getUserData().equals("left")) {
            this.getBody().setLinearVelocity(speed,0 + jerk);
            if(this.getBody().getPosition().x < 5.7f) {
                this.getBody().setTransform(originX,originY,this.getBody().getAngle());
            }
        }
        else if(this.getBody().getUserData().equals("right")) {
            this.getBody().setLinearVelocity(speed,0 + jerk);
            if(this.getBody().getPosition().x > 7.4f) {
                this.getBody().setTransform(originX,originY,this.getBody().getAngle());
            }
        }

    }


    public Animation<TextureRegion> animate() {
        //TODO: Run the animation
        return animation;
    }


    public Body getBody() {
        return body;
    }



}
