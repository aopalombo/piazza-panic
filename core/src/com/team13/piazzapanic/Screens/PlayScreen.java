package com.team13.piazzapanic.Screens;

import Ingredients.*;
import Recipes.*;
import Sprites.*;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team13.piazzapanic.HUD;
import com.team13.piazzapanic.MainGame;
import java.util.ArrayList;

/**
 * The PlayScreen class is responsible for displaying the game to the user and handling the user's interactions.
 * The PlayScreen class implements the Screen interface which is part of the LibGDX framework.
 *
 * The PlayScreen class contains several important fields, including the game instance, stage instance, viewport instance,
 * and several other helper classes and variables. The game instance is used to access the global game configuration and
 * to switch between screens. The stage instance is used to display the graphics and handle user interactions, while the
 * viewport instance is used to manage the scaling and resizing of the game window.
 *
 * The PlayScreen class also contains several methods for initializing and updating the game state, including the
 * constructor, show(), render(), update(), and dispose() methods. The constructor sets up the stage, viewport, and
 * other helper classes and variables. The show() method is called when the PlayScreen becomes the active screen. The
 * render() method is called repeatedly to render the game graphics and update the game state. The update() method is
 * called to update the game state and handle user inputs. The dispose() method is called when the PlayScreen is no longer
 * needed and is used to clean up resources and prevent memory leaks.
 */


