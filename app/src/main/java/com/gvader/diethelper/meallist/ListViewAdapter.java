package com.gvader.diethelper.meallist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gvader.diethelper.R;
import com.gvader.diethelper.meallist.data.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ListViewAdapter extends BaseAdapter {
    private static final String TAG = ListViewAdapter.class.toString();
    private Context context;
    private LayoutInflater inflater;
    private List<Meal> mealList = null;
    private ArrayList<Meal> cachedMealList;

    public ListViewAdapter(Context context, ArrayList<Meal> data) {
        this.context = context;
        this.mealList = data;
        this.inflater = LayoutInflater.from(context);
        this.cachedMealList = new ArrayList<>();
        this.cachedMealList.addAll(data);
    }

    public class ViewHolder {
        TextView name;
        TextView category;
        TextView descritption;
        TextView dishes;
    }

    @Override
    public int getCount() {
        return mealList.size();
    }

    @Override
    public Object getItem(int position) {
        return mealList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.meal_list_item_layout, null);

            holder.name = view.findViewById(R.id.MealItemName);
            holder.category = view.findViewById(R.id.MealItemCategory);
            holder.descritption = view.findViewById(R.id.MealItemDescription);
            holder.dishes = view.findViewById(R.id.MealItemDishes);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Meal currentMeal = mealList.get(position);

        holder.name.setText(currentMeal.getName());
        holder.category.setText(currentMeal.getCategory());
        holder.descritption.setText(currentMeal.getDescription());
        holder.dishes.setText(currentMeal.getDishesNames());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleMealViewActivity.class);
                intent.putExtra("name", (currentMeal.getName()));

                context.startActivity(intent);
            }
        });

        return view;
    }

    public void filter(String text) {
        // TODO: Very simple filter that does not include dishes ingredients,
        // FIXME: Improve !!!!
        Log.d(TAG, "Filtering " + text);
        text = text.toLowerCase(Locale.getDefault());
        mealList.clear();
        if (text.length() == 0) {
            Log.d(TAG, "Empty text");
            mealList.addAll(cachedMealList);
        } else {
            Log.d(TAG, "In Else");
            for(Meal meal : cachedMealList) {
                Log.d(TAG, "In For checking meal " +meal.getName());
                if (meal.getName().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding name " + text);
                    mealList.add(meal);
                } else if (meal.getCategory().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding category " + text);
                    mealList.add(meal);
                } else if (meal.getDescription().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding description " + text);
                    mealList.add(meal);
                } else if (meal.getDishesNames().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding Dishes names" + text);
                    mealList.add(meal);
                } else {
                    Log.d(TAG, "Not adding nothing");
                }
            }
        }
        Log.d(TAG, "Notifing about changes");
        notifyDataSetChanged();
    }
}
