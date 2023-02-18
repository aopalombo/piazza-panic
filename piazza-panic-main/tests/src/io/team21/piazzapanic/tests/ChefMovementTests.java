package io.team21.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import Sprites.Chef;

@RunWith(GdxTestRunner.class)
public class ChefMovementTests {

    @Test
    public void moveUp(){
        World world = new World(new Vector2(0, 0),true);
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
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
        float initialVelocity = chefTest.b2body.getLinearVelocity().x;
        chefTest.move(false,true,false,false,1);
        //assertTrue("wow", true);
        assertTrue("This test only passes if the chef moves right",chefTest.b2body.getLinearVelocity().x>initialVelocity);
    }

    @Test
    public void moveLeft(){
        World world = new World(new Vector2(0, 0),true);
        int x = 20;
        int y = 20;
        Chef chefTest = new Chef(world, x, y);
        float initialVelocity = chefTest.b2body.getLinearVelocity().x;
        chefTest.move(true,false,false,false,1);
        //assertTrue("wow", true);
        assertTrue("This test only passes if the chef moves left",chefTest.b2body.getLinearVelocity().x<initialVelocity);
    }
}
