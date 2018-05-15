package com.gvader.diethelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gvader.diethelper.R;
import com.gvader.diethelper.ui.MealList.MealListActivity;
import com.gvader.diethelper.ui.Settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();

    //boolean flag to know if main FAB is in open or closed state.
    private boolean fabExpanded = false;
    private FloatingActionButton fabMain;

    //Linear layout holding the Add submenu
    private LinearLayout layoutFabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Button MealListButton = findViewById(R.id.MealListButton);
        MealListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Switching to MealEntity List Activity");
                Intent intent = new Intent(getBaseContext(), MealListActivity.class);
                startActivity(intent);
            }
        });

        Button DietLogButton = findViewById(R.id.DietLogButton);
        DietLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Switching to Diet Log Activity");
            }
        });

        fabMain = (FloatingActionButton) findViewById(R.id.MainFabSubmenuMainFab);

        layoutFabAdd = (LinearLayout) findViewById(R.id.MainFabSubmenuAddWaterLayout);

        /*
         * When main FAB is clicked, it expands if not expanded already, or collapses if main FAB
         * was open already. This adds FAB open/close behaviour
         */
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabExpanded) {
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabAdd.setVisibility(View.INVISIBLE);
        fabMain.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabAdd.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabMain.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }
}
