package com.gvader.diethelper.ui.Settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gvader.diethelper.MyApplication;
import com.gvader.diethelper.R;

public class MealCategoriesPreference extends Preference {
    private static final int TEST_MEAL_NUM = 4;
    private static final String TAG = MealCategoriesPreference.class.getSimpleName();
    private LinearLayout mainLayout;
    private Button addCategoryButton;

    public MealCategoriesPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private MealCategoriesPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.meal_category_preference);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mainLayout = (LinearLayout) holder.findViewById(R.id.MealCategoryPreferenceParentLayout);
        addCategoryButton = (Button) holder.findViewById(R.id.MealCategoryPreferenceAddCategoryButton);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddField(v);
            }
        });

        holder.itemView.setClickable(false); //disable parent click

        for (int i = 0; i < TEST_MEAL_NUM; ++i) {

        }
    }

    private void onAddField(@NonNull View v) {
        LayoutInflater inflater = (LayoutInflater) MyApplication
                .getInstance()
                .getAppContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            final View rowView = inflater.inflate(R.layout.meal_category_item, null);
            // Add the new row before the add field button
            mainLayout.addView(
                    rowView,
                    mainLayout.getChildCount() - 1
            );
        } else {
            Log.e(TAG, "Inflater service returned null, do not adding next category!");
        }
    }

    public void onDeleteField(@NonNull View v) {
        mainLayout.removeView((View) v.getParent());
    }
}
