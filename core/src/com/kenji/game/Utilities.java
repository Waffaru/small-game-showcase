package com.kenji.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Waffaru on 21.6.2017.
 */

public class Utilities {

    public static Body createCharBody(float x, float y) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        // estää hahmon pyörimästä kun osuu seiniin.
        myBodyDef.fixedRotation = true;

        myBodyDef.position.set(new Vector2(x, y));
        PolygonShape shape = new PolygonShape();

        // CASTATAAN FLOATIIN TOI TILE_WIDTH JA MUUNNETAAN SE METREIKSI, SITTEN JAETAAN KAHDELLA NIIN SAADAAN OIKEA LEVEYS.
        //TODO: Muuta nämä oikeaksi kooksi
        shape.setAsBox((float) Constants.TILE_WIDTH * Constants.SCALE, (float) (Constants.TILE_HEIGHT * Constants.SCALE) / 2);
        //bits detection test
        FixtureDef test = new FixtureDef();
        test.shape = shape;
        test.density = 5;

        //TODO: Change back to CreateWorld
        Body body = CreateWorld.world.createBody(myBodyDef);
        body.createFixture(test);

        body.resetMassData();
        shape.dispose();
        return body;




    }




}
