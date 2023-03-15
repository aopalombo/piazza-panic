package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import Ingredients.Ingredient;
import Ingredients.PizzaSauce;

public class PizzaSauceStation extends InteractiveTileObject{
    /**
     * Constructor for PizzaSauceStation.
     * Creates a new PizzaSauceStation with a Box2D body and fixture.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public PizzaSauceStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }
    /**
     * Gets pizza sauce from the station.
     *
     * @return A new PizzaSauce object.
     */
    public Ingredient getIngredient() {
        return new PizzaSauce(0, 3);
    }
}
