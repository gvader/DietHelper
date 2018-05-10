package com.gvader.diethelper.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.gvader.diethelper.db.dao.DishDao;
import com.gvader.diethelper.db.dao.MealDao;
import com.gvader.diethelper.db.entity.DishEntity;
import com.gvader.diethelper.db.entity.MealEntity;
import com.gvader.diethelper.ui.MealListActivity;

@Database(
        entities = {
                MealEntity.class,
                DishEntity.class
//                IngredientEntity.class,
//                MealToDishEntity.class,
//                IngredientToDishEntity.class
        },
        version = 1
)
public abstract class DietDatabase extends RoomDatabase {
    private static DietDatabase INSTANCE;

    public static DietDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DietDatabase.class) {
                if (INSTANCE == null) {
                    // Instantiate database
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DietDatabase.class,
                            "diet_database"
                    ).addCallback(roomDatabaseCallback).build();

                }
            }
        }

        return INSTANCE;
    }

    public abstract MealDao mealDao();
    public abstract DishDao dishDao();
    //public abstract IngredientDao ingredientDao();
    //public abstract MealToDishDao mealToDishDao();
    //public abstract IngredientToDishDao ingredientToDishDao();


    // FIXME: Data added for test. Remove later
    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MealDao mealDao;

        PopulateDbAsync(DietDatabase instance) {
            mealDao = instance.mealDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mealDao.deleteAll();
            MealEntity meal = new MealEntity(
                    "Uber sniadanie",
                    MealListActivity.MEAL_CATEGORY_BREAKFAST,
                    "Test test test test test"
                    );
            mealDao.insert(meal);
            MealEntity meal2 = new MealEntity(
                    "Uber obiad",
                    MealListActivity.MEAL_CATEGORY_LUNCH,
                    "Bardzo dobry opracowy obiad"
            );
            mealDao.insert(meal2);


            return null;
        }
    }
}
