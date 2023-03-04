package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;
import Ingredients.PizzaDough;

public class PizzaDoughStation extends InteractiveTileObject{
    /**
     * Constructor for PizzaDoughStation.
     * Creates a new PizzaDoughStation with a Box2D body and fixture.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public PizzaDoughStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    /**
     * Gets pizza dough from the station.
     *
     * @return A new PizzaDough object.
     */
    public Ingredient getIngredient() {
        return new PizzaDough(0, 3);
    }
}
