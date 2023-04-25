package Ingredients;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Bun extends Ingredient{

    /**

     The Bun class represents a specific type of ingredient in the game, specifically the burger buns.
     It extends the {@link Ingredient} class and has a preparation time and cooking time.
     The Bun class sets the prepared flag to true in the constructor, and sets up an ArrayList of textures for its different skins.

     */

    //TODO for some reason the buns texture brakes once they are toasted and placed on the plate FIX THIS
    public Bun(float prepareTime, float cookTime) {
        super(prepareTime, cookTime);
        super.setPrepared();
        super.tex = new ArrayList<>();
        super.tex.add(null);
<<<<<<< Updated upstream
        super.tex.add(new Texture("Food/Burger_buns.png"));
        super.tex.add(new Texture("Food/Toasted_burger_buns.png"));
=======
        super.tex.add(new Texture("Food/Ingredients/Burger_buns.png"));
        super.tex.add(new Texture("Food/Ingredients/Toasted_burger_buns.png"));
        super.tex.add(new Texture("Food/Failed_Ingredient.png"));
>>>>>>> Stashed changes
    }
}
