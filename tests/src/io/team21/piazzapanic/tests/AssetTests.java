package io.team21.piazzapanic.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void StartImageExists(){
        assertTrue("This test will only pass if startImage.png exists", Gdx.files.internal("startImage.png").exists());
    }
    @Test
    public void TileSetExists(){
        assertTrue("This test will only pass if TileSet.png exists", Gdx.files.internal("TileSet.png").exists());
    }
    @Test
    public void KitchenTileSetTSXExists(){
        assertTrue("This test will only pass if KitchenTileSet.png exists", Gdx.files.internal("KitchenTileSet.tsx").exists());
    }
    @Test
    public void KitchenMapTMXExists(){
        assertTrue("This test will only pass if Kitchen.png exists", Gdx.files.internal("Kitchen.tmx").exists());
    }
    @Test
    public void ChefAtlasExists(){
        assertTrue("This test will only pass if ChefAtlas.txt exists", Gdx.files.internal("Chef/chefAtlas.txt").exists());
    }
    @Test
    public void ChefSpritesExists(){
        assertTrue("This test will only pass if ChefSprites.png exists", Gdx.files.internal("Chef/ChefSprites.png").exists());
    }
    @Test
    public void FoodAssetsExists(){
        //Completed order and recipe display
        assertTrue("This test will only pass if burger_recipe.png exists", Gdx.files.internal("Food/burger_recipe.png").exists());
        assertTrue("This test will only pass if Burger.png exists", Gdx.files.internal("Food/Burger.png").exists());
        assertTrue("This test will only pass if salad_recipe.png exists", Gdx.files.internal("Food/salad_recipe.png").exists());
        assertTrue("This test will only pass if Salad.png exists", Gdx.files.internal("Food/Salad.png").exists());

        //Prepped ingredients
        assertTrue("This test will only pass if Chopped_lettuce.png exists", Gdx.files.internal("Food/Chopped_lettuce.png").exists());
        assertTrue("This test will only pass if Chopped_tomato.png exists", Gdx.files.internal("Food/Chopped_tomato.png").exists());
        assertTrue("This test will only pass if Chopped_onione.png exists", Gdx.files.internal("Food/Chopped_onion.png").exists());
        assertTrue("This test will only pass if Cooked_patty.png exists", Gdx.files.internal("Food/Cooked_patty.png").exists());
        assertTrue("This test will only pass if Patty.png exists", Gdx.files.internal("Food/Patty.png").exists());
        assertTrue("This test will only pass if Toasted_burger_buns.png exists", Gdx.files.internal("Food/Toasted_burger_buns.png").exists());

        //Unprepped ingredients
        assertTrue("This test will only pass if Lettuce.png exists", Gdx.files.internal("Food/Lettuce.png").exists());
        assertTrue("This test will only pass if Tomato.png exists", Gdx.files.internal("Food/Tomato.png").exists());
        assertTrue("This test will only pass if Onion.png exists", Gdx.files.internal("Food/Onion.png").exists());
        assertTrue("This test will only pass if Meat.png exists", Gdx.files.internal("Food/Meat.png").exists());
        assertTrue("This test will only pass if Burger_buns.png exists", Gdx.files.internal("Food/Burger_buns.png").exists());
    }
}
