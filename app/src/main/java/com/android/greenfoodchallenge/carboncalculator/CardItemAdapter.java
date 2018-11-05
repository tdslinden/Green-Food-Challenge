package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
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
       // holder.buttonText.setText(mCardItems.get(position).getButtonText());
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView backgroundImage;
        TextView buttonText, titleText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.card_background);
            buttonText = itemView.findViewById(R.id.action_button);
            titleText = itemView.findViewById(R.id.card_title);
        }
    }
}
