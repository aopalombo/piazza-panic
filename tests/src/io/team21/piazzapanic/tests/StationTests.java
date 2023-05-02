package io.team21.piazzapanic.tests;
import static org.junit.Assert.assertTrue;

import Ingredients.*;
import Recipes.SaladRecipe;
import Sprites.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team13.piazzapanic.MainGame;

import Tools.WorldContactListener;

@RunWith(GdxTestRunner.class)
public class StationTests {

    @Test
    public void bunDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        BunsStation bunsStation = new BunsStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(bunsStation.getIngredient() instanceof Bun);
    }

    @Test
    public void cheeseDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        CheeseStation cheeseStation = new CheeseStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(cheeseStation.getIngredient() instanceof Cheese);
    }

    @Test
    public void lettuceDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        LettuceStation lettuceStation = new LettuceStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(lettuceStation.getIngredient() instanceof Lettuce);
    }

    @Test
    public void onionDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        OnionStation onionStation = new OnionStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(onionStation.getIngredient() instanceof Onion);
    }

    @Test
    public void pizzaDoughDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        PizzaDoughStation pizzaDoughStation = new PizzaDoughStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(pizzaDoughStation.getIngredient() instanceof PizzaDough);
    }

    @Test
    public void pizzaSauceDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        PizzaSauceStation pizzaSauceStation = new PizzaSauceStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(pizzaSauceStation.getIngredient() instanceof PizzaSauce);
    }

    @Test
    public void potatoDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        PotatoStation potatoStation = new PotatoStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(potatoStation.getIngredient() instanceof Potato);
    }

    @Test
    public void steakDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        SteakStation steakStation = new SteakStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(steakStation.getIngredient() instanceof Steak);
    }

    @Test
    public void tomatoDispense() {
        World world = new World(new Vector2(0, 0),true);
        TiledMap tiledMap = new TiledMap();
        BodyDef bodyDef = new BodyDef();
        Rectangle rectangle = new Rectangle();
        TomatoStation tomatoStation = new TomatoStation(world, tiledMap, bodyDef, rectangle);
        assertTrue(tomatoStation.getIngredient() instanceof Tomato);
    }

    @Test
    public void recipeRecognisedByPlateStation() {
        World world = new World(new Vector2(0, 0),true);
        world.setContactListener(new WorldContactListener());
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        TiledMap map = mapLoader.load("Kitchen.tmx");
        BodyDef bdef = new BodyDef();
        bdef.position.set(12/ MainGame.PPM, 12 / MainGame.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Rectangle rectangle = new Rectangle(12, 12, 16, 16);

        Chef chefTest = new Chef(world, 10, 10);
        SaladRecipe saladTest = new SaladRecipe();
        Onion onionTest = new Onion(0,0);
        Lettuce lettuceTest = new Lettuce(0,0);
        Tomato tomatoTest = new Tomato(0,0);
        PlateStation plateStationTest = new PlateStation(world, map, bdef, rectangle);
        onionTest.setPrepared();
        lettuceTest.setPrepared();
        tomatoTest.setPrepared();
        chefTest.setInHandsIng(onionTest);
        chefTest.dropItemOn(plateStationTest, chefTest.getInHandsIng());
        chefTest.setInHandsIng(lettuceTest);
        chefTest.dropItemOn(plateStationTest, chefTest.getInHandsIng());
        chefTest.setInHandsIng(tomatoTest);
        chefTest.dropItemOn(plateStationTest, chefTest.getInHandsIng());
        chefTest.pickUpItemFrom(plateStationTest);
        plateStationTest.getCompletedRecipe();
        System.out.println(chefTest.getInHandsRecipe());

        assertTrue("This test only passes when the platestation recognises a recipe and changes it to the dish", chefTest.getInHandsRecipe().getClass().getName()==saladTest.getClass().getName());
    }
}
