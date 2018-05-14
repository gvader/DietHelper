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

    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    public void insertMeal(MealEntity meal) {
        new insertMealAsyncTask(mealDao).execute(meal);
    }

    public void deleteMeal(MealEntity meal) {
        new deleteMealAsyncTask(mealDao).execute(meal);
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

    private static class deleteMealAsyncTask extends AsyncTask<MealEntity, Void, Void> {
        private final MealDao mealDao;

        deleteMealAsyncTask(MealDao mealDao) { this.mealDao = mealDao; }

        @Override
        protected Void doInBackground(final MealEntity... params) {
            mealDao.delete(params[0]);
            return null;
        }
    }
}
