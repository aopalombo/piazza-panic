package com.team13.piazzapanic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Recipe.Order;
import Sprites.Chef;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;


public class HUD implements Disposable {
    public Stage stage;
    private Boolean scenarioComplete;

    private Integer worldTimerM;
    private Integer worldTimerS;

    private Integer score;

    public String timeStr;

    public Table table;

    private Label timeLabelT;
    private Label timeLabel;

    private Label reputationLabel;
    private Label reputationLabelT;
    public Integer repPoints = 3;

    private Label orderTimeLabel;
    private Label orderTimeLabelT;

    private Label scoreLabel;
    private Label scoreLabelT;

    private Label powerUpLabel;
    private Label powerUpLabelT;

    private Label orderNumL;
    private Label orderNumLT;
    private Integer numOrders;

    private Integer powerUpTime = 31;
    private Boolean powerUp = false;
    private ArrayList<String> powerUps = new ArrayList<String>();
    private String currentPowerUp;
    private Random randomizer = new Random();
    private Boolean freezeTime = false;

    private BitmapFont font = new BitmapFont();

    private HashMap<ProgressBar,Chef> bars = new HashMap<ProgressBar,Chef>();

    public HUD(SpriteBatch sb){
        this.scenarioComplete = Boolean.FALSE;
        worldTimerM = 0;
        worldTimerS = 0;
        score = 0;
        timeStr = String.format("%d", worldTimerM) + " : " + String.format("%d", worldTimerS);
        float fontX = 0.5F;
        float fontY = 0.3F;

        
        font.getData().setScale(fontX, fontY);
        Viewport viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        table = new Table();
        table.left().top();
        table.setFillParent(true);

        timeLabel = new Label(String.format("%d", worldTimerM, ":", "%i", worldTimerS), new Label.LabelStyle(font, Color.WHITE));
        timeLabelT = new Label("TIME", new Label.LabelStyle(font, Color.BLACK));
        orderNumLT = new Label("ORDER", new Label.LabelStyle(font, Color.BLACK));
        orderNumL = new Label(String.format("%d", 0), new Label.LabelStyle(font, Color.WHITE));
        

        orderTimeLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        orderTimeLabelT = new Label("TIMER", new Label.LabelStyle(font, Color.BLACK));

        reputationLabel = new Label(String.format("%d", repPoints), new Label.LabelStyle(font, Color.WHITE));
        reputationLabelT = new Label("REP", new Label.LabelStyle(font, Color.BLACK));

        scoreLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabelT = new Label("MONEY", new Label.LabelStyle(font, Color.BLACK));

        powerUpLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        powerUpLabelT = new Label("", new Label.LabelStyle(font, Color.BLACK));


        table.add(timeLabelT).padTop(2).padLeft(2);
        table.add(scoreLabelT).padTop(2).padLeft(2);
        table.add(orderNumLT).padTop(2).padLeft(2);
        table.row();
        table.add(timeLabel).padTop(2).padLeft(2);
        table.add(scoreLabel).padTop(2).padLeft(2);
        table.add(orderNumL).padTop(2).padLeft(2);
        table.row();
        table.add(reputationLabelT).padTop(2).padLeft(2);
        table.add(orderTimeLabelT).padTop(2).padLeft(2);
        table.row();
        table.add(reputationLabel).padTop(2).padLeft(2);
        table.add(orderTimeLabel).padTop(2).padLeft(2);
        table.left().top();
        stage.addActor(table);

        powerUpLabel.setPosition(61, 131);
        powerUpLabelT.setPosition(48, 139);

        stage.addActor(powerUpLabel);
        stage.addActor(powerUpLabelT);

        powerUps.add("2X SPEED");
        powerUps.add("2X MONEY");
        powerUps.add("FREEZE TIME");
        powerUps.add("SPEEDY");
    }

    /**
     * Updates the time label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     */
    public void updateTime(Boolean scenarioComplete, Order currentOrder){
        if(!Objects.isNull(currentOrder)){
            if(!freezeTime){
                currentOrder.orderTime--;
            }
            orderTimeLabel.setText(Integer.toString(currentOrder.orderTime));
            if(currentOrder.orderTime==0){
                if(!scenarioComplete){
                    repPoints--;
                    reputationLabel.setText(Integer.toString(repPoints));
                }
            }
        }
        if(scenarioComplete){
            timeLabel.setColor(Color.GREEN);
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
            timeLabel.setText(String.format("TIME: " + timeStr + " MONEY: %d", score));
            timeLabelT.setText("SCENARIO COMPLETE");
            reputationLabelT.setStyle(new LabelStyle(font, Color.GREEN));
            reputationLabelT.setText("Finished with " + Integer.toString(repPoints) + " Reputation points");
            reputationLabel.setText("");
            reputationLabel.remove();
            table.center().top();
            stage.addActor(table);
            return;
        }
        else {
            if (worldTimerS == 59) {
                worldTimerM += 1;
                worldTimerS = 0;
            } else {
                worldTimerS += 1;
            }
        }
        table.left().top();
        if(worldTimerS < 10){
            timeStr = String.format("%d", worldTimerM) + ":0" + String.format("%d", worldTimerS);
        }
        else {
            timeStr = String.format("%d", worldTimerM) + ":" + String.format("%d", worldTimerS);
        }
        timeLabel.setText(timeStr);
        stage.addActor(table);

        if(powerUp){
            powerUpTime--;
            powerUpLabel.setText(Integer.toString(powerUpTime));
            if(powerUpTime == 0){
                powerUpLabel.setText("");
                powerUpLabelT.setText("");
                powerUp = false;
                powerUpTime = 31;
                currentPowerUp = "";
            }
        }
    }

