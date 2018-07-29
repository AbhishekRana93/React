package com.mygdx.game.InputHandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Actors.Jet;
import com.mygdx.game.MyGdxGame;

public class UiInputHandler extends InputAdapter {
    Jet jet;
    World world;
    MyGdxGame game;
    Camera camera;
    Body jetBody;
    Joint a;

    public UiInputHandler(World world, MyGdxGame game, Camera camera, Jet jet) {
        this.jet = jet;
        this.game = game;
        this.world = world;
        this.camera = camera;
        this.jetBody = jet.jetBody;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Gdx.app.log("Clicked", "On Screen" + Gdx.graphics.getDeltaTime() );
        Vector3 touchPos = camera.unproject(new Vector3(screenX, screenY, 0f));
        Vector2 currentBodyPos = jetBody.getPosition();
        Vector3 movePos = touchPos.sub(new Vector3(currentBodyPos.x * 100, currentBodyPos.y*100, 0));
        Gdx.app.log("Movepos magnitude", "" + movePos.toString() + ", " + movePos.len());
        if(movePos.len() <= 50) return  false;
        movePos.nor();

        Vector2 move2d = new Vector2(movePos.x, movePos.y);
        move2d.scl(3f);

        float angle = (float) Math.atan2( movePos.y, movePos.x) ;
        float degAngle = angle* MathUtils.radiansToDegrees;
        Gdx.app.log("JetBody position ", jetBody.getPosition().toString());
//        Gdx.app.log("RotBody position ", jet.rotationBody.getPosition().toString());
        if(degAngle > 85 || degAngle < -85) {
//            jet.rotationBody.setTransform(new Vector2(currentBodyPos.x - 0.06f, currentBodyPos.y), 0);
            jetBody.setLinearVelocity(move2d);
//            jet.rotationBody.setType(BodyDef.BodyType.StaticBody);
            jet.startRotation = true;
//            jetBody.setAngularVelocity(0.1f);
        }
        else {
//            jet.rotationBody.setType(BodyDef.BodyType.DynamicBody);
//            jetBody.setLinearVelocity(move2d);
//            jet.rotationBody.setLinearVelocity(move2d);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Down", "Go first");
//        jet.startRotation = false;
        return true;
    }
}
