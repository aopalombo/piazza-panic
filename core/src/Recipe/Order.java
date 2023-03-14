package Recipe;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.MainGame;

import Sprites.PlateStation;

public class Order extends Sprite{
    private Dish burgerDish;
    private Dish saladDish;
    private ArrayList<Dish> menu = new ArrayList<Dish>();
    private Random randomizer = new Random();

    public int orderTime = 0;
    public ArrayList<Dish> dishes = new ArrayList<Dish>();
    private boolean orderComplete = false;
    
    public Order(Integer dishAmount){
        burgerDish = new Dish(PlateStation.burgerRecipe, new Texture("Food/burger_recipe.png"), 41);
        saladDish = new Dish(PlateStation.saladRecipe, new Texture("Food/salad_recipe.png"), 41);
        menu.add(burgerDish);
        menu.add(saladDish);
        for(int i = 0; i<dishAmount;i++){
            this.dishes.add(menu.get(randomizer.nextInt(menu.size())));
        }
        for(Dish dish: dishes){
            this.orderTime += dish.dishTime;
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
            dishes.get(i).create(x+(i*0.35f), y, batch);
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