    /**
     * Calculates the user's score per order and updates the label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param expectedTime The expected time an order should be completed in.
     */
    public void updateScore(Boolean scenarioComplete, Integer expectedTime, Integer multiplier, Order currentOrder){
        int addScore = 0;
        int currentTime;

        if(this.scenarioComplete == Boolean.FALSE){
            currentTime = ((worldTimerM * 60) + worldTimerS)/numOrders;
            if (currentTime <= expectedTime) {
                addScore = 100*multiplier;
            }
            else{
                addScore = 100*multiplier - (currentTime -expectedTime);
                if(addScore < 0){
                    addScore = 0;
                }
            }
            if(currentOrder.orderTime==0){
                addScore = 0;
            }
            score += addScore;
        }


        if(scenarioComplete==Boolean.TRUE){
            scoreLabel.setColor(Color.GREEN);
            scoreLabel.setText("");
            scoreLabelT.setText("");
            orderTimeLabel.setText("");
            orderTimeLabelT.setText("");
            powerUpLabel.setText("");
            powerUpLabelT.setText("");
            powerUpLabel.remove();
            powerUpLabelT.remove();
            orderTimeLabel.remove();
            orderTimeLabelT.remove();
            scoreLabelT.remove();
            scoreLabel.remove();
            table.center().top();
            stage.addActor(table);
            this.scenarioComplete = Boolean.TRUE;
            return;
        }

        table.left().top();
        scoreLabel.setText(String.format("%d", score));
        stage.addActor(table);

    }

    /**
     * Updates the order label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     * @param orderNum The index number of the order.
     */
    public void updateOrder(Boolean scenarioComplete, Integer orderNum){
        if(scenarioComplete==Boolean.TRUE){
            orderNumL.remove();
            orderNumLT.remove();
            table.center().top();
            stage.addActor(table);
        }

        table.left().top();
        orderNumL.setText(String.format("%d", orderNum));
        orderNumLT.setText("ORDER");
        stage.addActor(table);

    }

    /**
     * Generates a random power up from the 5 selections: 2x speed, 2x money, 2x cooking speed, no burning food, freeze order time
     */

    public void generatePowerUp(){
        currentPowerUp = powerUps.get(randomizer.nextInt(powerUps.size()));
        powerUp = true;
        powerUpLabelT.setText(currentPowerUp);
    }

    /**
     * returns current power up
     * @return currentPowerUp
     */

    public String getPowerUp(){
        return currentPowerUp;
    }

    /**
     * freezes the time of the current order
     */

    public void freezeTime(){
        freezeTime = true;
    }

    /**
     * unfreezes the time of the current order
     */

    public void unfreezeTime(){
        freezeTime = false;
    }

    public void setNumOrders(Integer num){
        this.numOrders = num;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Generates a progress bar at position x, y for a given duration
     * @param x x coordinate
     * @param y y coordinate
     * @param duration the duration of the bar
     */

    public void createProgressBar(float x, float y, Chef chef, float duration) {
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = getColoredDrawable(20, 5, Color.GREEN);
        style.knob = getColoredDrawable(0, 5, Color.WHITE);
        style.knobAfter = getColoredDrawable(20, 5, Color.WHITE);
        ProgressBar bar = new ProgressBar(0, duration, 0.05f, false, style);
        bar.setWidth(30);
        bar.setHeight(5);
        bar.setValue(15f);
        bar.setX(x);
        bar.setY(y);
        stage.addActor(bar);
        bars.put(bar,chef);
        System.out.println("progress bar created");
    }

    /**
     * Updates the progress bars
     */
    public void updateProgressBars() {
        if (!bars.isEmpty()) {
            for (ProgressBar bar : bars.keySet()) {
                bar.setValue(bar.getValue() - 0.05f);
                if (bar.getValue() <= 0) {
                    stage.getActors().removeValue(bar, false);
                }
            }
        }
    }
    /**
     * Helper function to create rectangles, used for progress bars
     */
    private static TextureRegionDrawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }
}
