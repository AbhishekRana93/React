package com.mygdx.game.InputHandlers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Actors.Bird;
import com.mygdx.game.MyGdxGame;

public class InputHandler extends InputAdapter {
    Bird birdAnimator;
    World world;
    MyGdxGame game;
    Camera camera;

    public InputHandler(Bird birdAnimator, World world, MyGdxGame game, Camera camera) {
        this.birdAnimator = birdAnimator;
        this.world = world;
        this.game = game;
        this.camera = camera;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = camera.unproject(new Vector3(screenX, screenY, 0f));
        Vector2 currentBodyPos = birdAnimator.body.getPosition();
        Vector3 movePos = touchPos.sub(new Vector3(currentBodyPos.x * 100, currentBodyPos.y*100, 0));
        movePos.nor();

        Vector2 move2d = new Vector2(movePos.x, movePos.y);
        move2d.scl(1);
//        birdAnimator.startRotation = false;
//        birdAnimator.body.setLinearVelocity(move2d);
//        birdAnimator.body.setLinearDamping(2);
//        birdAnimator.body.setAngularDamping(1);
//        birdAnimator.body.applyLinearImpulse(move2d.scl(-1f), currentBodyPos ,true);
//        birdAnimator.body.applyForce(move2d,currentBodyPos, true);

//        float angle = (float) Math.atan2( movePos.y, movePos.x) ;
//        float degAngle = angle*MathUtils.radiansToDegrees;
//        if(degAngle > 360) {
//            degAngle = degAngle - ((degAngle%360) * 360);
//        }
//        if( degAngle <= 90 && angle*MathUtils.radiansToDegrees >= 0) {
//            if(birdAnimator.body.getAngularVelocity() != 0) {
//                birdAnimator.body.setAngularVelocity(0);
//            }
//        }
//        birdAnimator.body.setTransform(currentBodyPos, angle);


        return true;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        birdAnimator.body.setTransform(birdAnimator.body.getPosition(), 0);
        birdAnimator.startRotation = false;

        Vector3 touchPos = camera.unproject(new Vector3(screenX, screenY, 0f));
        Vector2 currentBodyPos = birdAnimator.body.getPosition();
        Vector3 movePos = touchPos.sub(new Vector3(currentBodyPos.x * 100, currentBodyPos.y*100, 0));
        movePos.nor();

        Vector2 move2d = new Vector2(movePos.x, movePos.y);
        move2d.scl(1);
//        birdAnimator.body.setLinearVelocity(move2d);

//        birdAnimator.body.applyForce(move2d,currentBodyPos, true);

        float angle = (float) Math.atan2( movePos.y, movePos.x);
        if(angle *MathUtils.radiansToDegrees >= 70 || angle*MathUtils.radiansToDegrees <= -70) {
            birdAnimator.startRotation = true;

            birdAnimator.body.setTransform(birdAnimator.body.getPosition().x - 0.1f,
                    birdAnimator.body.getPosition().y, birdAnimator.body.getAngle());

//            birdAnimator.body.applyForce(-1f, -1f, touchPos.x, touchPos.y, true);
            //            birdAnimator.setOrigin( -100, -100);
//              birdAnimator.body.setAngularVelocity(0.1f);
//              birdAnimator.body.setFixedRotation(true);
//            birdAnimator.body.applyAngularImpulse(4, true);

        }
        else {
            Gdx.app.log("Applying impulse", "Inside else in InpHandlr");
            birdAnimator.body.applyLinearImpulse(move2d, currentBodyPos ,true);
//            birdAnimator.body.setTransform(currentBodyPos, angle);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}
