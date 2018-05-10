package com.gvader.diethelper.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gvader.diethelper.data_model.Meal;
import com.gvader.diethelper.ui.MealListActivity;

@Entity(tableName = "meals")
public class MealEntity implements Meal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "desc")
    private String description;


    public MealEntity(@NonNull String name, @NonNull @MealListActivity.MEAL_CATEGORIES String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
