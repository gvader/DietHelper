package com.gvader.diethelper.ui.MealList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gvader.diethelper.R;
import com.gvader.diethelper.db.entity.MealEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ListViewAdapter extends BaseAdapter {
    private static final String TAG = ListViewAdapter.class.toString();
    private Context context;
    private LayoutInflater inflater;
    private List<MealEntity> mealEntityList = null;
    private ArrayList<MealEntity> cachedMealEntityList;

    ListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cachedMealEntityList = new ArrayList<>();
    }

    public void setMeals(List<MealEntity> meals) {
        this.mealEntityList = meals;
        this.cachedMealEntityList.addAll(meals);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView name;
        TextView category;
        TextView description;
        TextView dishes;
    }

    @Override
    public int getCount() {
        return (mealEntityList != null) ? mealEntityList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mealEntityList.get(position);
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
            holder.description = view.findViewById(R.id.MealItemDescription);
            holder.dishes = view.findViewById(R.id.MealItemDishes);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final MealEntity currentMealEntity = mealEntityList.get(position);

        holder.name.setText(currentMealEntity.getName());
        holder.category.setText(currentMealEntity.getCategory());
        holder.description.setText(currentMealEntity.getDescription());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleMealViewActivity.class);
                intent.putExtra("id", (currentMealEntity.getId()));

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
        mealEntityList.clear();
        if (text.length() == 0) {
            Log.d(TAG, "Empty text");
            mealEntityList.addAll(cachedMealEntityList);
        } else {
            Log.d(TAG, "In Else");
            for(MealEntity mealEntity : cachedMealEntityList) {
                Log.d(TAG, "In For checking mealEntity " + mealEntity.getName());
                if (mealEntity.getName().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding name " + text);
                    mealEntityList.add(mealEntity);
                } else if (mealEntity.getCategory().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding category " + text);
                    mealEntityList.add(mealEntity);
                } else if (mealEntity.getDescription().toLowerCase(Locale.getDefault()).contains(text)) {
                    Log.d(TAG, "Adding description " + text);
                    mealEntityList.add(mealEntity);
                } else {
                    Log.d(TAG, "Not adding nothing");
                }
            }
        }
        Log.d(TAG, "Notifing about changes");
        notifyDataSetChanged();
    }
}
