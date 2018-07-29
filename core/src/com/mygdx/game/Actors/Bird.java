package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.MyGdxGame;

import javax.xml.soap.Text;

public class Bird extends Actor{

    public Body body, staticBody;
    private World world;
    private RevoluteJointDef revoluteJointDef;
    private int FRAME_COLS = 3, FRAME_ROWS = 7, rotCount = 0;
    private Animation<TextureRegion> eagleAnimation;
    public float stateTime, rotationAngle, PIXELS_TO_METRES = 100f, birdWidth, birdHeight, torque;
    private TextureRegion[] birdFrames;
    private TextureRegion[][] birds;
    public boolean shouldFly = false, startRotation = false, moveBackwardsFlag = false,
            moveForwardFlag = false;

    TextureAtlas atlas;


    public Bird(World world, MyGdxGame game) {

        this.world = world;
//        Texture birdSheet = new Texture(Gdx.files.internal("bird_walk.atlas"));
        atlas = new TextureAtlas(Gdx.files.internal("bird.atlas"));
//        birds = TextureRegion.split(birdSheet, birdSheet.getWidth()/FRAME_COLS,
//                birdSheet.getHeight()/FRAME_ROWS);
//
//        birdFrames = new TextureRegion[FRAME_ROWS*FRAME_COLS - 2];
//        int k = 0;
//        for(int i = 0; i < FRAME_ROWS; i ++) {
//            for(int j = 0; j < FRAME_COLS; j++) {
//                //skip last element since it has 17 images, a prime
//                if(i == 0 && j == 2) continue;
//                if(i == 1 && j == 2) continue;
//                birdFrames[k++] = birds[i][j];
//            }
//        }

        birdWidth = 100;
        birdHeight = 100;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((game.V_WIDTH/3)/PIXELS_TO_METRES, (game.V_HEIGHT/2)/PIXELS_TO_METRES);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((birdWidth/4)/PIXELS_TO_METRES, (birdHeight/4)/PIXELS_TO_METRES);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setGravityScale(0f);

        eagleAnimation = new Animation<TextureRegion>(0.05f, atlas.findRegions("bird"), Animation.PlayMode.LOOP);
        Gdx.app.log("Animation create", eagleAnimation.toString());

        stateTime = 0f;
        torque = 0.0f;
        rotationAngle = body.getAngle() * MathUtils.radiansToDegrees + 75f;


        polygonShape.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(startRotation) {
            float nextAngle = body.getAngle() + body.getAngularVelocity() / 10.0f;
            float totalRotation = 360*MathUtils.degreesToRadians - nextAngle;
            while ( totalRotation < -360 * MathUtils.degreesToRadians) totalRotation += 360 * MathUtils.degreesToRadians;
            while ( totalRotation >  360 * MathUtils.degreesToRadians) totalRotation -= 360 * MathUtils.degreesToRadians;
            float desiredAngularVelocity = totalRotation * 30;
            torque = body.getInertia() * desiredAngularVelocity/ (1/10.0f);
            body.applyTorque(torque, true);
//
//            if(world.getJointCount() == 0) {
//                world.createJoint(revoluteJointDef);
//            }

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = eagleAnimation.getKeyFrame(stateTime);

//        if(shouldFly) {
//        }
//        else {
//            int frameIndex = (int) (stateTime/0.05f);
//            frameIndex %= 17;
//            if(frameIndex != 7) stateTime += Gdx.graphics.getDeltaTime();
//        }
//
//        float angle = body.getAngle() ;
//        float degAngle = angle*MathUtils.radiansToDegrees;
//        if(degAngle > 360) {
//            degAngle = degAngle - ( (int)(degAngle/360) * 360);
//        }
//
//
//        Gdx.app.log("Body angle " ,  body.getAngle() * MathUtils.radiansToDegrees + ", " + degAngle);
//        if(body.getAngle() * MathUtils.radiansToDegrees <= -90 || body.getAngle() * MathUtils.radiansToDegrees >= 90) {
//            body.setGravityScale(0.1f);
//        }
//        else body.setGravityScale(0);
//
//        if( degAngle >= 0 && degAngle <= 1) {
//            if(body.getAngularVelocity() != 0) {
//                body.setAngularVelocity(0);
//            }
//        }
//

        batch.draw(currentFrame, body.getPosition().x * PIXELS_TO_METRES - birdWidth/2 ,
                body.getPosition().y * PIXELS_TO_METRES - birdHeight / 2,
                birdWidth/2,birdHeight/2, birdWidth, birdHeight, 1, 1,
                body.getAngle()*MathUtils.radiansToDegrees + 75f, true);

    }

}
