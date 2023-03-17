package Recipes;

import Ingredients.*;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;


/**

 The PizzaRecipe class is a subclass of the Recipe class and represents a pizza dish in the kitchen game.
 It holds an ArrayList of ingredients needed to make a pizza and a Texture of the completed dish image.
 The ingredients in the ArrayList consist of a {@link Ingredients.Bun} and a {@link Ingredients.Steak}.
 */


public class UnbakedPizzaRecipe extends Recipe{

    public UnbakedPizzaRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new PizzaDough(0,3));
        ingredients.add(new Cheese(0,0));
        ingredients.add(new PizzaSauce(0,0));
        completedImg = new Texture("Food/UnbakedPizza.png");
    }

    public UnbakedPizza getUnbackedPizza(){
        return new UnbakedPizza(0, 5);
    }
}
