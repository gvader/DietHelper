package com.gvader.diethelper.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gvader.diethelper.data_model.Dish;

@Entity(tableName = "dishes")
public class DishEntity implements Dish{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "recipe")
    private String recipe;

    public DishEntity(@NonNull String name, String recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRecipe() {
        return recipe;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
