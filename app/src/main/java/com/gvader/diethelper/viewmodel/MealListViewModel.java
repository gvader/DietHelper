package com.gvader.diethelper.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gvader.diethelper.DataRepository;
import com.gvader.diethelper.db.entity.MealEntity;

import java.util.List;

public class MealListViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<MealEntity>> allMeals;

    public MealListViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allMeals = repository.getAllMeals();
    }

    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    public void insertMeal(MealEntity meal) {
        repository.insertMeal(meal);
    }

    public void deleteMeal(MealEntity meal) {
        repository.deleteMeal(meal);
    }
}
