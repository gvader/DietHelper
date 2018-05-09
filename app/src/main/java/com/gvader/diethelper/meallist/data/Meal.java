package com.gvader.diethelper.meallist.data;

import android.app.Application;

import com.gvader.diethelper.MyApplication;
import com.gvader.diethelper.R;
import com.gvader.diethelper.meallist.MealListActivity;

import java.util.ArrayList;

public class Meal {
    private String name;
    private String category;
    private String description;
    private ArrayList<Dish> dishesList = new ArrayList<>();

    public Meal(String name, @MealListActivity.MEAL_CATEGORIES String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public Meal(String name, @MealListActivity.MEAL_CATEGORIES String category, String description, ArrayList<Dish> dishes) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.dishesList = dishes;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return returns coma separated names of the meals included in the meal.
     */
    public String getDishesNames() {
        String dishesNames = "";
        for (Dish dish : dishesList) {
            if (dishesNames.length() > 100) { 
                dishesNames += MyApplication.getAppContext().getString(R.string.meal_dish_list_finalizer);
            } else if (dishesNames.isEmpty()) {
                dishesNames = dish.getName();
            } else {
                dishesNames += ", " + dish.getName();
            }
        }
        
        return dishesNames;
    }
}
