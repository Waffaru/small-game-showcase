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

public class Tree {

    private TextureAtlas treeAtlas;
    private Animation<TextureRegion> animation;



    private Body body;
    public static ArrayList<Tree> trees = new ArrayList<Tree>();


    public Tree(Body body) {
        treeAtlas = new TextureAtlas(Gdx.files.internal("tree.atlas"));
        animation = new Animation(1/2f, treeAtlas.getRegions());
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
