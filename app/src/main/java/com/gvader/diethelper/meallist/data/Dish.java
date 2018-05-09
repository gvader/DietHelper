package com.gvader.diethelper.meallist.data;

import java.util.ArrayList;

public class Dish {
    private String name;
    private ArrayList<String> ingredients = new ArrayList<>();
    private String recipe;

    public Dish(String name, ArrayList<String> ingredients, String recipe) {
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

}
