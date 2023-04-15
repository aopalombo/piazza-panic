package com.team13.piazzapanic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team13.piazzapanic.Screens.MainMenuScreen;
import com.team13.piazzapanic.Screens.PlayScreen;

import Recipes.Order;
import Sprites.Chef;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;


public class HUD implements Disposable {
    public Stage stage;
    private MainGame game;
    private PlayScreen screen;
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
    private Order currentOrder;
    private Integer currentOrderNum;

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

    private Boolean isPaused = false;
    private ImageButton pauseBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pauseBtn.png"))));
    private ImageButton pauseMenu = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pauseMenu.png"))));
    private ImageButton resumeBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/resumeBtn.png"))));
    private ImageButton quitBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/quitBtn.png"))));
    private ImageButton xBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/xBtn.png"))));

    private ImageButton shopBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/shopBtn.png"))));
    private ImageButton shopMenu = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/shopMenu.png"))));

    private ImageButton buyChefBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/chefBtn.png"))));
    private Label chefPriceLabel = new Label("$400 (1x)", new Label.LabelStyle(font, Color.GREEN));
    private Boolean chefAvailable = true;
    private Label shopWalletLabel = new Label("", new Label.LabelStyle(font, new Color(1, 0.545f, 0.502f, 1)));

    private final static Integer CHEF_PRICE = 400;
    private final static Integer OVEN_PRICE = 300;
    private final static Integer BOARD_PRICE = 300;
    private final static Integer PAN_PRICE = 300;

    private Preferences saving;
    private String difficulty;

    public HUD(SpriteBatch sb, MainGame game, String difficulty, Boolean resume, PlayScreen screen){
        this.game = game;
        this.screen = screen;
        this.scenarioComplete = Boolean.FALSE;
        saving = Gdx.app.getPreferences("userData");
        this.difficulty = difficulty;
        if(resume){
            this.worldTimerM = saving.getInteger("minutes",0);
            this.worldTimerS = saving.getInteger("seconds", 0);
            this.repPoints = saving.getInteger("rep",3);
            this.score = saving.getInteger("money", 0);
        } else {
            worldTimerM = 0;
            worldTimerS = 0;
            score = 0;
        }
        timeStr = String.format("%d", worldTimerM) + " : " + String.format("%d", worldTimerS);
        float fontX = 0.4F;
        float fontY = 0.2F;
        
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
        table.add(powerUpLabelT).padTop(2).padLeft(2);
        table.row();
        table.add(reputationLabel).padTop(2).padLeft(2);
        table.add(orderTimeLabel).padTop(2).padLeft(2);
        table.add(powerUpLabel).padTop(2).padLeft(2);
        table.left().top();

        powerUps.add("2X SPEED");
        powerUps.add("2X MONEY");
        powerUps.add("FREEZE");
        powerUps.add("SPEEDY");

        pauseBtn.setPosition(1, viewport.getWorldHeight()-pauseBtn.getHeight()-1);
        shopBtn.setPosition(1, viewport.getWorldHeight()-pauseBtn.getHeight()-12);
        buyChefBtn.setPosition(30, 87);
        chefPriceLabel.setPosition(27, 75);
        shopWalletLabel.setPosition(65, 112);
        xBtn.setPosition(10, 143);
        pauseMenu.setPosition(10, 10);
        shopMenu.setPosition(10, 10);
        resumeBtn.setPosition((viewport.getWorldWidth()/2)-(resumeBtn.getWidth()/2), 68);
        quitBtn.setPosition((viewport.getWorldWidth()/2)-(quitBtn.getWidth()/2), 25);

        stage.addActor(pauseBtn);
        stage.addActor(shopBtn);
        table.setPosition(pauseBtn.getWidth(), table.getY());
        stage.addActor(table);
    }

