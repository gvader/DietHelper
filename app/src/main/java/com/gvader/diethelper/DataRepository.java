package com.gvader.diethelper;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.gvader.diethelper.db.DietDatabase;
import com.gvader.diethelper.db.dao.MealDao;
import com.gvader.diethelper.db.entity.MealEntity;

import java.util.List;

public class DataRepository {
    private MealDao mealDao;
    private LiveData<List<MealEntity>> allMeals;

    public DataRepository(Application app) {
        DietDatabase db = DietDatabase.getDatabase(app);
        mealDao = db.mealDao();
        allMeals = mealDao.getAllMeals();
    }

    public void insertMeal(MealEntity mealEntity) {
        new insertMealAsyncTask(mealDao).execute(mealEntity);
    }

    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    private static class insertMealAsyncTask extends AsyncTask<MealEntity, Void, Void> {
        private final MealDao mealDao;

        insertMealAsyncTask(MealDao mealDao) {
            this.mealDao = mealDao;
        }

        @Override
        protected Void doInBackground(final MealEntity... params) {
            mealDao.insert(params[0]);
            return null;
        }
    }
}
