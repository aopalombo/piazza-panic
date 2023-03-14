package Recipe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.MainGame;

/**
 * The `Order` class extends the `Sprite` class and represents a recipe order
 * in the game.
 */
public class Dish extends Sprite {
    /** The `Recipe` object associated with this order. */
    public Recipe recipe;
    /** A flag indicating whether the order has been completed. */
    public Boolean orderComplete;
    /** The image representing this order. */
    public Texture orderImg;
    /** Time for order to be completed*/
    public Integer dishTime;

    /**
     * Constructor for the `Order` class.
     *
     * @param recipe The `Recipe` object associated with this order.
     * @param orderImg The image representing this order.
     */
    public Dish(Recipe recipe, Texture orderImg, Integer time) {
        this.recipe = recipe;
        this.orderImg = orderImg;
        this.orderComplete = false;
        this.dishTime = time;
    }

    /**
     * Creates the order image and adds it to the given `SpriteBatch`.
     *
     * @param x The x coordinate of the order image.
     * @param y The y coordinate of the order image.
     * @param batch The `SpriteBatch` to add the order image to.
     */
    public void create(float x, float y, SpriteBatch batch) {
        Sprite sprite = new Sprite(orderImg);
        float adjustedX = x - (25 / MainGame.PPM);
        float adjustedY = y + (7 / MainGame.PPM);
        sprite.setBounds(adjustedX, adjustedY, 33 / MainGame.PPM, 28 / MainGame.PPM);
        sprite.draw(batch);
    }
}
