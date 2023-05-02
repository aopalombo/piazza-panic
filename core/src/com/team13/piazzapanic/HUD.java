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
import Sprites.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Image pauseMenu = new Image (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pauseMenu.png"))));
    private ImageButton resumeBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/resumeBtn.png"))));
    private ImageButton quitBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/quitBtn.png"))));
    private ImageButton helpBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/helpBtn.png"))));
    private ImageButton xBtn = new ImageButton (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/xBtn.png"))));
    private Image helpImage = new Image (new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/controls.png"))));

    private ImageButton shopBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/shopBtn.png"))));
    private Image shopMenu = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/shopMenu.png"))));
    private Label shopWalletLabel = new Label("", new Label.LabelStyle(font, new Color(1, 0.545f, 0.502f, 1)));
    
    private ImageButton buyChefBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/chefBtn.png"))));
    private Label chefPriceLabel = new Label("$400 (1x)", new Label.LabelStyle(font, Color.GREEN));
    private Boolean chefAvailable = true;

    private ImageButton buyChoppingBoardBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/choppingBoardBtn.png"))));
    private Label choppingBoardPriceLabel = new Label("$100 (2x)", new Label.LabelStyle(font, Color.GREEN));
    private Boolean choppingBoardAvailable = true;

    private ImageButton buyOvenBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/ovenBtn.png"))));
    private Label ovenPriceLabel = new Label("$400 (1x)", new Label.LabelStyle(font, Color.GREEN));
    private Boolean ovenAvailable = true;

    private ImageButton buyPanBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/panBtn.png"))));
    private Label panPriceLabel = new Label("200 (2x)", new Label.LabelStyle(font, Color.GREEN));
    private Boolean panAvailable = true;

    private final static Integer CHEF_PRICE = 400;
    private final static Integer OVEN_PRICE = 400;
    private final static Integer BOARD_PRICE = 100;
    private final static Integer PAN_PRICE = 200;

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

        //HUD and pause menu buttons
        pauseBtn.setPosition(1, viewport.getWorldHeight()-pauseBtn.getHeight()-1);
        shopBtn.setPosition(1, viewport.getWorldHeight()-pauseBtn.getHeight()-12);
        xBtn.setPosition(10, 143);
        pauseMenu.setPosition(10, 10);
        shopMenu.setPosition(10, 10);
        resumeBtn.setPosition((viewport.getWorldWidth()/2)-(resumeBtn.getWidth()/2), 68);
        helpBtn.setPosition(127, 128);
        helpImage.setPosition((viewport.getWorldWidth()/2)-(helpImage.getWidth()/2), (viewport.getWorldHeight()/2)-(helpImage.getHeight()/2));
        quitBtn.setPosition((viewport.getWorldWidth()/2)-(quitBtn.getWidth()/2), 25);

        //Shop menu buttons and labels
        shopWalletLabel.setPosition(65, 112);

        buyChefBtn.setPosition(30, 84);
        chefPriceLabel.setPosition(27, 72);

        buyChoppingBoardBtn.setPosition(70, 84);
        choppingBoardPriceLabel.setPosition(67, 72);

        buyPanBtn.setPosition(110, 84);
        panPriceLabel.setPosition(107, 72);

        buyOvenBtn.setPosition(30, 54);
        ovenPriceLabel.setPosition(27, 42);

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

    public ProgressBar createProgressBar(float x, float y, Chef chef, float duration, boolean fail) {
        ProgressBarStyle style = new ProgressBarStyle();
        if(fail){
            style.background = getColoredDrawable(20, 5, Color.RED);
        } else {
            style.background = getColoredDrawable(20, 5, Color.GREEN);
        }
        style.knob = getColoredDrawable(0, 5, Color.WHITE);
        style.knobAfter = getColoredDrawable(20, 5, Color.WHITE);
        ProgressBar bar = new ProgressBar(0, duration, 0.05f, false, style);
        bar.setWidth(30);
        bar.setHeight(5);
        bar.setValue(15f);
        bar.setX(x);
        bar.setY(y);
        if(!bars.values().contains(chef)){
            stage.addActor(bar);
            bars.put(bar,chef);
        }
        return bar;
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
                    bars.remove(bar);
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
        if(!stage.getActors().contains(helpBtn, true)){
            stage.getActors().add(helpBtn);
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
        stage.getActors().removeValue(helpBtn,false);
        stage.getActors().removeValue(helpImage,false);
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
        shopWalletLabel.setText("WALLET: $"+Integer.toString(score));
        int choppingBoardCount = 0;
        int panCount = 0;

        //Check if you can buy any more chopping boards
        for(ChoppingBoard board : screen.getChoppingBoards()){
            if(!board.isLocked()){
                choppingBoardCount++;
            }
        }
        if(choppingBoardCount >= 3){
            choppingBoardAvailable = false;
            choppingBoardPriceLabel.setStyle(new LabelStyle(font, Color.BLACK));
            choppingBoardPriceLabel.setText("unavailable");
        } else {
            choppingBoardAvailable = true;
        }

        //check if you can buy any more pans
        for(Pan pan : screen.getPans()){
            if(!pan.isLocked()){
                panCount++;
            }
        }
        if(panCount >= 3){
            panAvailable = false;
            panPriceLabel.setStyle(new LabelStyle(font, Color.BLACK));
            panPriceLabel.setText("unavailable");
        } else {
            panAvailable = true;
        }

        //Change color of labels. If the player has enough money to buy the item it is green otherwise it is red
        if(chefAvailable){
            if(score < CHEF_PRICE){
                chefPriceLabel.setStyle(new Label.LabelStyle(font, Color.RED));
            } else {
                chefPriceLabel.setStyle(new Label.LabelStyle(font, Color.GREEN));
            }
        }
        if(ovenAvailable){
            if(score < OVEN_PRICE){
                ovenPriceLabel.setStyle(new Label.LabelStyle(font, Color.RED));
            } else {
                ovenPriceLabel.setStyle(new Label.LabelStyle(font, Color.GREEN));
            }
            ovenPriceLabel.setText("$400 (1x)");
        }
        if(choppingBoardAvailable){
            if(score < BOARD_PRICE){
                choppingBoardPriceLabel.setStyle(new Label.LabelStyle(font, Color.RED));
            } else {
                choppingBoardPriceLabel.setStyle(new Label.LabelStyle(font, Color.GREEN));
            }
            choppingBoardPriceLabel.setText("$100 ("+Integer.toString(3-choppingBoardCount)+"x)");
        }
        if(panAvailable){
            if(score < PAN_PRICE){
                panPriceLabel.setStyle(new Label.LabelStyle(font, Color.RED));
            } else {
                panPriceLabel.setStyle(new Label.LabelStyle(font, Color.GREEN));
            }
            panPriceLabel.setText("$200 ("+Integer.toString(3-panCount)+"x)");
        }
        
        stage.getActors().removeValue(shopBtn, false);
        stage.getActors().removeValue(pauseBtn, false);
        if(!stage.getActors().contains(shopMenu, true)){
            stage.getActors().add(shopMenu);
        }
        if(!stage.getActors().contains(xBtn, true)){
            stage.getActors().add(xBtn);
        }
        if(!stage.getActors().contains(shopWalletLabel, true)){
            stage.getActors().add(shopWalletLabel);
        }
        if(!stage.getActors().contains(buyChefBtn, true)){
            stage.getActors().add(buyChefBtn);
        }
        if(!stage.getActors().contains(chefPriceLabel, true)){
            stage.getActors().add(chefPriceLabel);
        }
        if(!stage.getActors().contains(buyChoppingBoardBtn, true)){
            stage.getActors().add(buyChoppingBoardBtn);
        }
        if(!stage.getActors().contains(choppingBoardPriceLabel, true)){
            stage.getActors().add(choppingBoardPriceLabel);
        }
        if(!stage.getActors().contains(buyPanBtn, true)){
            stage.getActors().add(buyPanBtn);
        }
        if(!stage.getActors().contains(panPriceLabel, true)){
            stage.getActors().add(panPriceLabel);
        }
        if(!stage.getActors().contains(buyOvenBtn, true)){
            stage.getActors().add(buyOvenBtn);
        }
        if(!stage.getActors().contains(ovenPriceLabel, true)){
            stage.getActors().add(ovenPriceLabel);
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
        stage.getActors().removeValue(shopWalletLabel,false);
        stage.getActors().removeValue(buyChefBtn,false);
        stage.getActors().removeValue(chefPriceLabel,false);
        stage.getActors().removeValue(buyChoppingBoardBtn,false);
        stage.getActors().removeValue(choppingBoardPriceLabel,false);
        stage.getActors().removeValue(buyPanBtn,false);
        stage.getActors().removeValue(panPriceLabel,false);
        stage.getActors().removeValue(buyOvenBtn,false);
        stage.getActors().removeValue(ovenPriceLabel,false);
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
        if(helpBtn.isPressed()){
            if(!stage.getActors().contains(helpImage, true)){
                stage.getActors().add(helpImage);
            }
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
        if(buyChoppingBoardBtn.isPressed()){
            if(choppingBoardAvailable&&(score >= BOARD_PRICE)){
                for(ChoppingBoard board : screen.getChoppingBoards()){
                    if(board.isLocked()){
                        board.unlock();
                        score -= BOARD_PRICE;
                        scoreLabel.setText(Integer.toString(score));
                        choppingBoardAvailable = false;
                        break;
                    }
                }
                hideShop();
            }
        }
        if(buyPanBtn.isPressed()){
            if(panAvailable&&(score >= PAN_PRICE)){
                for(Pan pan : screen.getPans()){
                    if(pan.isLocked()){
                        pan.unlock();
                        score -= PAN_PRICE;
                        scoreLabel.setText(Integer.toString(score));
                        panAvailable = false;
                        break;
                    }
                }
                hideShop();
            }
        }
        if(buyOvenBtn.isPressed()){
            if(ovenAvailable&&(score >= OVEN_PRICE)){
                for(Oven oven : screen.getOvens()){
                    if(oven.isLocked()){
                        oven.unlock();
                        score -= OVEN_PRICE;
                        scoreLabel.setText(Integer.toString(score));
                        ovenPriceLabel.setStyle(new LabelStyle(font, Color.BLACK));
                        ovenPriceLabel.setText("unavailable");
                        ovenAvailable = false;
                        break;
                    }
                }
                hideShop();
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
            int choppingBoardCount = 0;
            int panCount = 0;
            int ovenCount = 0;
            for(ChoppingBoard board : screen.getChoppingBoards()){
                if(!board.isLocked()){
                    choppingBoardCount++;
                }
            }
            for(Pan pan : screen.getPans()){
                if(!pan.isLocked()){
                    panCount++;
                }
            }
            for(Oven oven : screen.getOvens()){
                if(!oven.isLocked()){
                    ovenCount++;
                }
            }
            String currentDish = "currentOrderDish";
            saving.putInteger("choppingBoardCount", choppingBoardCount);
            saving.putInteger("panCount", panCount);
            saving.putInteger("ovenCount", ovenCount);
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
    public void removeBar(ProgressBar bar){
        bar.setValue(0);
        this.stage.getActors().removeValue(bar, false);
        bars.remove(bar);
    }
}
