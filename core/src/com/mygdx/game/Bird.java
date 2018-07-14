package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bird extends Actor {

    private int FRAME_COLS = 6, FRAME_ROWS = 3;
    private Animation<TextureRegion> eagleAnimation;
    private float stateTime, currentPositionX = 100, currentPositionY = 400;
    private Texture birdSheet;
    private TextureRegion[][] birds;
    private boolean changeFrame = false;


    Bird() {

        birdSheet = new Texture(Gdx.files.internal("bird.png"));
        birds = TextureRegion.split(birdSheet, birdSheet.getWidth()/FRAME_COLS,
                birdSheet.getHeight()/FRAME_ROWS);

        TextureRegion[] birdFrames = new TextureRegion[FRAME_ROWS*FRAME_COLS - 1];
        int k = 0;
        for(int i = 0; i < FRAME_ROWS; i ++) {
            for(int j = 0; j < FRAME_COLS; j++) {
                //skip last element since it has 17 images, a prime
                if(i == 2 && j == 5) continue;
                birdFrames[k++] = birds[i][j];
            }
        }

        eagleAnimation = new Animation<TextureRegion>(0.05f, birdFrames);
        stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        if(changeFrame) {
            currentPositionY += 5;
            currentPositionX += 2;
        }
        else{
            currentPositionY -= 2;
            currentPositionX += 2;
        }
    }

    public void toggleChangeFrame() {
        changeFrame = !changeFrame;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(changeFrame) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

        TextureRegion currentFrame = eagleAnimation.getKeyFrame(stateTime, true);
        currentPositionX++;
        currentPositionY -= 0;
        batch.draw(currentFrame, currentPositionX, currentPositionY);

    }

}
