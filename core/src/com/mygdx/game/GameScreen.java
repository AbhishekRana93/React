package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Iterator;


public class GameScreen implements Screen {

    MyGdxGame game;

	Texture dropImage, bucketImage, eagleSheet;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;

	Vector3 touchPos = new Vector3();

	Rectangle bucket;

	Array<Rectangle> rainDrops;
	Array<Texture> layers;
	int dropsCollected;
	long lastDropTime;

	Stage stage;


	public GameScreen(MyGdxGame game) {
	    this.game = game;
	    stage = new Stage(new ScreenViewport());
		camera = (OrthographicCamera) stage.getViewport().getCamera();

//		dropImage = new Texture(Gdx.files.internal("droplet.png"));
//		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
//
//		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
//		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//		rainMusic.setLooping(true);
//
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 800, 480);
//
//		bucket = new Rectangle();
//		bucket.x = 800 / 2 - 64 / 2;
//		bucket.y = 20;
//		bucket.width = 64;
//		bucket.height = 64;
//
//		rainDrops = new Array<Rectangle>();
//		spawnRainDrop();
//
//		dropsCollected = 0;

		layers = new Array<Texture>();

		for(int i = 1 ; i <= 6; i++) {
			layers.add(new Texture(Gdx.files.internal("parallax/img" + i + ".png")));
		}
		ParallaxBackground parallaxBackground = new ParallaxBackground(layers);
		parallaxBackground.setSpeed(1);
		parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(parallaxBackground);


		Animator animator = new Animator();
		animator.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(animator);

	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	@Override
	public void dispose () {
//		dropSound.dispose();
//		dropImage.dispose();
//		rainMusic.dispose();
//		bucketImage.dispose();
//		eagleSheet.dispose();
		stage.dispose();
	}

	public void spawnRainDrop() {
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800 - 64);
		rainDrop.y = 480;
		rainDrop.width = 64;
		rainDrop.height = 64;
		rainDrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
	}

    @Override
    public void show() {
//        rainMusic.play();
    }

    @Override
    public void hide(){}

    @Override
    public void resume(){}

    @Override
    public void resize(int x, int y){}

    @Override
    public void pause(){}

}






//public class GameScreen implements Screen {
//
//    MyGdxGame game;
//
//
//    public GameScreen(MyGdxGame game) {
//        this.game = game;
//    }
//
//    @Override
//    public void render(float delta) { }
//
//
//}
