package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MealRecyclerViewAdapter extends RecyclerView.Adapter<MealRecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> stringMeals = new ArrayList<>();
    private ArrayList<Meal> databaseMeals = new ArrayList<>();
    private Context mContent;

    public MealRecyclerViewAdapter(ArrayList<Meal> specifiedMeals, Context Content) {
        databaseMeals = specifiedMeals;
        stringMeals.clear();
        for(Meal meal : databaseMeals){
            stringMeals.add("Location: " + meal.getLocation() +
                    " Meal: " + meal.getMeal() +
                    " Protein: " + meal.getTags() +
                    " Restaurant: " + meal.getRestaurant());
        }
        mContent = Content;
    }

    @androidx.annotation.NonNull
    @Override
    public MealRecyclerViewAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mealfeed, parent, false);
        MealRecyclerViewAdapter.ViewHolder viewHolder = new MealRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MealRecyclerViewAdapter.ViewHolder holder, int position) {
        //Called every time a new item is made
        holder.mealText.setText(stringMeals.get(position));
        Meal meal = databaseMeals.get(position);
        holder.mealPhoto.setImageResource(R.drawable.target);
    }

    @Override
    public int getItemCount() {
        return stringMeals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mealText;
        RelativeLayout mealLayout;
        ImageView mealPhoto;
        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            mealText = (TextView) itemView.findViewById(R.id.txtMeal);
            mealLayout = itemView.findViewById(R.id.meal_layout);
            mealPhoto = itemView.findViewById(R.id.imageMealPhoto);
        }
    }
}
