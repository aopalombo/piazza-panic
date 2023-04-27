package io.team21.piazzapanic.tests;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.team13.piazzapanic.MainGame;

import Ingredients.Onion;
import Sprites.Chef;
import Sprites.PlateStation;
import Tools.WorldContactListener;

@RunWith(GdxTestRunner.class)
public class ChefTests {
    @Test
    public void defineChefTest(){
        World world = new World(new Vector2(0, 0),true);
        world.setContactListener(new WorldContactListener());
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        chefTest.defineChef();
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        assertTrue("This test only passes if the chef exists in the world", bodies.contains(chefTest.b2body, true));
        assertTrue("This test only passes if the chef spawns in the correct position in the world", (chefTest.b2body.getPosition().x == (x/MainGame.PPM))&&(chefTest.b2body.getPosition().y == (y/MainGame.PPM)));
    }
    @Test
    public void itemsOnStationTest(){
        World world = new World(new Vector2(0, 0),true);
        world.setContactListener(new WorldContactListener());
        TmxMapLoader mapLoader = new TmxMapLoader(new InternalFileHandleResolver());
        TiledMap map = mapLoader.load("Kitchen.tmx");
        BodyDef bdef = new BodyDef();
        bdef.position.set(12/ MainGame.PPM, 12 / MainGame.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Rectangle rectangle = new Rectangle(12, 12, 16, 16);

        Chef chefTest = new Chef(world, 10, 10);
        Onion onionTest = new Onion(2,0);
        PlateStation plateStationTest = new PlateStation(world, map, bdef, rectangle);
        chefTest.setInHandsIng(onionTest);

        chefTest.dropItemOn(plateStationTest, chefTest.getInHandsIng());
        assertTrue("This test passes if the chef succesfull dropped an ingredient on the station", (chefTest.getInHandsIng()==null)&&(plateStationTest.getPlate().contains(onionTest)));
        chefTest.pickUpItemFrom(plateStationTest);
        assertTrue("This test passes if the chef succesfull picked up an ingredient from the station", chefTest.getInHandsIng()==onionTest);
    }
    @Test
    public void moveUp(){
        World world = new World(new Vector2(0, 0),true);
        world.setContactListener(new WorldContactListener());
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        chefTest.defineChef();
        float initialVelocity = chefTest.b2body.getLinearVelocity().y;
        chefTest.move(false,false,false,true,1);
        assertTrue("This test only passes if the chef moves up",chefTest.b2body.getLinearVelocity().y>initialVelocity);
    }

    @Test
    public void moveDown(){
        World world = new World(new Vector2(0, 0),true);
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        chefTest.defineChef();
        float initialVelocity = chefTest.b2body.getLinearVelocity().y;
        chefTest.move(false,false,true,false,1);
        assertTrue("This test only passes if the chef moves down",chefTest.b2body.getLinearVelocity().y<initialVelocity);
    }

    @Test
    public void moveRight(){
        World world = new World(new Vector2(0, 0),true);
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        chefTest.defineChef();
        float initialVelocity = chefTest.b2body.getLinearVelocity().x;
        chefTest.move(false,true,false,false,1);
        assertTrue("This test only passes if the chef moves right",chefTest.b2body.getLinearVelocity().x>initialVelocity);
    }

    @Test
    public void moveLeft(){
        World world = new World(new Vector2(0, 0),true);
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        chefTest.defineChef();
        float initialVelocity = chefTest.b2body.getLinearVelocity().x;
        chefTest.move(true,false,false,false,1);
        assertTrue("This test only passes if the chef moves left",chefTest.b2body.getLinearVelocity().x<initialVelocity);
    }
}
