package com.gvader.diethelper.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gvader.diethelper.db.entity.IngredientEntity;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientEntity ingredientEntity);

    @Delete
    void delete(IngredientEntity ingredientEntity);

    @Update
    void update(IngredientEntity ingredientEntity);

    @Query("DELETE from ingredients")
    void deleteAll();

    @Query("SELECT * from ingredients ORDER BY name ASC")
    LiveData<List<IngredientEntity>> getAllIngredients();
}
