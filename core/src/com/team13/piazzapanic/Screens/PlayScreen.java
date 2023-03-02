package com.team13.piazzapanic.Screens;

import Ingredients.Ingredient;
import Recipe.Recipe;
import Sprites.*;
import Recipe.Order;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team13.piazzapanic.HUD;
import com.team13.piazzapanic.MainGame;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    private final Chef chef1 = new Chef(this.world, 31.5F,65);
    private final Chef chef2 = new Chef(this.world, 128,65);

    private Chef controlledChef;

    public ArrayList<Order> ordersArray = new ArrayList<Order>();

    public PlateStation plateStation;


    public Boolean scenarioComplete;
    public Boolean createdOrder;

    public static float trayX;
    public static float trayY;

    private float timeSeconds = 0f;

    private float timeSecondsCount = 0f;

    private float multiplier = 1f;

    private ArrayList<ProgressBar> bars = new ArrayList<ProgressBar>();
    /**
     * PlayScreen constructor initializes the game instance, sets initial conditions for scenarioComplete and createdOrder,
     * creates and initializes game camera and viewport,
     * creates and initializes HUD and orders hud, loads and initializes the map,
     * creates and initializes world, creates and initializes chefs and sets them, sets contact listener for world, and initializes ordersArray.
     * @param game The MainGame instance that the PlayScreen will be a part of.
     */

    public PlayScreen(MainGame game){
        this.game = game;
        scenarioComplete = Boolean.FALSE;
        createdOrder = Boolean.FALSE;
        gamecam = new OrthographicCamera();
        // FitViewport to maintain aspect ratio whilst scaling to screen size
        gameport = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);
        // create HUD for score & time
        hud = new HUD(game.batch);
        // create map
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        map = mapLoader.load("Kitchen.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        new B2WorldCreator(world, map, this);

        controlledChef = chef1;
        world.setContactListener(new WorldContactListener());
        controlledChef.notificationSetBounds("Down");
        //createProgressBar(controlledChef);
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
        if ((Gdx.input.isKeyJustPressed(Input.Keys.R) &&
                chef1.getUserControlChef() &&
                chef2.getUserControlChef())) {
            if (controlledChef.equals(chef1)) {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef2;
            } else {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef1;
            }
        }
        if (!controlledChef.getUserControlChef()){
            if (chef1.getUserControlChef()){
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef1;
            } else if(chef2.getUserControlChef()) {
                controlledChef.b2body.setLinearVelocity(0, 0);
                controlledChef = chef2;
            }
        }
        if (controlledChef.getUserControlChef()) {
            //move chef using WASD
            controlledChef.move(Gdx.input.isKeyPressed(Input.Keys.A),Gdx.input.isKeyPressed(Input.Keys.D),
                                Gdx.input.isKeyPressed(Input.Keys.S), Gdx.input.isKeyPressed(Input.Keys.W),
                                multiplier);
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
                                TomatoStation tomatoTile = (TomatoStation) tile;
                                controlledChef.setInHandsIng(tomatoTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.BunsStation":
                                BunsStation bunTile = (BunsStation) tile;
                                controlledChef.setInHandsIng(bunTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.OnionStation":
                                OnionStation onionTile = (OnionStation) tile;
                                controlledChef.setInHandsIng(onionTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.SteakStation":
                                SteakStation steakTile = (SteakStation) tile;
                                controlledChef.setInHandsIng(steakTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.LettuceStation":
                                LettuceStation lettuceTile = (LettuceStation) tile;
                                controlledChef.setInHandsIng(lettuceTile.getIngredient());
                                controlledChef.setChefSkin(controlledChef.getInHandsIng());
                                break;
                            case "Sprites.PlateStation":
                                if(plateStation.getPlate().size() > 0 || plateStation.getCompletedRecipe() != null){
                                    controlledChef.pickUpItemFrom(tile);

                                }

                        }
                    } else {
                        switch (tileName) {
                            case "Sprites.Bin":
                                controlledChef.setInHandsRecipe(null);
                                controlledChef.setInHandsIng(null);
                                controlledChef.setChefSkin(null);
                                break;

                            case "Sprites.ChoppingBoard":
                                if(controlledChef.getInHandsIng() != null){
                                    if(controlledChef.getInHandsIng().prepareTime > 0){
                                        createProgressBar((controlledChef.b2body.getPosition().x*MainGame.PPM)-14,(controlledChef.b2body.getPosition().y*MainGame.PPM)+12, controlledChef,6);
                                        controlledChef.setUserControlChef(false);
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
                                if(controlledChef.getInHandsIng() != null) {
                                    if (controlledChef.getInHandsIng().isPrepared() && controlledChef.getInHandsIng().cookTime > 0){
                                        createProgressBar((controlledChef.b2body.getPosition().x*MainGame.PPM)-14,(controlledChef.b2body.getPosition().y*MainGame.PPM)+12, controlledChef,9);
                                        controlledChef.setUserControlChef(false);
                                    }
                                }

                                break;
                            case "Sprites.CompletedDishStation":
                                if (controlledChef.getInHandsRecipe() != null){
                                    if(controlledChef.getInHandsRecipe().getClass().equals(ordersArray.get(0).recipe.getClass())){
                                        controlledChef.dropItemOn(tile);
                                        ordersArray.get(0).orderComplete = true;
                                        controlledChef.setChefSkin(null);
                                        if(ordersArray.size()==1){
                                            scenarioComplete = Boolean.TRUE;
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
        chef1.update(dt);
        chef2.update(dt);
        world.step(1/60f, 6, 2);

        if(hud.repPoints == 0){
            scenarioComplete = true;
        }

    }

    /**
     * Creates the orders randomly and adds to an array, updates the HUD.
     */
    public void createOrder() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        Texture burger_recipe = new Texture("Food/burger_recipe.png");
        Texture salad_recipe = new Texture("Food/salad_recipe.png");
        Order order;

        for(int i = 0; i<5; i++){
            if(randomNum==1) {
                order = new Order(PlateStation.burgerRecipe, burger_recipe, (6 - ordersArray.size()) * 35);
            }
            else {
                order = new Order(PlateStation.saladRecipe, salad_recipe, (6 - ordersArray.size()) * 35);
            }
            ordersArray.add(order);
            randomNum = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        }
        hud.updateOrder(Boolean.FALSE, 1);
    }

    /**
     * Updates the orders as they are completed, or if the game scenario has been completed.
     */
    public void updateOrder(){
        if(scenarioComplete==Boolean.TRUE) {
            hud.updateScore(Boolean.TRUE, (6 - ordersArray.size()) * 35);
            hud.updateOrder(Boolean.TRUE, 0);
            return;
        }
        if(ordersArray.size() != 0) {
            if ((ordersArray.get(0).orderComplete) || (ordersArray.get(0).orderTime == 0)) {
                hud.updateScore(Boolean.FALSE, (6 - ordersArray.size()) * 35);
                ordersArray.remove(0);
                hud.updateOrder(Boolean.FALSE, 6 - ordersArray.size());
                return;
            }
            ordersArray.get(0).create(trayX, trayY, game.batch);
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
        update(delta);

        //Execute handleEvent each 1 second
        timeSeconds +=Gdx.graphics.getDeltaTime();
        timeSecondsCount += Gdx.graphics.getDeltaTime();

        if(Math.round(timeSecondsCount) == 5 && createdOrder == Boolean.FALSE){
            createdOrder = Boolean.TRUE;
            createOrder();
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
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        updateOrder();
        updateProgressBars();
        chef1.draw(game.batch);
        chef2.draw(game.batch);
        controlledChef.drawNotification(game.batch);
        if (plateStation.getPlate().size() > 0){
            for(Object ing : plateStation.getPlate()){
                Ingredient ingNew = (Ingredient) ing;
                ingNew.create(plateStation.getX(), plateStation.getY(),game.batch);
            }
        } else if (plateStation.getCompletedRecipe() != null){
            Recipe recipeNew = plateStation.getCompletedRecipe();
            recipeNew.create(plateStation.getX(), plateStation.getY(), game.batch);
        }
        if (!chef1.getUserControlChef()) {
            if (chef1.getTouchingTile() != null && chef1.getInHandsIng() != null){
                if (chef1.getTouchingTile().getUserData() instanceof InteractiveTileObject){
                    chef1.displayIngStatic(game.batch);
                }
            }
        }
        if (!chef2.getUserControlChef()) {
            if (chef2.getTouchingTile() != null && chef2.getInHandsIng() != null) {
                if (chef2.getTouchingTile().getUserData() instanceof InteractiveTileObject) {
                    chef2.displayIngStatic(game.batch);
                }
            }
        }
        if (chef1.previousInHandRecipe != null){
            chef1.displayIngDynamic(game.batch);
        }
        if (chef2.previousInHandRecipe != null){
            chef2.displayIngDynamic(game.batch);
        }
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){multiplier = 2f;}
        if(Gdx.input.isKeyPressed(Input.Keys.M)){multiplier = 1f;}
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){game.setScreen(new MainMenuScreen(game));}
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
    
    private void createProgressBar(float x, float y, Chef chef, Integer duration) {
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = getColoredDrawable(20, 5, Color.GREEN);
        style.knob = getColoredDrawable(0, 5, Color.WHITE);
        style.knobAfter = getColoredDrawable(20, 5, Color.WHITE);
        ProgressBar bar = new ProgressBar(0, duration, 0.005f, false, style);
        bar.setWidth(30);
        bar.setHeight(5);
        bar.setValue(15f);
        bar.setX(x);
        bar.setY(y);
        hud.stage.addActor(bar);
        bars.add(bar);
    }

    private void updateProgressBars() {
        if (!bars.isEmpty()) {
            for (ProgressBar bar : bars) {
                bar.setValue(bar.getValue() - 0.05f);
                if (bar.getValue() <= 0) {
                    hud.stage.getActors().removeValue(bar, false);
                    //bars.remove(bar);
                }
            }
        }
    }
    private static TextureRegionDrawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }
}
