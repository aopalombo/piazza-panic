package Recipe;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Sprites.PlateStation;

public class Order {
    private Dish burgerDish = new Dish(PlateStation.burgerRecipe, new Texture("Food/burger_recipe.png"), 41);
    private Dish saladDish = new Dish(PlateStation.saladRecipe, new Texture("Food/salad_recipe.png"), 41);
    private ArrayList<Dish> menu;
    private Random randomizer = new Random();

    public int orderTime = 0;
    private ArrayList<Dish> dishes;
    private boolean orderComplete = false;
    
    public Order(Integer dishAmont){
        menu.add(burgerDish);
        menu.add(saladDish);
        for(int i = 0; i<dishAmont;i++){
            dishes.add(menu.get(randomizer.nextInt(menu.size())));
        }
    }

    public boolean isComplete(){
        for(Dish dish : dishes){
            if(!dish.orderComplete){
                return false;
            }
        }
        this.orderComplete = true;
        return true;
    }

    public void create(float x, float y, SpriteBatch batch){
        for(int i = 0; i<dishes.size();i++){
            dishes.get(i).create(x+i, y+i, batch);
        }
    }

    public boolean completeDish(Recipe recipe){
        for(Dish dish : dishes){
            if(dish.recipe.getClass().equals(recipe.getClass())){
                dish.orderComplete = true;
                return true;
            }
        }
        return false;
    }
}
