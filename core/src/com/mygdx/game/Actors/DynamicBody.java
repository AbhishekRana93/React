package com.mygdx.game.Actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MyGdxGame;

public class DynamicBody extends Actor{
    World world;
    Body dynamicBody;
    MyGdxGame game;
    Image crateImg;
    TextureRegion crateRegion;
    float crateWidth, crateHeight;
    float PIXELS_TO_METRES = 100f;


    public DynamicBody(World world, MyGdxGame game) {
        this.world = world;
        this.game = game;

        Texture crate = new Texture(Gdx.files.internal("crate.png"));
        crate.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        crateRegion = new TextureRegion(crate);
        crateWidth = crateRegion.getRegionWidth() / 2;
        crateHeight = crateRegion.getRegionHeight() / 2;


//        crateImg = new Image(crate);
//        crateImg.setSize(crateWidth, crateHeight);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set( (game.V_WIDTH/2) / PIXELS_TO_METRES, (game.V_HEIGHT/2) / PIXELS_TO_METRES );

        dynamicBody = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((crateWidth/2) / PIXELS_TO_METRES, (crateHeight/2) / PIXELS_TO_METRES);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        dynamicBody.createFixture(fixtureDef);

//        dynamicBody.applyTorque(3, true);
//        dynamicBody.setGravityScale(0);
        dynamicBody.setAngularVelocity(4);

        polygonShape.dispose();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw (
            crateRegion, dynamicBody.getPosition().x * PIXELS_TO_METRES - crateWidth/2,
            dynamicBody.getPosition().y * PIXELS_TO_METRES - crateHeight/2, crateWidth/2,
            crateHeight/2, crateWidth, crateHeight, 1, 1,
                dynamicBody.getAngle()*MathUtils.radiansToDegrees, false);
    }
}
