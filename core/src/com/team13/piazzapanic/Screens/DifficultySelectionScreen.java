package com.team13.piazzapanic.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.team13.piazzapanic.MainGame;

public class DifficultySelectionScreen implements Screen{
    private int Height = 720;
    private int Width = 1280;
    private FitViewport view = new FitViewport(Width, Height);
    private Stage stage;
    private MainGame game;
    private BitmapFont font = new BitmapFont();

    private ImageButton easyBtn;
    private ImageButton normalBtn;
    private ImageButton hardBtn;
    private ImageButton exitBtn;
    private Texture playFrame = new Texture("playFrame.png");

    public DifficultySelectionScreen (MainGame game){
        stage = new Stage(view, game.batch);
        view.getCamera().position.set(Width / 2, Height / 2, 1f);
        this.game = game;
        font.setColor(Color.PINK);
        font.getData().setScale(2f);

        easyBtn = createButton("Buttons/easyBtn.png");
        normalBtn = createButton("Buttons/normalBtn.png");
        hardBtn = createButton("Buttons/hardBtn.png");
        exitBtn = createButton("Buttons/exitBtn.png");
        stage.addActor(exitBtn);
        stage.addActor(easyBtn);
        stage.addActor(normalBtn);
        stage.addActor(hardBtn);

        easyBtn.setPosition(10, 230);
        normalBtn.setPosition(440, 230);
        hardBtn.setPosition(870, 230);
        exitBtn.setPosition((Width / 2) - (exitBtn.getWidth() / 2), 10);

        Gdx.input.setInputProcessor(stage);
    }

    private ImageButton createButton(String assetPath){
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(assetPath)));
        ImageButton output = new ImageButton(drawable);
        ClickListener cursorHovering = new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Hand);}
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(SystemCursor.Arrow);}
        };
        output.addListener(cursorHovering);
        return output;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act();
        ScreenUtils.clear(1, 1, 1, 1);
        view.apply();

        game.batch.setProjectionMatrix(view.getCamera().combined);
        game.batch.begin();
        game.batch.draw(playFrame, ((Width / 2) - (playFrame.getWidth() / 2)), 500);
        game.batch.end();

        if(easyBtn.isPressed()){Gdx.graphics.setSystemCursor(SystemCursor.Arrow);game.setScreen(new PlayScreen(game, "easy"));}
        if(normalBtn.isPressed()){Gdx.graphics.setSystemCursor(SystemCursor.Arrow);game.setScreen(new PlayScreen(game, "normal"));}
        if(hardBtn.isPressed()){Gdx.graphics.setSystemCursor(SystemCursor.Arrow);game.setScreen(new PlayScreen(game, "hard"));}
        if(exitBtn.isPressed()){Gdx.graphics.setSystemCursor(SystemCursor.Arrow);game.setScreen(new MainMenuScreen(game));}
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        playFrame.dispose();
        stage.dispose();
    }
}

