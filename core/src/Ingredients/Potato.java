package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Potato extends Ingredient{

    /**

     The Potato class represents a specific type of ingredient in the game.
     It extends the {@link Ingredient} class and has a preparation time and cooking time.
     The Potato class sets the prepared flag to true in the constructor, and sets up an ArrayList of textures for its different skins.

     */

    public Potato(float prepareTime, float cookTime) {
        super(prepareTime, cookTime);
        super.setPrepared();
        super.tex = new ArrayList<>();
        super.tex.add(new Texture("Food/Ingredients/Potato.png"));
        super.tex.add(new Texture("Food/Ingredients/Baked_potato.png"));
        super.tex.add(new Texture("Food/Ingredients/Failed_ingredient.png"));
    }
}
