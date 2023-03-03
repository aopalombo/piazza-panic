package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import Ingredients.Ingredient;
import Ingredients.Potato;

public class PotatoStation extends InteractiveTileObject{
    /**
     * Constructor for PotatoStation.
     * Creates a new PotatoStation with a Box2D body and fixture.
     *
     * @param world The playable world.
     * @param map The tiled map.
     * @param bdef The body definition of a tile.
     * @param rectangle Rectangle shape.
     */
    public PotatoStation(World world, TiledMap map, BodyDef bdef, Rectangle rectangle) {
        super(world, map, bdef, rectangle);
        fixture.setUserData(this);
    }

    /**
     * Gets a potato from the station.
     *
     * @return A new Potato object.
     */
    public Ingredient getIngredient() {
        return new Potato(0, 3);
    }
    
}
