package com.gvader.diethelper.meallist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gvader.diethelper.R;

public class NewMealActivity extends AppCompatActivity {
    private static final String TAG = NewMealActivity.class.toString();
    private EditText nameText;
    private EditText categoryText;
    private EditText descriptionText;
    private EditText dishesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);
        Toolbar toolbar = findViewById(R.id.new_meal_activity_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameText = findViewById(R.id.new_meal_name_edit_text);
        categoryText = findViewById(R.id.new_meal_category_edit_text);
        descriptionText = findViewById(R.id.new_meal_desc_edit_text);
        dishesText = findViewById(R.id.new_meal_dishes_edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_meal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_meal:
                Log.d(TAG, "Adding new to the list");
                if (this.addMeal()) {
                    // Closing activity
                    setResult(1);
                    finish();
                } else {
                    Toast.makeText(this, R.string.new_meal_not_added_msg, Toast.LENGTH_LONG).show();
                    return true;
                }

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean addMeal() {
        // TODO: Add new meal to the list here...

        // validate data

        // add do database

        return true;
    }

}
