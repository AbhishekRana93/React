package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import javax.xml.soap.Text;

public class Bird extends Actor {

    private World world;
    private Body body;
    private int FRAME_COLS = 6, FRAME_ROWS = 3;
    private Animation<TextureRegion> eagleAnimation;
    public float stateTime, currentPositionX, currentPositionY, rotation = -90f, PTM = 10f,
            birdWidth, birdHeight;
    private TextureRegion[] birdFrames;
    private TextureRegion[][] birds;
    private boolean shouldFly = false, rotateClockwise = false, moveBackwardsFlag = false,
            moveForwardFlag = false;


    Bird(World world, float pos_x, float pos_y) {

        this.world = world;
        currentPositionX = pos_x;
        currentPositionY = pos_y;

        Texture birdSheet = new Texture(Gdx.files.internal("bird.png"));
        birds = TextureRegion.split(birdSheet, birdSheet.getWidth()/FRAME_COLS,
                birdSheet.getHeight()/FRAME_ROWS);

        birdFrames = new TextureRegion[FRAME_ROWS*FRAME_COLS - 1];
        int k = 0;
        for(int i = 0; i < FRAME_ROWS; i ++) {
            for(int j = 0; j < FRAME_COLS; j++) {
                //skip last element since it has 17 images, a prime
                if(i == 2 && j == 5) continue;
                birdFrames[k++] = birds[i][j];
            }
        }

        birdWidth = birdFrames[0].getRegionWidth();
        birdHeight = birdFrames[0].getRegionHeight();

        Gdx.app.log("Size of bird frame ", "" + birdWidth + " " + birdHeight);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set( (pos_x + birdWidth)/PTM, (pos_y + birdHeight)/PTM);

        body = this.world.createBody(bodyDef);

//        body.applyTorque(1f, true);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((birdWidth/2)/PTM, (birdHeight/2)/PTM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.1f;
//        fixtureDef.friction = 0f;
//        fixtureDef.restitution= 1f;
        Fixture fixture = body.createFixture(fixtureDef);

        eagleAnimation = new Animation<TextureRegion>(0.05f, birdFrames);

        body.setUserData(eagleAnimation);

        stateTime = 0f;
        polygonShape.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        rotation += body.getAngle() *  MathUtils.radiansToDegrees;

//        Gdx.app.log("Inside act: Body ", "" + body.getPosition().x + " " + body.getPosition().y);
//        Gdx.app.log("Rotation of body" , "" + Math.toDegrees(body.getAngle()) );
//        Gdx.app.log("Inside act: Bird ", "" + currentPositionX + " " + currentPositionY);

//        currentPositionX = body.getPosition().x * PTM - birdWidth/2;
//        currentPositionY = body.getPosition().y * PTM - birdHeight/2;


        if(moveBackwardsFlag) moveBackwards(delta);
        else if(moveForwardFlag) moveforward(delta);

    }

    public void setMoveForwardFlag(boolean t){
        moveForwardFlag = t;
    }

    public void setMoveBackwardsFlag(boolean t) {
        moveBackwardsFlag = t;
    }

    public void setShouldFly(boolean t) {
        shouldFly = t;
    }

    public void setRotateClockwise(boolean t) {
        rotateClockwise = t;
    }

    private void moveforward(float delta) {
        Gdx.app.log("Moving forward ", " Moving forward ");

        currentPositionX += 1;
        currentPositionY += 5;
    }

    private void moveBackwards(float delta) {
        Gdx.app.log("Rotating backwards ", "Moving backwards");

        currentPositionX -= 1;
        currentPositionY -= 5;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = eagleAnimation.getKeyFrame(stateTime, true);

        if(shouldFly) {
            stateTime += Gdx.graphics.getDeltaTime();
        }
        else {
            int frameIndex = (int) (stateTime/0.05f);
            frameIndex %= 17;
            if(frameIndex != 7) stateTime += Gdx.graphics.getDeltaTime();
        }

        currentPositionY = currentPositionY < 0 ? 0 : currentPositionY;


        batch.draw(currentFrame, currentPositionX, currentPositionY, birdFrames[0].getRegionWidth()/2,
                birdFrames[0].getRegionHeight()/2, birdFrames[0].getRegionWidth(),
                birdFrames[0].getRegionHeight(), 1, 1, rotation, rotateClockwise);
    }

}
