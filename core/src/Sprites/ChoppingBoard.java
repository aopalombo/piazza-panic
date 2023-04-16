package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.team13.piazzapanic.HUD;
import com.team13.piazzapanic.MainGame;

/**
 * The ChoppingBoard class extends the InteractiveTileObject class.
 *
 * This class is used to define a chopping board object in the game that can chop up steak into a patty and
 * to cut up fruit and vegetables
 */
public class ChoppingBoard extends InteractiveTileObject {
    private boolean locked;
    private Image lockImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("Food/lock.png"))));
    public ChoppingBoard(World world, TiledMap map, BodyDef bdef, Rectangle rectangle, boolean locked) {
        super(world, map, bdef, rectangle);
        this.locked = locked;
        fixture.setUserData(this);
    }

    public float getX(){
        return super.bdefNew.position.x;
    }

    public float getY(){
        return super.bdefNew.position.y;
    }

    public void unlock(){
        locked = false;
    }

    public void lock(){
        locked = true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void draw(HUD hud){
        if(locked){
            if(!hud.stage.getActors().contains(lockImage, true)){
                lockImage.setPosition(getX()*MainGame.PPM-8, getY()*MainGame.PPM-8);
                hud.stage.addActor(lockImage);
            }
        } else {
            hud.stage.getActors().removeValue(lockImage, false);
        }
    }
}
