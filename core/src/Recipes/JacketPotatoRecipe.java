package Recipes;

import Ingredients.*;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 * The JacketPotatoRecipe class is a subclass of the Recipe class.
 * It holds an ArrayList of ingredients that makes up a jacket potato dish, and the texture of the completed jacket potato dish.
 * The salad dish consists of {@link Ingredients.Lettuce}, {@link Ingredients.Tomato} and {@link Ingredients.Onion} ingredients.
 */

public class JacketPotatoRecipe extends Recipe {
    public JacketPotatoRecipe(){
        super.ingredients = new ArrayList<>();
        ingredients.add(new Potato(0,0));
        ingredients.add(new Cheese(0,0));
        ingredients.add(new PizzaSauce(0,0));
        completedImg = new Texture("Food/JacketPotato.png");
    }
}

