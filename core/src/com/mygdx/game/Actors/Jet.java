package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public class Jet extends Actor{

    private World world;
    public Body jetBody, rotationBody;
    public RevoluteJointDef revoluteJointDef;
    private MyGdxGame game;
    private Texture plane;
    private Animation<TextureRegion> jet;
    private float planeWidth, planeHeight, stateTime = 0f, PIXELS_TO_METRES = 100, torque = 0f;
    private Bullet bullet;
    public ArrayList<Bullet> bulletList;
    private ArrayList<Bullet> removeBulletList;
    private Camera camera;
    public boolean startRotation = false;

    public Jet(final World world, final MyGdxGame game,final Camera camera) {
        this.world = world;
        this.game = game;
        this.camera = camera;

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("plane.atlas"));
        jet = new Animation<TextureRegion>(0.01f, textureAtlas.findRegions("Fly"), Animation.PlayMode.LOOP);

        planeWidth = 100f;
        planeHeight = 80f;

        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((game.V_WIDTH/2) / PIXELS_TO_METRES, (game.V_HEIGHT/2) / PIXELS_TO_METRES);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((planeWidth/2)/PIXELS_TO_METRES , (planeHeight/2)/PIXELS_TO_METRES);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        jetBody = world.createBody(bodyDef);
        jetBody.createFixture(fixtureDef);
        jetBody.setGravityScale(0f);
//        jetBody.setFixedRotation(true);

//        BodyDef rotationBodyDef = new BodyDef();
//        rotationBodyDef.position.set(jetBody.getPosition().x - 1f, jetBody.getPosition().y);
//        rotationBodyDef.type = BodyDef.BodyType.StaticBody;
//        rotationBody = world.createBody(rotationBodyDef);
//        rotationBody.setFixedRotation(true);
//        rotationBody.setGravityScale(0f);
//        PolygonShape rotShape = new PolygonShape();
//        rotShape.setAsBox(0.1f, 0.1f);
//        rotationBody.createFixture(rotShape, 0);

//        revoluteJointDef = new RevoluteJointDef();
//        revoluteJointDef.initialize(jetBody, rotationBody, rotationBody.getWorldCenter());
//        world.createJoint(revoluteJointDef);

//        DistanceJointDef distanceJointDef = new DistanceJointDef();
//        distanceJointDef.initialize(jetBody, rotationBody, jetBody.getWorldCenter(), rotationBody.getWorldCenter());
//        world.createJoint(distanceJointDef);

//        WeldJointDef weldJointDef = new WeldJointDef();
//        weldJointDef.initialize(jetBody, rotationBody, jetBody.getWorldCenter());
//        world.createJoint(weldJointDef);

        bulletList = new ArrayList<Bullet>();
        removeBulletList = new ArrayList<Bullet>();

        this.setBounds(jetBody.getPosition().x * PIXELS_TO_METRES - planeWidth/2,
                jetBody.getPosition().y * PIXELS_TO_METRES - planeHeight/2, planeWidth, planeHeight);

        this.addListener(new InputListener(){
        });


    }

    @Override
    public void act(float delta) {
        this.setPosition(jetBody.getPosition().x * PIXELS_TO_METRES - planeWidth/2,
                jetBody.getPosition().y * PIXELS_TO_METRES - planeHeight/2);

        if(startRotation) {
            float nextAngle = jetBody.getAngle() + jetBody.getAngularVelocity() / 10.0f;
            Gdx.app.log("Next angle", nextAngle*MathUtils.radiansToDegrees + "");
            float totalRotation = 360*MathUtils.degreesToRadians - nextAngle;
            Gdx.app.log("totalRotation", totalRotation*MathUtils.radiansToDegrees + "");
            while ( totalRotation < -360 * MathUtils.degreesToRadians) totalRotation += 360 * MathUtils.degreesToRadians;
            while ( totalRotation >  360 * MathUtils.degreesToRadians) totalRotation -= 360 * MathUtils.degreesToRadians;

            float desiredAngularVelocity = totalRotation * 30;
            Gdx.app.log("desiredAngularVelocity", desiredAngularVelocity + "");
            Gdx.app.log("getInertia", jetBody.getInertia() + "");

            torque = jetBody.getInertia() * desiredAngularVelocity/ (1/10.0f);
            Gdx.app.log("Torque" , torque + "");
            jetBody.applyTorque(torque, true);

//            if( ) {
//                Gdx.app.log("Turnign off startRotation, setting 0 angle", "Ok then");
//                startRotation = false;
//                jetBody.setTransform(jetBody.getPosition(), 0);
//                jetBody.applyTorque(-torque, true);
//            }
//
//            if(world.getJointCount() == 0) {
//                world.createJoint(revoluteJointDef);
//            }

        }

        removeBulletList.clear();
        for(Bullet b : bulletList) {
            if(b.bulletBody.getPosition().x*PIXELS_TO_METRES >= 750) {
                removeBulletList.add(b);
                Gdx.app.log("Removing bullet", "No wis the time");
                b.remove();
                world.destroyBody(b.bulletBody);
            }
        }
        bulletList.removeAll(removeBulletList);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();

        for(Bullet b : bulletList) {
            b.draw(batch, parentAlpha);
        }

        TextureRegion currentFrame = jet.getKeyFrame(stateTime);

        batch.draw(currentFrame, jetBody.getPosition().x * PIXELS_TO_METRES - planeWidth/2,
                jetBody.getPosition().y * PIXELS_TO_METRES - planeHeight/2, planeWidth/2,
                planeHeight/2, planeWidth, planeHeight, 1, 1,
                jetBody.getAngle() * MathUtils.radiansToDegrees
        );


    }

}
