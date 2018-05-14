package com.gvader.diethelper.ui.MealList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gvader.diethelper.R;
import com.gvader.diethelper.db.entity.MealEntity;
import com.gvader.diethelper.viewmodel.MealListViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Locale;

public class MealListActivity extends AppCompatActivity {
    private static final String TAG = MealListActivity.class.toString();

    // TODO: Move meal categories to editable option in settings.
    public static final String MEAL_CATEGORY_BREAKFAST = "Breakfast";
    public static final String MEAL_CATEGORY_2ND_BREAKFAST = "Second Breakfast";
    public static final String MEAL_CATEGORY_LUNCH = "Lunch";
    public static final String MEAL_CATEGORY_DINNER = "Dinner";
    public static final String MEAL_CATEGORY_SUPPER = "Supper";
    private static final int NEW_MEAL_ACTIVITY_REQUEST_CODE = 1;

    @StringDef({MEAL_CATEGORY_BREAKFAST, MEAL_CATEGORY_2ND_BREAKFAST, MEAL_CATEGORY_LUNCH, MEAL_CATEGORY_DINNER, MEAL_CATEGORY_SUPPER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MEAL_CATEGORIES {}

    private MealListAdapter adapter;
    private EditText searchView;

    private MealListViewModel mealListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealListActivity.this, NewMealActivity.class);
                startActivityForResult(i, NEW_MEAL_ACTIVITY_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new MealListAdapter(this);

        mealListViewModel = ViewModelProviders.of(this).get(MealListViewModel.class);
        mealListViewModel.getAllMeals().observe(this, new Observer<List<MealEntity>>() {
            @Override
            public void onChanged(@Nullable List<MealEntity> mealEntities) {
                adapter.setMeals(mealEntities);
            }
        });

        RecyclerView mealListView = findViewById(R.id.MealListRecyclerView);

        mealListView.setAdapter(adapter);
        mealListView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        searchView = findViewById(R.id.MealListSearch);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing to do here
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                Log.d(TAG, "Pushing text " + text);
                adapter.filter(text);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult, request: " + requestCode + " result: " + resultCode);
        if (requestCode == NEW_MEAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MealEntity meal = new MealEntity(
                    data.getStringExtra(NewMealActivity.NEW_MEAL_NAME_EXTRA),
                    data.getStringExtra(NewMealActivity.NEW_MEAL_CATEGORY_EXTRA),
                    data.getStringExtra(NewMealActivity.NEW_MEAL_DESC_EXTRA)
            );
            mealListViewModel.insertMeal(meal);
            Toast.makeText(this, R.string.meal_list_activity_new_meal_success_msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.meal_list_activity_new_meal_empty_not_saved_msg,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

}
