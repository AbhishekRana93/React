package com.mygdx.game.Actors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;

public class Alien extends Actor {
    private World world;
    private Body alienBody;
    private TextureRegion textureRegion;
    private float width, height, PIXELS_TO_METRES = 100;
    private Jet jet;
    public String desc = "Alien";

    public Alien(World world, Jet jet, MyGdxGame game) {
        this.world = world;
        width = 60f;
        height = 60f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set( (game.V_WIDTH-100)/PIXELS_TO_METRES, (game.V_HEIGHT/2)/PIXELS_TO_METRES);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((width/2)/PIXELS_TO_METRES , (height/2)/PIXELS_TO_METRES);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;

        alienBody = world.createBody(bodyDef);
        alienBody.createFixture(fixtureDef);
        alienBody.setLinearVelocity(-0.4f, 0);
        alienBody.setGravityScale(0f);
        alienBody.setUserData(this);

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("alien_ship.atlas"));
        textureRegion = textureAtlas.findRegion("alien");
    }


    public void draw(Batch batch, float parentAlpha) {

        batch.draw(textureRegion, alienBody.getPosition().x * PIXELS_TO_METRES - width/2,
                alienBody.getPosition().y*PIXELS_TO_METRES - height/2, width/2,
                height/2, width, height, 1f, 1f,
                alienBody.getAngle()* MathUtils.radiansToDegrees
        );
    }

    public void removeBody() {
        world.destroyBody(alienBody);
    }

}
