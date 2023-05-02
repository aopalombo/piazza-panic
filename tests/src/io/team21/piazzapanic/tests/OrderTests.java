package io.team21.piazzapanic.tests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import Recipes.Order;

@RunWith(GdxTestRunner.class)
public class OrderTests {
    @Test
    public void orderInstance(){
        int numOfDishes = 2;
        Order orderTest = new Order(numOfDishes);
        assertTrue("This test only passes if the correct amount of dishes was created", (orderTest.dishes.size() == numOfDishes));
        assertFalse("This test only passes if the correct amount of dishes was created", (orderTest.dishes.size() == 100));

        assertTrue("This test passes if the dishes generated are random and not alike",orderTest.dishes.get(0)!=orderTest.dishes.get(1));
        assertFalse("This test passes if the dishes generated are random and not alike",orderTest.dishes.get(0)==orderTest.dishes.get(1));
    }

    @Test
    public void orderSaveInstance(){
        String dish1test = "Recipes.JacketPotatoRecipe";
        String dish2test = "Recipes.BurgerRecipe";
        String dish3test = "none";

        Order orderTest = new Order(dish1test, dish2test, dish3test);

        assertTrue("This test only passes if the correct amount of dishes was created from a save", (orderTest.dishes.size() == 2));
        assertFalse("This test only passes if the correct amount of dishes was created from a save", (orderTest.dishes.size() == 1));
    }

    @Test
    public void orderComplete(){
        String dish1test = "Recipes.JacketPotatoRecipe";
        String dish2test = "Recipes.BurgerRecipe";
        String dish3test = "none";

        Order orderTest = new Order(dish1test, dish2test, dish3test);
        assertFalse("This test only passes if the order is not complete", orderTest.isComplete());
    }
}
