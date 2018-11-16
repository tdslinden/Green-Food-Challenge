package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.myViewHolder> {

    Context mContext;
    List<CardItem> mCardItems;

    public CardItemAdapter(Context context, List<CardItem> cardItems) {
        mContext = context;
        mCardItems = cardItems;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new myViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.backgroundImage.setImageResource(mCardItems.get(position).getBackground());
        holder.titleText.setText(mCardItems.get(position).getTitle());
        holder.buttonText.setText(mCardItems.get(position).getButtonText());
        holder.descriptionText.setText(mCardItems.get(position).getDescription());
        if(position == 0){
            holder.buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToCalculator = new Intent(mContext, CalorieCalc.class);
                    goToCalculator.addFlags(goToCalculator.FLAG_ACTIVITY_NO_ANIMATION);
                    mContext.startActivity(goToCalculator);
                }
            });
        }else if(position == 1){
            holder.buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToAuth = authenticationActivity.makeIntent(mContext);;
                    goToAuth.addFlags(goToAuth.FLAG_ACTIVITY_NO_ANIMATION);
                    mContext.startActivity(goToAuth);
                }
            });
        }else if(position == 2){
            holder.buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToViewPledges = ViewPledgeActivity.makeIntent(mContext);;
                    goToViewPledges.addFlags(goToViewPledges.FLAG_ACTIVITY_NO_ANIMATION);
                    mContext.startActivity(goToViewPledges);
                }
            });
        }else if(position == 3){
            holder.buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    // change the msg here
                    intent.setType("text/plain");
                    String shareBody = "Join the green food challenge and see how you can save the planet.";
                    intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    mContext.startActivity(Intent.createChooser(intent, "Share using"));
                }
            });
        }
        else if(position == 3){
            holder.buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToPledges = ViewMealActivity.makeIntent(mContext);;
                    mContext.startActivity(goToPledges);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView backgroundImage;
        TextView buttonText, titleText, descriptionText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.card_background);
            buttonText = itemView.findViewById(R.id.action_button);
            titleText = itemView.findViewById(R.id.card_title);
            descriptionText = itemView.findViewById(R.id.card_description);
        }
    }
}
