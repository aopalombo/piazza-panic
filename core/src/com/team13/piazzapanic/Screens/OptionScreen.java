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

public class OptionScreen implements Screen{
    private int Height = 720;
    private int Width = 1280;
    private FitViewport view = new FitViewport(Width, Height);
    private Stage stage;
    private MainGame game;
    private BitmapFont font = new BitmapFont();

    private ImageButton scenarioBtn;
    private ImageButton endlessBtn;
    private ImageButton exitBtn;
    private Texture playFrame = new Texture("playFrame.png");

    public OptionScreen (MainGame game){
        stage = new Stage(view, game.batch);
        view.getCamera().position.set(Width / 2, Height / 2, 1f);
        this.game = game;
        font.setColor(Color.PINK);
        font.getData().setScale(2f);

        scenarioBtn = createButton("Buttons/scenarioBtn.png");
        endlessBtn = createButton("Buttons/endlessBtn.png");
        exitBtn = createButton("Buttons/exitBtn.png");
        stage.addActor(exitBtn);
        stage.addActor(scenarioBtn);
        stage.addActor(endlessBtn);

        scenarioBtn.setPosition(80, 300);
        endlessBtn.setPosition(700, 300);
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
        //Scenario mode description
        font.draw(game.batch,"In Scenario mode your reputation is on",80,280);
        font.draw(game.batch, "the line! Your goal is to serve customers",80,250);
        font.draw(game.batch,"on time and earn money. Use your money",80,220);
        font.draw(game.batch,"to upgrade your kitchen and get new staff.",80,190);
        //Endless mode description
        font.draw(game.batch,"In Endless mode there is an infinite horde",700,280);
        font.draw(game.batch,"of customers coming to your restaurant",700,250);
        font.draw(game.batch,"If you lose your reputation points you lose!",700,220);
        game.batch.draw(playFrame, ((Width / 2) - (playFrame.getWidth() / 2)), 500);
        game.batch.end();

        if(scenarioBtn.isPressed()){game.setScreen(new DifficultySelectionScreen(game));}
        if(endlessBtn.isPressed()){game.setScreen(new PlayScreen(game, "infinite"));}
        if(exitBtn.isPressed()){game.setScreen(new MainMenuScreen(game));}
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

