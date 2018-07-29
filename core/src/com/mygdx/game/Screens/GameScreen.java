package com.mygdx.game.Screens;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.mygdx.game.Actors.Bird;
import com.mygdx.game.Actors.Box2DObject;
import com.mygdx.game.Actors.Bullet;
import com.mygdx.game.Actors.DynamicBody;
import com.mygdx.game.Actors.Jet;
import com.mygdx.game.InputHandlers.InputHandler;
import com.mygdx.game.InputHandlers.JetInputHandler;
import com.mygdx.game.InputHandlers.UiInputHandler;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Actors.ParallaxBackground;


public class GameScreen implements Screen {

    MyGdxGame game;

	Music rainMusic;
	OrthographicCamera camera;

	Array<Texture> layers;
	Stage stage;
	Bird birdAnimator;

	World world;
	InputHandler inputHandler;

	Box2DObject box2DObject;
	DynamicBody dynamicBody;
	Bullet bullet;

	Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

	public GameScreen(MyGdxGame game) {
	    this.game = game;
	    camera = new OrthographicCamera();
	    stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, camera));

		layers = new Array<Texture>();

		for(int i = 1 ; i <= 6; i++) {
			layers.add(new Texture(Gdx.files.internal("parallax/img" + i + ".png")));
		}

		ParallaxBackground parallaxBackground = new ParallaxBackground(layers, game);
		parallaxBackground.setSpeed(1);
		parallaxBackground.setSize(game.V_WIDTH, game.V_HEIGHT);
		stage.addActor(parallaxBackground);

		world = new World(new Vector2(0f, -10f), true);
		box2DObject = new Box2DObject(world, game);
//		dynamicBody = new DynamicBody(world, game);
		stage.addActor(box2DObject);
//		stage.addActor(dynamicBody);

		Jet jet = new Jet(world, game, camera);
		stage.addActor(jet);


//		birdAnimator = new Bird(world, game);
//		stage.addActor(birdAnimator);
//		inputHandler = new InputHandler(birdAnimator, world, game, camera);

		JetInputHandler jetInputHandler = new JetInputHandler(world, game, jet.bulletList, jet);
		UiInputHandler uiInputHandler = new UiInputHandler(world, game, camera, jet);

		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(uiInputHandler);
		inputMultiplexer.addProcessor(jetInputHandler);


		Gdx.input.setInputProcessor(inputMultiplexer);

		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(camera.combined);
		debugMatrix = game.batch.getProjectionMatrix().cpy().scale(100f,
				100f, 0);

		game.batch.begin();
		stage.act();
		stage.draw();
		game.batch.end();

		world.step(1/60f, 6, 2);
		debugRenderer.render(world, debugMatrix);

		if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}

	}

	@Override
	public void dispose () {
		stage.dispose();
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