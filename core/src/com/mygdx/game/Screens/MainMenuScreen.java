package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.GameScreen;

public class MainMenuScreen implements Screen {
    MyGdxGame game;
//    OrthographicCamera camera;
    Stage stage;
    Label outputLabel;
    Button button;


    public MainMenuScreen(MyGdxGame game) {
        Gdx.input.setCatchBackKey(true);
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        int Help_Guides = 12;
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        Skin mySkin = new Skin(Gdx.files.internal("skins/glassy-ui/glassy-ui.json"));

        outputLabel = new Label("Press Button to Start!", mySkin, "black");
        outputLabel.setSize(Gdx.graphics.getWidth(), row_height);
        outputLabel.setPosition(0, row_height);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);

        button = new TextButton("Click to Start Game!", mySkin);
        button.setSize(col_width*4, row_height);
        button.setPosition(0, Gdx.graphics.getHeight() - row_height*3);
        button.setOrigin(button.getWidth()/2, button.getHeight()/2);
        button.setTransform(true);
        stage.addActor(button);

        MoveToAction moveRightAction = new MoveToAction();
        moveRightAction.setDuration(2);
        moveRightAction.setPosition(col_width*8, Gdx.graphics.getHeight() - row_height);
        moveRightAction.setInterpolation(Interpolation.smooth);


        MoveToAction moveLeftAction = new MoveToAction();
        moveLeftAction.setDuration(2);
        moveLeftAction.setPosition(0, Gdx.graphics.getHeight() - row_height*3);
        moveLeftAction.setInterpolation(Interpolation.smooth);

        ParallelAction parallelActionRight = new ParallelAction();
        parallelActionRight.addAction(moveRightAction);
        parallelActionRight.addAction(Actions.rotateBy(360, 2));

        ParallelAction parallelActionLeft = new ParallelAction();
        parallelActionLeft.addAction(moveLeftAction);
        parallelActionLeft.addAction(Actions.rotateBy(360,2));
//        parallelActionLeft.addAction(Actions.scaleBy(1, 1, 2));

        SequenceAction seq = new SequenceAction();
        seq.addAction(parallelActionLeft);
        seq.addAction(parallelActionRight);

        RepeatAction sequence = new RepeatAction();
        sequence.setCount(RepeatAction.FOREVER);
        sequence.setAction(seq);


//        button.addAction(sequence);

//        button.addListener(new InputListener(){
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Press Text Button");
//            }
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Pressed Text Button");
//                return true;
//            }
//        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//        game.batch.begin();
//        game.font.draw(game.batch, "Welcome to Drop!!", 100, 150);
//        game.font.draw(game.batch, "Tap anywhere to start!!", 100, 100);
//        game.batch.end();

        stage.act();
        stage.draw();

        if(button.isChecked()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public void show(){}
    @Override
    public void hide(){}
    @Override
    public void resume(){}
    @Override
    public void resize(int x, int y){}
    @Override
    public void pause(){}

}
