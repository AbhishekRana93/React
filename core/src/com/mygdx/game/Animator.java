package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Animator extends Actor{
    int FRAME_COLS = 3, FRAME_ROWS = 3;

    Animation<TextureRegion> eagleAnimation;
    TextureRegion[] birds;
    float stateTime;

    Animator() {

        birds = new TextureRegion[17];
        for(int i = 1; i <= 17; i ++) {
            Texture texture = new Texture(Gdx.files.internal("bird_" + i + ".png"));
            birds[i-1] = new TextureRegion(texture);
        }

        eagleAnimation = new Animation<TextureRegion>(0.05f, birds);
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = eagleAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 350, 400, 364, 464);

    }

}
