package com.gvader.diethelper.meallist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gvader.diethelper.R;
import com.gvader.diethelper.meallist.data.Dish;
import com.gvader.diethelper.meallist.data.Meal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Locale;

public class MealListActivity extends AppCompatActivity {
    private static final String TAG = MealListActivity.class.toString();

    public static final String MEAL_CATEGORY_BREAKFAST = "Breakfast";
    public static final String MEAL_CATEGORY_2ND_BREAKFAST = "Second Breakfast";
    public static final String MEAL_CATEGORY_LUNCH = "Lunch";
    public static final String MEAL_CATEGORY_DINNER = "Dinner";
    public static final String MEAL_CATEGORY_SUPPER = "Supper";

    @StringDef({MEAL_CATEGORY_BREAKFAST, MEAL_CATEGORY_2ND_BREAKFAST, MEAL_CATEGORY_LUNCH, MEAL_CATEGORY_DINNER, MEAL_CATEGORY_SUPPER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MEAL_CATEGORIES {}

    ListView listView;
    ListViewAdapter adapter;
    EditText searchView;

    ArrayList<Meal> mealList = new ArrayList<>();

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
                startActivityForResult(i, 1 /* putting whatever, not used value*/);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.MealListListView);

        // TODO: FAKE DATA< REMOVEME AFTER DATABASE IMPLEMENTATION

        mealList.add(new Meal("Uber Obiad", MEAL_CATEGORY_DINNER, "DUPADUPACYCKICYCKI", new ArrayList<Dish>(){{
            add(new Dish(
                    "Cola",
                    new ArrayList<String>(){{
                        add("Syf");
                    }},
                    "Idz do sklepu"));
            add(new Dish("Salatka",
                new ArrayList<String>(){{
                    add("Ogorek");
                    add("Pomidor");
                    add("Kielbasa");
                }},
                "zmiksuj"));
            add(new Dish("chleb", new ArrayList<String>(){{add("-");}}, "kup"));
        }} ));

        mealList.add(new Meal("Uber Obiad 2", MEAL_CATEGORY_DINNER, "DUPADUPACYCKICYCKI22222", new ArrayList<Dish>(){{
            add(new Dish("Herbata", new ArrayList<String>() {{
                add("Herbata");
            }}, "Zagotuj wode"));
            add(new Dish("Burger",
                    new ArrayList<String>() {{
                        add("wołowina");
                        add("bułka");
                        add("sałąta");
                        add("pomidor");
                    }},
                    "Obierz warzywa, usmarz mięso, cośtam, cośtam"
            ));
            add(new Dish("Zupa",
                    new ArrayList<String>() {{
                        add("pomidory");
                        add("makaron");
                        add("cokolwiek");
                    }},
                    "ugotuj"));
        }} ));


        mealList.add(new Meal("Uber Sniadanie", MEAL_CATEGORY_BREAKFAST, "DUPADUPACYCKICYCKISNIADANIE", new ArrayList<Dish>(){{
            add(new Dish("Herbata", new ArrayList<String>() {{
                add("Herbata");
            }}, "Zagotuj wode"));
            add(new Dish("Zupa",
                    new ArrayList<String>() {{
                        add("pomidory");
                        add("makaron");
                        add("cokolwiek");
                    }},
                    "ugotuj"));
        }} ));


        mealList.add(new Meal("Uber Kolacja", MEAL_CATEGORY_SUPPER, "DUPADUPACYCKICYCKIKOLACJA", new ArrayList<Dish>(){{
            add(new Dish("Cola", new ArrayList<String>(){{
                add("Syf");
            }}, "Idz do sklepu"));
            add(new Dish("Salatka",
                    new ArrayList<String>(){{
                        add("Ogorek");
                        add("Pomidor");
                        add("Kielbasa");
                    }},
                    "zmiksuj"));
            add(new Dish("Burger",
                    new ArrayList<String>() {{
                        add("wołowina");
                        add("bułka");
                        add("sałąta");
                        add("pomidor");
                    }},
                    "Obierz warzywa, usmarz mięso, cośtam, cośtam"
                    ));
        }} ));

        adapter = new ListViewAdapter(this, mealList);

        listView.setAdapter(adapter);

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
        if (requestCode == 1) {
            Toast.makeText(this, R.string.main_activity_new_meal_success_msg, Toast.LENGTH_SHORT).show();
        }
    }

}
