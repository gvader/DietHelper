package com.gvader.diethelper.ui.Settings;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gvader.diethelper.R;

public class CategoryView extends LinearLayout {
    private static final String TAG = CategoryView.class.getSimpleName();
    private EditText categoryText;
    private Button deleteCategoryButton;

    public CategoryView(Context context) {
        super(context);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.meal_category_item, this);
        categoryText = findViewById(R.id.MealCategoryItemMealName);
        deleteCategoryButton = findViewById(R.id.MealCategoryItemDeleteMealButton);
        deleteCategoryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
    }

    private void remove() {
        Log.d(TAG,"CLICK!!!!");
    }
}
