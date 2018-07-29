package com.mygdx.game.InputHandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Actors.Bullet;
import com.mygdx.game.Actors.Jet;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

public class JetInputHandler extends InputAdapter {
    Jet jet;
    Body jetBody;
    Bullet bullet;
    ArrayList<Bullet> bulletList;
    World world;
    MyGdxGame game;
    final float PIXELS_TO_METRES = 100f;

    public JetInputHandler(World world, MyGdxGame game, ArrayList bulletList, Jet jet) {
        this.world = world;
        this.game = game;
        this.jet = jet;
        this.jetBody = jet.jetBody;
        this.bulletList = bulletList;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float planeWidth = 100f;
        bullet = new Bullet(world, game, 200f, 300f);
        bullet.setPosition(jetBody.getPosition().x * PIXELS_TO_METRES + planeWidth/2,
                jetBody.getPosition().y * PIXELS_TO_METRES);
        bullet.startMotion();
        bulletList.add(bullet);

        return true;
    }

}
