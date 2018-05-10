package com.gvader.diethelper.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gvader.diethelper.db.entity.DishEntity;

import java.util.List;

@Dao
public interface DishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DishEntity dishEntity);

    @Update
    void update(DishEntity dishEntity);

    @Delete
    void delete(DishEntity dishEntity);

    @Query("DELETE from dishes")
    void deleteAll();

    @Query("SELECT * from dishes ORDER BY name ASC")
    LiveData<List<DishEntity>> getAllDishes();
}
