package Recipes;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team13.piazzapanic.Screens.PlayScreen;

import Sprites.PlateStation;

public class Order{
    private Dish burgerDish =  new Dish(PlateStation.burgerRecipe, new Texture("Food/burger_recipe.png"), PlayScreen.orderTime);
    private Dish saladDish = new Dish(PlateStation.saladRecipe, new Texture("Food/salad_recipe.png"), PlayScreen.orderTime);
    private Dish jacketPotatoDish = new Dish(PlateStation.jacketPotatoRecipe, new Texture("Food/jacketPotato_recipe.png"), PlayScreen.orderTime);
    private Dish pizzaDish = new Dish(PlateStation.pizzaRecipe, new Texture("Food/pizza_recipe.png"), PlayScreen.orderTime+20);
    private ArrayList<Dish> menu = new ArrayList<Dish>();
    private Random randomizer = new Random();

    public int orderTime = 0;
    public ArrayList<Dish> dishes = new ArrayList<Dish>();
    
    public Order(Integer dishAmount){
        menu.add(burgerDish);
        menu.add(saladDish);
        menu.add(jacketPotatoDish);
        menu.add(pizzaDish);
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
        return true;
    }

    public void create(float x, float y, SpriteBatch batch){
        for(int i = 0; i<dishes.size();i++){
            if(!dishes.get(i).orderComplete){
                dishes.get(i).create(x+(i*0.35f), y, batch);
            } else {
                dishes.remove(i);
            }
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
