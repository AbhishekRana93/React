package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ParallaxBackground extends Actor {

    Array<Texture> layers;
    int srcX, srcY, scroll, speed;

    ParallaxBackground(Array<Texture> textures) {
        scroll = srcY = speed = 0;
        layers = textures;
        for(int i = 0; i < textures.size ; i++) {
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat,
                    Texture.TextureWrap.MirroredRepeat);
        }

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        scroll += speed;

        for(int i = 0; i < layers.size; i++) {

            srcX = scroll + i*1*scroll;
            batch.draw(
                    layers.get(i), 0, 0, 0, 0, Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight(), 1, 1, 0, srcX, srcY,
                    layers.get(i).getWidth(), layers.get(i).getHeight(), false, false
            );

        }
    }
}
