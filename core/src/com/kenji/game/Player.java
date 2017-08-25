package com.kenji.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.utils.random.ConstantDoubleDistribution;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Waffaru on 21.6.2017.
 */

public class Player {

    private TextureAtlas ylos, oikea, alas, vasen, ylosStop, oikeaStop, alasStop, vasenStop, armorUp, armorRight, armorDown, armorLeft, armorUpStop, armorRightStop, armorDownStop, armorLeftStop;
    private Animation<TextureRegion> ylosAnimation, oikeaAnimation, alasAnimation, vasenAnimation, ylosStopAnimation, oikeaStopAnimation, alasStopAnimation, vasenStopAnimation, defaultAnimation;
    private Animation<TextureRegion> armorUpAnimation, armorRightAnimation, armorDownAnimation, armorLeftAnimation, armorUpStopAnimation, armorRightStopAnimation, armorDownStopAnimation, armorLeftStopAnimation;


    private Body playerBody;
    public float speed = 0.4f;

    public static int direction = 0;
    public static boolean isKeyPressed = false;
    public static boolean isArmorOn = false;
    public static boolean chestTouching = false;
    int lastAnimation = 0;


    // 1 left 2 right 3 up 4 down
    public static String dir = "";

    public Player(float x, float y) {
        playerBody = Utilities.createCharBody(x, y);
        playerBody.setUserData("player");

        ylos = new TextureAtlas(Gdx.files.internal("ylos.atlas"));
        oikea = new TextureAtlas(Gdx.files.internal("oikea.atlas"));
        alas = new TextureAtlas(Gdx.files.internal("alas.atlas"));
        vasen = new TextureAtlas(Gdx.files.internal("vasen.atlas"));
        ylosStop = new TextureAtlas(Gdx.files.internal("ylosStop.pack"));
        oikeaStop = new TextureAtlas(Gdx.files.internal("oikeaStop.pack"));
        alasStop = new TextureAtlas(Gdx.files.internal("alasStop.pack"));
        vasenStop = new TextureAtlas(Gdx.files.internal("vasenStop.pack"));

        armorUp = new TextureAtlas(Gdx.files.internal("armorUp.pack"));
        armorRight = new TextureAtlas(Gdx.files.internal("armorRight.pack"));
        armorDown = new TextureAtlas(Gdx.files.internal("armorDown.pack"));
        armorLeft = new TextureAtlas(Gdx.files.internal("armorLeft.pack"));
        armorUpStop = new TextureAtlas(Gdx.files.internal("armorUpStop.pack"));
        armorRightStop = new TextureAtlas(Gdx.files.internal("armorRightStop.pack"));
        armorDownStop = new TextureAtlas(Gdx.files.internal("armorDownStop.pack"));
        armorLeftStop = new TextureAtlas(Gdx.files.internal("armorLeftStop.pack"));




        ylosAnimation = new Animation(1/5f, ylos.getRegions());
        oikeaAnimation = new Animation(1/5f, oikea.getRegions());
        alasAnimation = new Animation(1/5f, alas.getRegions());
        vasenAnimation = new Animation(1/5f, vasen.getRegions());
        ylosStopAnimation = new Animation(1/5f, ylosStop.getRegions());
        oikeaStopAnimation = new Animation(1/5f, oikeaStop.getRegions());
        alasStopAnimation =  new Animation(1/5f, alasStop.getRegions());
        vasenStopAnimation = new Animation(1/5f, vasenStop.getRegions());

        armorUpAnimation = new Animation(1/5f, armorUp.getRegions());
        armorRightAnimation = new Animation(1/5f, armorRight.getRegions());
        armorDownAnimation = new Animation(1/5f, armorDown.getRegions());
        armorLeftAnimation = new Animation(1/5f, armorLeft.getRegions());
        armorUpStopAnimation = new Animation(1/5f, armorUpStop.getRegions());
        armorRightStopAnimation = new Animation(1/5f, armorRightStop.getRegions());
        armorDownStopAnimation = new Animation(1/5f, armorDownStop.getRegions());
        armorLeftStopAnimation = new Animation(1/5f, armorLeftStop.getRegions());

        // 0 = ylös, 1 = oikea 2 = alas 3 = vasen


    }


    public Body getPlayerBody() {
        return playerBody;
    }

    public void setPlayerBody(Body playerBody) {
        this.playerBody = playerBody;
    }




    // Naming the directions of movement.

    static int left = 1;
    static int right = 2;
    static int up = 3;
    static int down = 4;

    public void movement() {

        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            direction = left;
            isKeyPressed = true;
            move(left);

        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            direction = right;
            isKeyPressed = true;
            move(right);

        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            direction = up;
            isKeyPressed = true;
            move(up);

        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            direction = down;
            isKeyPressed = true;
            move(down);
        }

            else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println(this.getPlayerBody().getPosition().x + " " + this.getPlayerBody().getPosition().y);
        }
