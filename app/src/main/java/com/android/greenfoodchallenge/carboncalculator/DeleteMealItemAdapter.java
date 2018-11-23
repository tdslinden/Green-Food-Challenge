package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteMealItemAdapter extends RecyclerView.Adapter<DeleteMealItemAdapter.myViewHolder> {

    Context mContext;
    List<Meal> meals;
    List<String> IDs;
    private DatabaseReference mealDatabase;

    public DeleteMealItemAdapter(Context context, List<Meal> mealList, List<String> IDList) {
        mContext = context;
        meals = mealList;
        IDs = IDList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.delete_meal_item, parent, false);
        return new myViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Meal currentMeal = meals.get(position);
        if(!currentMeal.getMealPhoto().equals("")){
            Picasso.with(mContext).load(currentMeal.getMealPhoto()).into(holder.backgroundImage);
        } else{
            holder.backgroundImage.setImageResource(R.drawable.veggies);
        }
        holder.titleText.setText(currentMeal.getMeal().toUpperCase());
        holder.descriptionText.setText(currentMeal.getDescription());
        holder.tagText.setText("Tags: " + currentMeal.getTags());
        holder.restaurantLocationText.setText(currentMeal.getRestaurant() + ", " + currentMeal.getLocation());
        holder.buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealDatabase = FirebaseDatabase.getInstance().getReference("meals");
                DatabaseReference mealNode = mealDatabase.child(IDs.get(position));
                mealNode.setValue(null);
            }
        });
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
            buttonText = itemView.findViewById(R.id.btnDeleteMeal);
            descriptionText = itemView.findViewById(R.id.card_description);
            tagText = itemView.findViewById(R.id.card_tags);
            restaurantLocationText = itemView.findViewById(R.id.card_restaurant_location);
        }
    }
}
