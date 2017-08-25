package com.kenji.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

/**
 * Created by Waffaru on 23.6.2017.
 */

public class Torch {

    private TextureAtlas torchAtlas;
    private Animation<TextureRegion> animation;
    private box2dLight.PointLight light;



    private Body body;
    public static ArrayList<Torch> torches = new ArrayList<Torch>();


    public Torch(Body body) {
        torchAtlas = new TextureAtlas(Gdx.files.internal("torch.atlas"));
        animation = new Animation(1/4f, torchAtlas.getRegions());
        this.body = body;

    }


    public Animation<TextureRegion> animate() {
        //TODO: Run the animation
        return animation;
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
