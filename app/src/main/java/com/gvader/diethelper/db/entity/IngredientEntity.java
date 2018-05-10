package com.gvader.diethelper.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.gvader.diethelper.data_model.Ingredient;

@Entity(tableName = "ingredients")
public class IngredientEntity implements Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public IngredientEntity(@NonNull String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