    /**
     * Updates the time label.
     *
     * @param scenarioComplete Whether the game scenario has been completed.
     */
    public void updateTime(Boolean scenarioComplete, Order currentOrder){
        this.currentOrder = currentOrder;
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
            saving.clear();
            saving.flush();
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
            stage.getActors().removeValue(shopBtn, false);
            return;
        }
        if(!isPaused) {
            if (worldTimerS == 59) {
                worldTimerM += 1;
                worldTimerS = 0;
            } else {
                worldTimerS += 1;
            }
            if(worldTimerS%10==0){
                if(!scenarioComplete){
                    save(false);
                }
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

        if(powerUp&&(!isPaused)){
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
        this.currentOrder = currentOrder;
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
        this.currentOrderNum = orderNum;
        if(scenarioComplete==Boolean.TRUE){
            orderNumL.remove();
            orderNumLT.remove();
            table.center().top();
            stage.addActor(table);
        }

        table.left().top();
        orderNumL.setText(String.format("%d", currentOrderNum));
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
    }

    /**
     * Updates the progress bars
     */
    public void updateProgressBars() {
        if (!bars.isEmpty()) {
            for (ProgressBar bar : bars.keySet()) {
                if(!isPaused){
                    bar.setValue(bar.getValue() - 0.05f);
                }
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
    /*
     * Activated when the player pauses the game
     * Pauses the time, stops the movement of the chefs
     */
    public void pause(){
        isPaused = true;
        freezeTime = true;
        stage.getActors().removeValue(pauseBtn, false);
        stage.getActors().removeValue(shopBtn, false);
        if(!stage.getActors().contains(pauseMenu, true)){
            stage.getActors().add(pauseMenu);
        }
        if(!stage.getActors().contains(resumeBtn, true)){
            stage.getActors().add(resumeBtn);
        }
        if(!stage.getActors().contains(quitBtn, true)){
            stage.getActors().add(quitBtn);
        }
        if(!stage.getActors().contains(xBtn, true)){
            stage.getActors().add(xBtn);
        }
    }
    /*
     * Activated when the player unpauses the game
     * Resumes the time and activates the movement of the chefs
     */
    public void unPause(){
        isPaused = false;
        freezeTime = false;
        stage.getActors().removeValue(pauseMenu,false);
        stage.getActors().removeValue(resumeBtn,false);
        stage.getActors().removeValue(quitBtn,false);
        stage.getActors().removeValue(xBtn,false);
        if(!stage.getActors().contains(pauseBtn, true)){
            stage.getActors().add(pauseBtn);
        }
        if(!stage.getActors().contains(shopBtn, true)){
            stage.getActors().add(shopBtn);
        }
    }
    /*
     * Activated when the player presses the shop button
     * Shows the shop menu. The user can buy new stations and new chefs
     */
    public void showShop(){
        isPaused = true;
        freezeTime = true;
        if(chefAvailable){
            if(score < CHEF_PRICE){
                chefPriceLabel.setStyle(new Label.LabelStyle(font, Color.RED));
                shopWalletLabel.setText("WALLET: $"+Integer.toString(score));
            } else {
                chefPriceLabel.setStyle(new Label.LabelStyle(font, Color.GREEN));
                shopWalletLabel.setText("WALLET: $"+Integer.toString(score));
            }}
        stage.getActors().removeValue(shopBtn, false);
        stage.getActors().removeValue(pauseBtn, false);
        if(!stage.getActors().contains(shopMenu, true)){
            stage.getActors().add(shopMenu);
        }
        if(!stage.getActors().contains(xBtn, true)){
            stage.getActors().add(xBtn);
        }
        if(!stage.getActors().contains(buyChefBtn, true)){
            stage.getActors().add(buyChefBtn);
        }
        if(!stage.getActors().contains(shopWalletLabel, true)){
            stage.getActors().add(shopWalletLabel);
        }
        if(!stage.getActors().contains(chefPriceLabel, true)){
            stage.getActors().add(chefPriceLabel);
        }
    }

    /*
     * Activated when the player presses the exit button in the shop button
     * Shows the shop menu. The user can buy new stations and new chefs
     */
    public void hideShop(){
        isPaused = false;
        freezeTime = true;
        stage.getActors().removeValue(shopMenu,false);
        stage.getActors().removeValue(xBtn,false);
        stage.getActors().removeValue(buyChefBtn,false);
        stage.getActors().removeValue(shopWalletLabel,false);
        stage.getActors().removeValue(chefPriceLabel,false);
        if(!stage.getActors().contains(shopBtn, true)){
            stage.getActors().add(shopBtn);
        }
        if(!stage.getActors().contains(pauseBtn, true)){
            stage.getActors().add(pauseBtn);
        }
    }

    /*
     * Returns true if the game is paused, otherwise false
     */
    public boolean isPaused(){
        return isPaused;
    }

    /*
     * Handles all inputs on the buttons in the game
     */
    public void handleButtons(){
        //pause menu buttons
        if(pauseBtn.isPressed()){
            pause();
        }
        if(resumeBtn.isPressed()){
            unPause();
        }
        if(quitBtn.isPressed()){
            if(scenarioComplete){
                game.setScreen(new MainMenuScreen(game));
            } else {
                save(true);
            }
        }
        //shop menu buttons
        if(shopBtn.isPressed()){
            showShop();
        }
        if(buyChefBtn.isPressed()){
            if(chefAvailable){
                if(score >= CHEF_PRICE){
                    if(!screen.chefs.contains(screen.chef3)){
                        screen.chef3.defineChef();
                        screen.chefs.add(screen.chef3);
                        
                        chefAvailable = false;
                        chefPriceLabel.setStyle(new LabelStyle(font, Color.BLACK));
                        chefPriceLabel.setText("unavailable");

                        score -= CHEF_PRICE;
                        scoreLabel.setText(Integer.toString(score));
                    }
                    hideShop();
                }
            }
        }
        //x button for all menus
        if(xBtn.isPressed()){
            hideShop();
            unPause();
        }
    }

    /*
     * Saves current game state in an XML file in the user profile on the local machine
     * If quit is true it will send the user to the main menu after saving
     */
    public void save(boolean quit){
        if(scenarioComplete){
            saving.clear();
        } else {
            String currentDish = "currentOrderDish";
            saving.putInteger("money", this.score);
            saving.putInteger("minutes", this.worldTimerM);
            saving.putInteger("seconds", this.worldTimerS);
            saving.putInteger("currentOrderNum", this.currentOrderNum);
            saving.putInteger("currentOrderTimer", this.currentOrder.orderTime);
            saving.putInteger("countOrder", this.numOrders);
            saving.putInteger("rep", this.repPoints);
            saving.putString("difficulty", this.difficulty);
            for(int i = 0; i<3;i++){
                if((i)<currentOrder.dishes.size()){
                    saving.putString((currentDish+Integer.toString(i+1)), currentOrder.dishes.get(i).recipe.getClass().getName());
                } else {
                    saving.putString((currentDish+Integer.toString(i+1)),"none");
                }
            }
        }
        saving.flush();
        if(quit){
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
