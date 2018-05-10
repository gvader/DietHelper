package com.gvader.diethelper.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gvader.diethelper.db.entity.MealEntity;

import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealEntity mealEntity);

    @Delete
    void delete(MealEntity mealEntity);

    @Update
    void update(MealEntity mealEntity);

    @Query("DELETE from meals")
    void deleteAll();

    @Query("SELECT * from meals ORDER BY name ASC")
    LiveData<List<MealEntity>> getAllMeals();
}
