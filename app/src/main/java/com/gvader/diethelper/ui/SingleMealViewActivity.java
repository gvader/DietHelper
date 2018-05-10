package com.gvader.diethelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gvader.diethelper.R;

public class SingleMealViewActivity extends AppCompatActivity {

    private static final String TAG = SingleMealViewActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String mealName;

        TextView tvName;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_meal_view);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        mealName = i.getStringExtra("name");

        Log.d(TAG, "Showing meal detail for meal: " + mealName);

        tvName = findViewById(R.id.SimpleMealName);

        tvName.setText(mealName);
    }

}
