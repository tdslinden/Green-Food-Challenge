package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealItemAdapter extends RecyclerView.Adapter<MealItemAdapter.myViewHolder> {

    Context mContext;
    List<Meal> meals;

    public MealItemAdapter(Context context, List<Meal> mealList) {
        mContext = context;
        meals = mealList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meal_item, parent, false);
        return new myViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Meal currentMeal = meals.get(position);
        if(!currentMeal.getMealPhoto().equals("")){
            Picasso.with(mContext).load(currentMeal.getMealPhoto()).into(holder.backgroundImage);
        } else{
            holder.backgroundImage.setImageResource(R.drawable.defaultimage);
        }
        holder.titleText.setText(currentMeal.getMeal().toUpperCase());
        holder.descriptionText.setText(currentMeal.getDescription());
        holder.tagText.setText("Tags: " + currentMeal.getTags());
        holder.restaurantLocationText.setText(currentMeal.getRestaurant() + ", " + currentMeal.getLocation());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView backgroundImage;
        TextView buttonText, titleText, descriptionText, tagText, restaurantLocationText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.card_background);
            titleText = itemView.findViewById(R.id.card_title);
            descriptionText = itemView.findViewById(R.id.card_description);
            tagText = itemView.findViewById(R.id.card_tags);
            restaurantLocationText = itemView.findViewById(R.id.card_restaurant_location);
        }
    }
}
