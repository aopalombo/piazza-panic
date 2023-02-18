package io.team21.piazzapanic.tests;

import static org.junit.Assert.assertEquals;

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
        chefTest.move(false,false,false,true,1);
        //assertTrue("wow", true);
        assertEquals("This test only passes if the chef moves up",chefTest.b2body.getPosition().y, y+0.5,0.001f);
    }
}