else {
        isKeyPressed = false;
        direction = 0;
        move(direction);
    }

    }


    public void move(int movementDir) {

        switch (movementDir) {
            case 0:
                dir = "";
                isKeyPressed = false;
                this.playerBody.setLinearVelocity(0,0);
                break;
            //LEFT
            case 1:
                lastAnimation = 3;
                this.playerBody.setLinearVelocity(-this.speed, 0);
                dir = "left";

                break;


            //RIGHT
            case 2:
                lastAnimation = 1;
                this.playerBody.setLinearVelocity(this.speed, 0);
                dir = "right";
                break;
            //UP
            case 3:
                lastAnimation = 0;
                this.playerBody.setLinearVelocity(0f, this.speed);
                dir = "up";
                break;
            //DOWN
            case 4:
                lastAnimation = 2;
                dir = "down";
                this.playerBody.setLinearVelocity(0f, -this.speed);

                break;


        }


    }


    public Animation<TextureRegion> animate() {

        if(dir.equals("left") && isKeyPressed == true) {
            if(!isArmorOn) {
                defaultAnimation = vasenStopAnimation;
                return vasenAnimation;
            }
            else if(isArmorOn) {
                defaultAnimation = armorLeftStopAnimation;
                return armorLeftAnimation;
            }

        }
        else if(dir.equals("right")  && isKeyPressed == true) {
            if(!isArmorOn) {
                defaultAnimation = oikeaStopAnimation;
                return oikeaAnimation;
            }
            else if(isArmorOn) {
                defaultAnimation = armorRightStopAnimation;
                return armorRightAnimation;
            }

        }
        else if(dir.equals("up")  && isKeyPressed == true) {
            if(!isArmorOn){
                defaultAnimation = ylosStopAnimation;
                return ylosAnimation;
            }
            else if(isArmorOn){
                defaultAnimation = armorUpStopAnimation;
                return armorUpAnimation;
            }
        }
        else if(dir.equals("down")  && isKeyPressed == true){
            if(!isArmorOn) {
                defaultAnimation = alasStopAnimation;
                return alasAnimation;
            }
            else if(isArmorOn) {
                defaultAnimation = armorDownStopAnimation;
                return armorDownAnimation;
            }
        }

        else if(isKeyPressed == false) {
            if(lastAnimation == 0) {
                if(!isArmorOn) {
                    defaultAnimation = ylosStopAnimation;
                    return ylosStopAnimation;
                }
                else if(isArmorOn) {
                    defaultAnimation = armorUpStopAnimation;
                    return armorUpStopAnimation;
                }
            }
            else if(lastAnimation == 1) {
                if(!isArmorOn) {
                    defaultAnimation = oikeaStopAnimation;
                    return oikeaStopAnimation;
                }
                else if(isArmorOn) {
                    defaultAnimation = armorRightStopAnimation;
                    return armorRightStopAnimation;
                }
            }
            else if(lastAnimation == 2) {
                if(!isArmorOn) {
                    defaultAnimation = alasStopAnimation;
                    return alasStopAnimation;
                }
                else if(isArmorOn) {
                    defaultAnimation = armorDownStopAnimation;
                    return armorDownStopAnimation;
                }
            }
            else if(lastAnimation == 3) {
                if(!isArmorOn) {
                    defaultAnimation = vasenStopAnimation;
                    return vasenStopAnimation;
                }
                else if(isArmorOn) {
                    defaultAnimation = armorLeftStopAnimation;
                    return armorLeftStopAnimation;
                }
            }
        }
        return defaultAnimation;
    }



    public void checkCollision() {
        CreateWorld.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door1") || (contact.getFixtureB().getBody().getUserData() == "Door1"
                        && contact.getFixtureA().getBody().getUserData() == "player")) {
                    Constants.moveTo = Constants.warpPoints.get(1);
                    Constants.movePlayer = true;
                    Constants.hander.setAmbientLight(1);

                }



                    if ( (contact.getFixtureB().getBody().getUserData() == "player"
                            && contact.getFixtureA().getBody().getUserData() == "Chest") || (contact.getFixtureB().getBody().getUserData() == "Chest"
                            && contact.getFixtureA().getBody().getUserData() == "player") ) {
                        chestTouching = true;


                }


                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "door2") || (contact.getFixtureB().getBody().getUserData() == "door2"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(0);
                    Constants.movePlayer = true;
                    Constants.hander.setAmbientLight(0.8f);
                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door3") || (contact.getFixtureB().getBody().getUserData() == "Door3"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(3);
                    Constants.movePlayer = true;

                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door4") || (contact.getFixtureB().getBody().getUserData() == "Door4"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(2);
                    Constants.movePlayer = true;

                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door5") || (contact.getFixtureB().getBody().getUserData() == "Door5"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(5);
                    Constants.movePlayer = true;
                    Constants.hander.setAmbientLight(0.7f);
                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door6") || (contact.getFixtureB().getBody().getUserData() == "Door6"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(4);
                    Constants.movePlayer = true;
                    Constants.hander.setAmbientLight(1f);

                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door7") || (contact.getFixtureB().getBody().getUserData() == "Door7"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(7);
                    Constants.movePlayer = true;

                }

                if ((contact.getFixtureB().getBody().getUserData() == "player"
                        && contact.getFixtureA().getBody().getUserData() == "Door8") || (contact.getFixtureB().getBody().getUserData() == "Door8"
                        && contact.getFixtureA().getBody().getUserData() == "player") ) {
                    Constants.moveTo = Constants.warpPoints.get(6);
                    Constants.movePlayer = true;

                }



            }


            @Override
            public void endContact(Contact contact) {
                chestTouching = false;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                System.out.println(chestTouching);
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && chestTouching == true) {
                    isArmorOn = true;
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }



}

