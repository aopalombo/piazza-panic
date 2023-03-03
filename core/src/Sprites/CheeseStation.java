package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Cheese;
import Ingredients.Ingredient;

public class CheeseStation extends InteractiveTileObject{
    /**
     * Constructor for CheeseStation.
     * Creates a new CheeseStation with a Box2D body and fixture.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public CheeseStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    /**
     * Gets cheese from the station.
     *
     * @return A new Cheese object.
     */
    public Ingredient getIngredient() {
        return new Cheese(0, 3);
    }
}
