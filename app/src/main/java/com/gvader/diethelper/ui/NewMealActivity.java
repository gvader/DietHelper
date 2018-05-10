package com.gvader.diethelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.gvader.diethelper.R;

public class NewMealActivity extends AppCompatActivity {
    private static final String TAG = NewMealActivity.class.toString();
    private static final String EXTRA_PREFIX = "com.gvader.diethelper.ui.NewMealActivity.";
    public static final String NEW_MEAL_NAME_EXTRA = EXTRA_PREFIX + "meal_name";
    public static final String NEW_MEAL_CATEGORY_EXTRA = EXTRA_PREFIX + "meal_category";
    public static final String NEW_MEAL_DESC_EXTRA = EXTRA_PREFIX + "meal_description";

    private EditText nameText;
    private EditText categoryText;
    private EditText descriptionText;

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
                //if (this.addMeal()) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(nameText.getText()) && TextUtils.isEmpty(categoryText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = nameText.getText().toString();
                    String category = categoryText.getText().toString();
                    String desc = descriptionText.getText().toString();
                    replyIntent.putExtra(NEW_MEAL_NAME_EXTRA, name);
                    replyIntent.putExtra(NEW_MEAL_CATEGORY_EXTRA, category);
                    replyIntent.putExtra(NEW_MEAL_DESC_EXTRA, desc);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
                /*} else {
                    Toast.makeText(this, R.string.new_meal_not_added_msg, Toast.LENGTH_LONG).show();
                    return true;
                }*/

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
