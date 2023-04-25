package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class UnbakedPizza extends Ingredient{

    /**
     * The UnbakedPizza class represents a unbaked pizza in the game.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The Tomato class sets up an ArrayList of textures for its different skins.
     */

    public UnbakedPizza(float prepareTime, float cookTime) {
        super(prepareTime, cookTime);
        super.tex = new ArrayList<>();
        tex.add(new Texture("Food/UnbakedPizza.png"));
        tex.add(new Texture("Food/UnbakedPizza.png"));
        tex.add(new Texture("Food/Failed_Ingredient.png"));
        tex.add(new Texture("Food/Pizza.png"));

    }
}
