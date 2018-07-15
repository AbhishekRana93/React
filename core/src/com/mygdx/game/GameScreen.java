package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Button;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;


public class GameScreen implements Screen {

    MyGdxGame game;

	Music rainMusic;
	OrthographicCamera camera;

	Vector2 touchPos = new Vector2();
	Array<Texture> layers;
	Stage stage;
	Bird birdAnimator;

	World world;

	public GameScreen(MyGdxGame game) {
	    this.game = game;

	    camera = new OrthographicCamera(800,480);
	    stage = new Stage(new ScreenViewport(camera));


		layers = new Array<Texture>();

		for(int i = 1 ; i <= 6; i++) {
			layers.add(new Texture(Gdx.files.internal("parallax/img" + i + ".png")));
		}
		ParallaxBackground parallaxBackground = new ParallaxBackground(layers);
		parallaxBackground.setSpeed(1);
		parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(parallaxBackground);


		world = new World(new Vector2(0, -9.8f), true);

		birdAnimator = new Bird(world, Gdx.graphics.getWidth()/16, Gdx.graphics.getHeight()/2);
		birdAnimator.setBounds(1000, 0, 800, 480);

		stage.addActor(birdAnimator);


		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				birdAnimator.setShouldFly(false);
				birdAnimator.setMoveForwardFlag(false);
				birdAnimator.setMoveBackwardsFlag(false);
				return  true;
			}

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector2 touchPosition = birdAnimator.screenToLocalCoordinates(new Vector2(screenX,
						screenY));

				Gdx.app.log("Touch pos ", touchPosition.x + " " + touchPosition.y);

				if( touchPosition.x > birdAnimator.currentPositionX &&
						touchPosition.y > birdAnimator.currentPositionY) {
					birdAnimator.setMoveForwardFlag(true);
					birdAnimator.setMoveBackwardsFlag(false);
//					birdAnimator.setRotateClockwise(false);
				}
				else if(touchPosition.x <= birdAnimator.currentPositionX &&
						touchPosition.y > birdAnimator.currentPositionY){
					birdAnimator.setMoveForwardFlag(false);
					birdAnimator.setMoveBackwardsFlag(true);
//					birdAnimator.setRotateClockwise(false);
				}
				else if(touchPosition.x <= birdAnimator.currentPositionX &&
						touchPosition.y <= birdAnimator.currentPositionY){
					birdAnimator.setMoveForwardFlag(false);
					birdAnimator.setMoveBackwardsFlag(true);
//					birdAnimator.setRotateClockwise(true);
				}
				else if(touchPosition.x > birdAnimator.currentPositionX &&
						touchPosition.y <= birdAnimator.currentPositionY){
					birdAnimator.setMoveForwardFlag(true);
					birdAnimator.setMoveBackwardsFlag(false);
//					birdAnimator.setRotateClockwise(true);
				}

				birdAnimator.setShouldFly(true);
                return true;
            }



//			@Override
//			public boolean touchDragged(int screenX, int screenY, int pointer) {
//				Vector2 touchPos = birdAnimator.screenToLocalCoordinates(new Vector2(screenX, screenY));
//				Gdx.app.log("Touch pos ", screenX + " " + screenY + " " + pointer);
//				birdAnimator.currentPositionY = touchPos.y;
//				birdAnimator.currentPositionX = touchPos.x;
//				return true;
//			}
		});

	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

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
