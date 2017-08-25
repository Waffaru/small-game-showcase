package com.kenji.game;

/**
 * Created by Waffaru on 21.6.2017.
 */


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Class used to create all the lights, cameras, and walls for our world.
 *
 * @author Gonzalo Ortiz, Teemu Ryhänen
 * @version 1.0
 * @since 2017-05-11
 */


public class CreateWorld {
    public static World world;
    public static TiledMap map;
    public static BodyDef myBodyDef;

    /**
     * Method used to build our world.
     *
     *
     * @param backBone the main-class of our game.
     */
    public static void buildWorld() {
        world = new World(new Vector2(0, 0), true);
        buildWalls();
        Constants.hander = new RayHandler(world);
        Constants.hander.setAmbientLight(0.8f);
    }

    /**
     * Wrapper method to build all bodies to the world.
     *
     * @param backBone the main-class of our game.
     */
    public static void buildWalls() {

        transformWallsToBodies("Walls", "walls");
        transformWallsToBodies("Door1", "Door1");
        transformWallsToBodies("door2", "door2");
        transformWallsToBodies("Door3", "Door3");
        transformWallsToBodies("Door4", "Door4");
        transformWallsToBodies("Door5", "Door5");
        transformWallsToBodies("Door6", "Door6");
        transformWallsToBodies("Door7", "Door7");
        transformWallsToBodies("Door8", "Door8");
        createTrees("Tree", "Tree");
        createTorches("Torch", "Torch");
        transformWallsToBodies("Chest", "Chest");
        createBirds("Bird Left", "left");
        createBirds("Bird Right", "right");
        createCandles("Candle", "Candle");
        transformLayersToLights("ConeLight Down");

    }

    /**
     *
     *
     * @param layer the layer we want to transform to bodies
     * @param userData userdata set to the bodies
     */


    public static void createCandles(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);
        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE * 5);

            createCandleBody(rectangle, userData);
        }
    }


    public static void createCandleBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);
        wall.setSleepingAllowed(true);
        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);
        wall.createFixture(groundBox, 0.0f);

        Candle.candles.add(new Candle(wall));
    }













    public static void createBirds(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);
        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE * 5);

            createBirdBody(rectangle, userData);
        }
    }

    public static void createBirdBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);
        wall.setSleepingAllowed(true);
        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);
        wall.createFixture(groundBox, 0.0f);

        Bird.birdList.add(new Bird(wall));

    }


    public static void createTorches(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);
        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE * 5);

            createTorchBody(rectangle, userData);
        }
    }


    public static void createTorchBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);
        wall.setSleepingAllowed(true);
        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);
        wall.createFixture(groundBox, 0.0f);

        Torch.torches.add(new Torch(wall));
    }




    public static void createTrees(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);
        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE * 5);

            createTreeBody(rectangle, userData);
        }
    }



    public static void createTreeBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);
        wall.setSleepingAllowed(true);
        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);
        wall.createFixture(groundBox, 0.0f);

        Tree.trees.add(new Tree(wall));
    }


    // END OF TREE

    public static void transformWallsToBodies(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);
        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE*5);

            createStaticBody(rectangle, userData);
        }





    }

    /**
     * turns rectangles into static bodies.
     *
     *
     * @param rect the rectangle we want to transform to a body
     * @param userData the userdata we want to set to the body
     */
    public static void createStaticBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);
        wall.setSleepingAllowed(true);
        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);
        wall.createFixture(groundBox, 0.0f);
    }

    /**
     * Method use to scale the rectangles to the proper size.
     *
     *
     * @param r the rectangle we wan to scale
     * @param scale the ammount we want to scale it by.
     * @return a Rectangle-type object.
     */
    private static Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x = r.x * scale;
        rectangle.y = r.y * scale;
        rectangle.width = r.width * scale;
        rectangle.height = r.height * scale;
        return rectangle;
    }

    /**
     * Method used to create cameras to the map
     *
     * @param layer the layer where we have cameras
     * @param userData userdata set to the cameras
     * @param backBone the main-class of our game
     */


    /**
     * Creates a kinetic body out of a rectangle
     *
     * @param rect the rectangle to make a body out of.
     * @param userData the userdata that's going to be set to the body
     * @return a Body-type object
     */
    public static Body createKineticBody(Rectangle rect, String userData) {
        myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.KinematicBody;
        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width / 2 + x;
        float centerY = height / 2 + y;

        myBodyDef.position.set(centerX, centerY);

        Body wall = world.createBody(myBodyDef);

        wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);

        wall.createFixture(groundBox, 0.0f);
        if(userData.equals("camera-upright")) {
            wall.setTransform(wall.getPosition().x, wall.getPosition().y, 0.8f);
        }
        else if(userData.equals("camera-upleft")) {
            wall.setTransform(wall.getPosition().x, wall.getPosition().y, 2.4f);
        }
        else if(userData.equals("camera-downright")) {
            wall.setTransform(wall.getPosition().x, wall.getPosition().y, -0.8f);
        }
        else if(userData.equals("camera-downleft")) {
            wall.setTransform(wall.getPosition().x, wall.getPosition().y, -2.4f);
        }

        return wall;
    }


    /**
     * Method to create lights directly form Tiled.
     *
     * @param layer the layer where our lights are
     */

    public static void transformLayersToLights(String layer) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = map.getLayers().get(layer);

        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, Constants.SCALE*5);

            createLights(rectangle);
        }
    }

    /**
     * method that creates a light out of a rectangle
     *
     * @param rect the rectangle we want to make into a light.
     */
    public static void createLights(Rectangle rect) {
        Constants.rect = rect;
    }
}
