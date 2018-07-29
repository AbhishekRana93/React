package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;

public class Bullet extends Actor {
    World world;
    MyGdxGame game;
    Animation<TextureRegion> bulletAnim;
    TextureRegion singleBullet;
    float pos_x, pos_y, width, height, PIXELS_TO_METRES = 100, speed = 5f, stateTime;
    Body bulletBody;

    public Bullet(World world, MyGdxGame game, float pos_x, float pos_y) {
        this.world = world;
        this.game = game;
        this.pos_x = pos_x;
        this.pos_y = pos_y;


        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("bullet.atlas"));
        bulletAnim = new Animation<TextureRegion>(0.1f, atlas.findRegions("Bullet"),
                Animation.PlayMode.LOOP);
        singleBullet = bulletAnim.getKeyFrame(0);

        width = 45f;
        height = 20f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos_x / PIXELS_TO_METRES, pos_y / PIXELS_TO_METRES);
        bodyDef.bullet = true;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width/(2*PIXELS_TO_METRES) , height/ (2*PIXELS_TO_METRES));

        bulletBody = world.createBody(bodyDef);
        bulletBody.createFixture(polygonShape, 1f);
        bulletBody.setGravityScale(0f);
        bulletBody.setFixedRotation(true);

        stateTime = 0f;
        polygonShape.dispose();
    }

//    @Override
//    public void act(float delta) {
//        Gdx.app.log("Acting", bulletBody.getPosition().x*PIXELS_TO_METRES + "");
//        if(bulletBody.getPosition().x*PIXELS_TO_METRES >= game.V_WIDTH - 100) {
////            this.remove = true;
//            world.destroyBody(bulletBody);
//        }
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = bulletAnim.getKeyFrame(stateTime, true);
        batch.draw(
                singleBullet, bulletBody.getPosition().x * PIXELS_TO_METRES - width / 2,
                bulletBody.getPosition().y * PIXELS_TO_METRES - height / 2, width / 2,
                height / 2, width, height, 1, 1,
                bulletBody.getAngle() * MathUtils.radiansToDegrees
        );

//        batch.draw(
//                singleBullet, bulletBody.getPosition().x * PIXELS_TO_METRES - width/2,
//                bulletBody.getPosition().y * PIXELS_TO_METRES - height/2, width,
//                height);

    }

    public void setPosition(float x, float y) {
//        pos_x = x;
//        pos_y = y;
        bulletBody.setTransform((x + width/2)/PIXELS_TO_METRES, (y- 10)/PIXELS_TO_METRES, 0);
//        Gdx.app.log("Now position", bulletBody.getPosition().toString());
    }

    public void startMotion() {
        bulletBody.applyLinearImpulse(1, 0, 0, 0, true);
//        bulletBody.setLinearVelocity(speed, 0);
    }
}
