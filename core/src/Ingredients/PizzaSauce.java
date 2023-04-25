package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class PizzaSauce extends Ingredient{

    /**
     * The PizzaSauce class represents a specific type of ingredient in the game, pizza sauce.
     * It extends the {@link Ingredient} class and has a preparation time and cooking time.
     * The PizzaSauce class sets up an ArrayList of textures for its different skins.
     */

    public PizzaSauce(float prepareTime, float cookTime) {
        super(prepareTime, cookTime);
        super.setPrepared();
        super.tex = new ArrayList<>();
        tex.add(new Texture("Food/Ingredients/PizzaSauce.png"));
        tex.add(new Texture("Food/Ingredients/PizzaSauce.png"));
        tex.add(new Texture("Food/Failed_Ingredient.png"));
    }
}
