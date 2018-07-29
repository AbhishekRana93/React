package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;

public class Box2DObject extends Actor {
    World world;
    Texture ground;
    Body groundBody, leftSideBody, rightSideBody, topBody;
    MyGdxGame game;
    float PIXELS_TO_METRES = 100f;


    public Box2DObject(World world, MyGdxGame game) {
        this.world = world;
        this.game = game;
        this.setPosition(0, 0);

        ground = new Texture(Gdx.files.internal("ground_desert.png"));

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 0);

        PolygonShape groundPolygonShape = new PolygonShape();
        groundPolygonShape.setAsBox(game.V_WIDTH / PIXELS_TO_METRES, (game.V_HEIGHT/10) /PIXELS_TO_METRES );

        groundBody = world.createBody(groundBodyDef);
        groundBody.createFixture(groundPolygonShape, 0);

        PolygonShape leftPolygonShape = new PolygonShape();
        leftPolygonShape.setAsBox(0.1f, game.V_HEIGHT / PIXELS_TO_METRES);
        leftSideBody = world.createBody(groundBodyDef);
        leftSideBody.createFixture(leftPolygonShape, 0);

        BodyDef topBodyDef = new BodyDef();
        topBodyDef.position.set(game.V_WIDTH / PIXELS_TO_METRES, (game.V_HEIGHT + 1f)/ PIXELS_TO_METRES);

        PolygonShape rightPolygonShape = new PolygonShape();
        rightPolygonShape.setAsBox(0.01f, game.V_HEIGHT / PIXELS_TO_METRES);
        rightSideBody = world.createBody(topBodyDef);
        rightSideBody.createFixture(rightPolygonShape, 0);

        PolygonShape topPolygonShape = new PolygonShape();
        topPolygonShape.setAsBox(game.V_WIDTH / PIXELS_TO_METRES, 0.01f);

        topBody = world.createBody(topBodyDef);
        topBody.createFixture(topPolygonShape, 0);

        groundPolygonShape.dispose();
        leftPolygonShape.dispose();
        rightPolygonShape.dispose();
        topPolygonShape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(ground, 0, 0, game.V_WIDTH, game.V_HEIGHT/8);
    }
}
