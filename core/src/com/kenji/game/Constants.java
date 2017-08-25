package com.kenji.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.RayHandler;

/**
 * Created by Waffaru on 21.6.2017.
 */

public class Constants {

    //Light

    public static RayHandler hander;

    //Scales

    //TODO:  Change the first one to the proper one
    public static final float SCALE = 1 / 1000f;
    public static Rectangle rect;


    public static final int APP_WIDTH = 1024;     //1024
    public static final int APP_HEIGHT = 576;    //576 ;
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;



    //Main character animation sheet and rows and cols
    public static final int MAINCHAR_COLS = 4;
    public static final int MAINCHAR_ROWS = 4;


    //Spawn point for the player.
    public static final float MAINCHAR_SPAWN_X = 0f;
    public static final float MAINCHAR_SPAWN_Y = 0f;
    public static ArrayList<Vector2> warpPoints;


    public static Vector2 moveTo = new Vector2();
    public static boolean movePlayer = false;


}