public class PlayScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera gamecam;
    private final Viewport gameport;
    private final HUD hud;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final World world= new World(new Vector2(0,0), true);
    private Chef chef1 = new Chef(this.world, 31.5F,65);
    private Chef chef2 = new Chef(this.world, 128,65);
    public Chef chef3 = new Chef(this.world, 80,93);
    public ArrayList<Chef> chefs = new ArrayList<Chef>();

    private Chef controlledChef;
    private Chef lastChef = chef2;

    public ArrayList<Order> ordersArray = new ArrayList<Order>();

    public PlateStation plateStation;


    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;
    private float timeSecondsCount = 0f;

    private int orderCount;
    public static int orderTime = 40;

    private float chefSpeedMultiplier = 1f;
    private int moneyMultiplier = 1;
    private float cookSpeedMultiplier = 1f;

    private boolean endless = false;

    private int dishAmount = 1;
    private Preferences saving = Gdx.app.getPreferences("userData");
    private boolean resume;

    private Integer currentOrderNum = 1;

    private ArrayList<ChoppingBoard> boards = new ArrayList<ChoppingBoard>();
    private ArrayList<Pan> pans = new ArrayList<Pan>();
    private ArrayList<Oven> ovens = new ArrayList<Oven>();


    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game, String difficulty, boolean resume){
        this.game = game;
        this.resume = resume;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD
        hud = new HUD(game.batch, game, difficulty,resume, this);
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        new B2WorldCreator(world, map, this, resume);
        chef1.defineChef();
        chef2.defineChef();
        chefs.add(chef1);
        chefs.add(chef2);

        controlledChef = chef1;
        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");
        if(resume){
            orderCount = saving.getInteger("countOrder");
            orderCount -= saving.getInteger("currentOrderNum",1);
            currentOrderNum = saving.getInteger("currentOrderNum", 1);
        } else {
            if(difficulty == "easy"){
                this.orderCount = 5;
            } else if(difficulty == "normal"){
                this.orderCount = 8;
            } else if(difficulty == "hard"){
                this.orderCount = 11;
            } else if(difficulty == "endless"){
                this.orderCount = 10;
                this.endless = true;
            }
        }
        hud.setNumOrders(orderCount);
        Gdx.input.setInputProcessor(hud.stage);
    }

    public void addChoppingBoard(ChoppingBoard board){
        boards.add(board);
    }

    public ArrayList<ChoppingBoard> getChoppingBoards(){
        return boards;
    }

    public void addPan(Pan pan){
        pans.add(pan);
    }

    public ArrayList<Pan> getPans(){
        return pans;
    }

    public void addOven(Oven oven){
        ovens.add(oven);
    }

    public ArrayList<Oven> getOvens(){
        return ovens;
    }

    @Override
    public void show(){

    }


    /**
     * The handleInput method is responsible for handling the input events of the game such as movement and interaction with objects.
     *
     * It checks if the 'R' key is just pressed and both chefs have the user control, if so,
     * it switches the control between the two chefs.
     *
     * If the controlled chef does not have the user control,
     * the method checks if chef1 or chef2 have the user control and sets the control to that chef.
     *
     * If the controlled chef has the user control,
     * it checks if the 'W', 'A', 'S', or 'D' keys are pressed and sets the velocity of the chef accordingly.
     *
     * If the 'E' key is just pressed and the chef is touching a tile,
     * it checks the type of tile and sets the chef's in-hands ingredient accordingly.
     *
     * The method also sets the direction of the chef based on its linear velocity.
     *
     * @param dt is the time delta between the current and previous frame.
     */

    public void handleInput(float dt){
        for(int i = 0; i<chefs.size();i++){
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1+i)){
                lastChef = controlledChef;
                controlledChef = chefs.get(i);
                for(int j = 0; j<chefs.size();j++){
                    if(j!=i){
                        chefs.get(j).b2body.setLinearVelocity(0,0);
                    }
                }
            }
        }
        if (controlledChef.getUserControlChef()) {
            //move chef using WASD
            controlledChef.move(Gdx.input.isKeyPressed(Input.Keys.A),Gdx.input.isKeyPressed(Input.Keys.D),
                                Gdx.input.isKeyPressed(Input.Keys.S), Gdx.input.isKeyPressed(Input.Keys.W),
                                chefSpeedMultiplier);
            if (controlledChef.getInHandsIng() != null && Gdx.input.isKeyJustPressed(Input.Keys.F)){
                controlledChef.getInHandsIng().setFailed();
                System.out.println("Failed current");
            }
        }
        if (controlledChef.b2body.getLinearVelocity().x > 0){
            controlledChef.notificationSetBounds("Right");
        }
        if (controlledChef.b2body.getLinearVelocity().x < 0){
            controlledChef.notificationSetBounds("Left");
        }
        if (controlledChef.b2body.getLinearVelocity().y > 0){
            controlledChef.notificationSetBounds("Up");
        }
        if (controlledChef.b2body.getLinearVelocity().y < 0){
            controlledChef.notificationSetBounds("Down");
        }


        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
                if(controlledChef.getTouchingTile() != null){
                    InteractiveTileObject tile = (InteractiveTileObject) controlledChef.getTouchingTile().getUserData();
                    String tileName = tile.getClass().getName();
                    if (controlledChef.getInHandsIng() == null && controlledChef.getInHandsRecipe() == null) {
                        switch (tileName) {
                            case "Sprites.TomatoStation":
                                controlledChef.setInHandsIng(new Tomato(2*cookSpeedMultiplier, 3));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.PizzaSauceStation":
                                controlledChef.setInHandsIng(new PizzaSauce(0, 0));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.PotatoStation":
                                controlledChef.setInHandsIng(new Potato(0, 3*cookSpeedMultiplier));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.PizzaDoughStation":
                                controlledChef.setInHandsIng(new PizzaDough(0, 0));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.CheeseStation":
                                controlledChef.setInHandsIng(new Cheese(2*cookSpeedMultiplier,0));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.BunsStation":
                                controlledChef.setInHandsIng(new Bun(0,3*cookSpeedMultiplier));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.OnionStation":
                                controlledChef.setInHandsIng(new Onion(2*cookSpeedMultiplier,0));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.SteakStation":
                                controlledChef.setInHandsIng(new Steak(2*cookSpeedMultiplier,3*cookSpeedMultiplier));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.LettuceStation":
                                controlledChef.setInHandsIng(new Lettuce(2*cookSpeedMultiplier, 0));
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.PlateStation":
                                if(plateStation.getPlate().size() > 0 || plateStation.getCompletedRecipe() != null){
                                    controlledChef.pickUpItemFrom(tile);

                                }

                        }
                    } else {
                        controlledChef.setFailState();
                        switch (tileName) {
                            case "Sprites.Bin":
                                controlledChef.setInHandsRecipe(null);
                                controlledChef.setInHandsIng(null);
                                controlledChef.setChefSkin(null);
                                break;

                            case "Sprites.ChoppingBoard":
                                if(controlledChef.getInHandsIng() != null){
                                    ChoppingBoard choppingBoard = (ChoppingBoard) tile;
                                    if((controlledChef.getInHandsIng().prepareTime > 0)&&(!choppingBoard.isLocked())){
                                        hud.createProgressBar(Math.round(controlledChef.b2body.getPosition().x*MainGame.PPM)-14,Math.round(controlledChef.b2body.getPosition().y*MainGame.PPM)+12, controlledChef,6*cookSpeedMultiplier);
                                        controlledChef.setUserControlChef(false);
                                        Chef temp = controlledChef;
                                        controlledChef = lastChef;
                                        lastChef = temp;
                                    }
                                }
                               break;
                            case "Sprites.PlateStation":
                                if (controlledChef.getInHandsRecipe() == null){
                                controlledChef.dropItemOn(tile, controlledChef.getInHandsIng());
                                controlledChef.setChefSkin(null);
                            }
                                break;
                            case "Sprites.Pan":
                                if((controlledChef.getInHandsIng() != null)&&(controlledChef.getInHandsIng().getClass().getName() != "Ingredients.Potato")) {
                                    Pan pan = (Pan) tile;
                                    if (controlledChef.getInHandsIng().isPrepared() && controlledChef.getInHandsIng().cookTime > 0 && !pan.isLocked()){
                                        hud.createProgressBar(Math.round(controlledChef.b2body.getPosition().x*MainGame.PPM)-14,Math.round(controlledChef.b2body.getPosition().y*MainGame.PPM)+12, controlledChef,9*cookSpeedMultiplier);
                                        controlledChef.setUserControlChef(false);
                                        Chef temp = controlledChef;
                                        controlledChef = lastChef;
                                        lastChef = temp;
                                    }
                                }
                                break;
                            case "Sprites.Oven":
                            if((controlledChef.getInHandsIng() != null)&&(controlledChef.getInHandsIng().getClass().getName() != "Ingredients.Steak")) {
                                Oven oven = (Oven) tile;
                                if (controlledChef.getInHandsIng().isPrepared() && controlledChef.getInHandsIng().cookTime > 0 && !oven.isLocked()){
                                    hud.createProgressBar(Math.round(controlledChef.b2body.getPosition().x*MainGame.PPM)-14,Math.round(controlledChef.b2body.getPosition().y*MainGame.PPM)+12, controlledChef,9*cookSpeedMultiplier);
                                    controlledChef.setUserControlChef(false);
                                    Chef temp = controlledChef;
                                    controlledChef = lastChef;
                                    lastChef = temp;
                                }
                                }
                                break;
                            case "Sprites.CompletedDishStation":
                                if((controlledChef.getInHandsIng() != null)&&(controlledChef.getInHandsIng() instanceof UnbakedPizza)&&(controlledChef.getInHandsIng().cookTime <= 0)){
                                    controlledChef.setInHandsIng(null);
                                    controlledChef.setInHandsRecipe(new PizzaRecipe());
                                }
                                if (controlledChef.getInHandsRecipe() != null){
                                    if(ordersArray.get(0).completeDish(controlledChef.getInHandsRecipe())){
                                        controlledChef.dropItemOn(tile);
                                        controlledChef.setChefSkin(null);
                                        if(ordersArray.get(0).isComplete()){
                                            if(((currentOrderNum)%2) == 0){
                                                hud.generatePowerUp();
                                                activatePowerUp();
                                            }
                                            if(ordersArray.size()==1){
                                                scenarioComplete = Boolean.TRUE;
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }

    /**
     * The update method updates the game elements, such as camera and characters,
     * based on a specified time interval "dt".
     * @param dt time interval for the update
    */
    public void update(float dt){
        handleInput(dt);

        gamecam.update();
        renderer.setView(gamecam);
        for(int i = 0; i<chefs.size();i++){
            chefs.get(i).update(dt);
        }
        world.step(1/60f, 6, 2);

        if(hud.repPoints == 0){
            scenarioComplete = true;
        }

    }

    public void createResumedOrder(){
        ordersArray.add(new Order(saving.getString("currentOrderDish1", "none"),saving.getString("currentOrderDish2", "none"),saving.getString("currentOrderDish3", "none")));
        if(Math.floor(currentOrderNum/3)>=3){
            this.dishAmount = 3;
        } else {
            this.dishAmount+=Math.floor(currentOrderNum/3);
        }
        createOrder();
    }

    /**
     * Creates the orders randomly and adds to an array, updates the HUD.
     */
    public void createOrder() {
        Order order;
        for(int i = 1; i<orderCount+1; i++){
            order = new Order(dishAmount);
            //every three orders increase the amount of dishes per order
            if(i%3 == 0){
                dishAmount++;
                //no more than 3 dishes per order
                if(dishAmount>3){
                    dishAmount = 3;
                }
            }
            ordersArray.add(order);
        }
        hud.updateOrder(Boolean.FALSE, currentOrderNum);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {
            hud.updateScore(Boolean.TRUE, orderTime, moneyMultiplier,ordersArray.get(0));
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            if ((ordersArray.get(0).isComplete()) || (ordersArray.get(0).orderTime == 0)) {
                hud.updateScore(Boolean.FALSE, orderTime , moneyMultiplier,ordersArray.get(0));
                ordersArray.remove(0);
                currentOrderNum +=1;
                hud.updateOrder(Boolean.FALSE, currentOrderNum);
                return;
            }
            if(!hud.isPaused()){
                ordersArray.get(0).create(trayX, trayY, game.batch);
            }
        }
    }

    /**

     The render method updates the screen by calling the update method with the given delta time, and rendering the graphics of the game.

     It updates the HUD time, clears the screen, and renders the renderer and the hud.

     Additionally, it checks the state of the game and draws the ingredients, completed recipes, and notifications on the screen.

     @param delta The time in seconds since the last frame.
     */
    @Override
    public void render(float delta){
        if(!hud.isPaused()){
            update(delta);
        }

        //Execute handleEvent each 1 second
        timeSeconds +=Gdx.graphics.getDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if(resume && !createdOrder){
            createResumedOrder();
            createdOrder = Boolean.TRUE;
        } else if (!createdOrder){
            if(Math.round(timeSecondsCount) == 5){
                createdOrder = Boolean.TRUE;
                createOrder();
            }
        }
        float period = 1f;
        if(timeSeconds > period) {
            timeSeconds -= period;
            if(ordersArray.size()>0){
                hud.updateTime(scenarioComplete,ordersArray.get(0));
            } else {hud.updateTime(scenarioComplete,null);}
        }

        Gdx.gl.glClear(1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        updateOrder();
        disablePowerUps();
        hud.updateProgressBars();
        if(!hud.isPaused()){
            for(int i = 0; i<chefs.size();i++){
                chefs.get(i).draw(game.batch);
            }
            controlledChef.drawNotification(game.batch);
        }
        hud.handleButtons();
        if (plateStation.getPlate().size() > 0){
            for(Object ing : plateStation.getPlate()){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
            }
        } else if (plateStation.getCompletedRecipe() != null){
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
        }
        for(int i = 0; i<chefs.size();i++){
            if (!chefs.get(i).getUserControlChef()) {
                if (chefs.get(i).getTouchingTile() != null && chefs.get(i).getInHandsIng() != null){
                    if (chefs.get(i).getTouchingTile().getUserData() instanceof InteractiveTileObject){
                        chefs.get(i).displayIngStatic(game.batch);
                    }
                }
            }
            if (chefs.get(i).previousInHandRecipe != null){
                chefs.get(i).displayIngDynamic(game.batch);
            }
        }
        for(ChoppingBoard board : boards){
            board.draw(hud);
        }
        for(Pan pan : pans){
            pan.draw(hud);
        }
        for(Oven oven : ovens){
            oven.draw(hud);
        }
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.T)){
            if(!chefs.contains(chef3)){
                chef3.defineChef();
                chefs.add(chef3);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            hud.unPause();
            hud.hideShop();
        }
        //if in endless mode and orders are done generate new ones
        if((ordersArray.size()==1)&&(endless)){
                createOrder();
        }
    }

    private void activatePowerUp() {
        if(hud.getPowerUp() ==  "2X SPEED"){
            chefSpeedMultiplier = 1.75f;
        } else if (hud.getPowerUp() ==  "2X MONEY"){
            moneyMultiplier = 2;
        } else if(hud.getPowerUp() ==  "FREEZE"){
            hud.freezeTime();
        }else if(hud.getPowerUp() ==  "SPEEDY"){
            cookSpeedMultiplier = 0.5f;
        }
    }

    private void disablePowerUps(){
        if(hud.getPowerUp() == ""){
            chefSpeedMultiplier = 1f;
            moneyMultiplier = 1;
            hud.unfreezeTime();
            cookSpeedMultiplier = 1f;
        }
    }

    @Override
    public void resize(int width, int height){
        gameport.update(width, height);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){
        
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        world.dispose();
        hud.dispose();
    }
}
