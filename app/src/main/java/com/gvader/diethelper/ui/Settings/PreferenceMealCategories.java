package com.gvader.diethelper.ui.Settings;

import android.content.Context;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;

import com.gvader.diethelper.R;

public class PreferenceMealCategories extends Preference {
    public PreferenceMealCategories(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private PreferenceMealCategories(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.preference_meal_categories);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false); //disable parent click

    }
}
