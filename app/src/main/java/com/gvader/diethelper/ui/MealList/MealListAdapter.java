package com.gvader.diethelper.ui.MealList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gvader.diethelper.R;
import com.gvader.diethelper.db.entity.MealEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {
    private static final String TAG = MealListAdapter.class.toString();
    private Context context;
    private List<MealEntity> mealEntityList = null;
    private ArrayList<MealEntity> cachedMealEntityList;

    MealListAdapter(Context context) {
        this.context = context;
        this.cachedMealEntityList = new ArrayList<>();
    }

    public void setMeals(List<MealEntity> meals) {
        Log.d(TAG, "Setting meals... meals count: " + meals.size());
        this.mealEntityList = meals;
        this.cachedMealEntityList.addAll(meals);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, description, dishes;
        ImageView thumbnail;
        RelativeLayout viewForeground, viewBackground;
        View parent;

        ViewHolder(View view) {
            super(view);
            parent = view;
            name = view.findViewById(R.id.MealItemName);
            category = view.findViewById(R.id.MealItemCategory);
            description = view.findViewById(R.id.MealItemDescription);
            dishes = view.findViewById(R.id.MealItemDishes);
            thumbnail = view.findViewById(R.id.MealItemThumbnail);
            viewForeground = view.findViewById(R.id.MealItemForeground);
            viewBackground = view.findViewById(R.id.MealItemBackground);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MealEntity meal = mealEntityList.get(position);

        holder.name.setText(meal.getName());
        holder.category.setText(meal.getCategory());
        holder.description.setText(meal.getDescription());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleMealViewActivity.class);
                intent.putExtra("id", (meal.getId()));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
         return (mealEntityList != null) ? mealEntityList.size() : 0;
    }

    public void removeItem(int position) {
        mealEntityList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(MealEntity item, int position) {
        mealEntityList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public MealEntity getItem(int position) {
        return ((mealEntityList != null) && (position < mealEntityList.size())) ? mealEntityList.get(position) : null;
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
