package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PledgeRecylerViewAdapter extends RecyclerView.Adapter<PledgeRecylerViewAdapter.ViewHolder>{
    private ArrayList<String> stringPledges = new ArrayList<>();
    private ArrayList<Pledge> databasePledges = new ArrayList<>();
    private Context mContent;

    public PledgeRecylerViewAdapter(ArrayList<Pledge> specifiedPledges, Context Content) {
        databasePledges = specifiedPledges;
        stringPledges.clear();
        for(Pledge pledge : databasePledges){
            stringPledges.add(pledge.getRegion() + ": " + pledge.getName() + " has pledged to reduce their footprint by " + Long.toString(pledge.getPledge()) + " CO2e.");
        }
        mContent = Content;
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        //Called every time a new item is made
        holder.pledgeText.setText(stringPledges.get(position));
        Pledge pledge = databasePledges.get(position);
        if(pledge.getIcon().equals("Star")) {
            holder.pledgeIcon.setImageResource(R.drawable.gstar64);
        }
        else if(pledge.getIcon().equals("Leaf")) {
            holder.pledgeIcon.setImageResource(R.drawable.leaf64);
        }
        else if(pledge.getIcon().equals("Sprout")) {
            holder.pledgeIcon.setImageResource(R.drawable.sprout);
        }
        else if(pledge.getIcon().equals("Heart")) {
            holder.pledgeIcon.setImageResource(R.drawable.heart);
        }
        else if(pledge.getIcon().equals("Recycle")) {
            holder.pledgeIcon.setImageResource(R.drawable.recycle);
        }
        else if(pledge.getIcon().equals("Tree")) {
            holder.pledgeIcon.setImageResource(R.drawable.tree);
        }
        //Default icon is target
        else {
            holder.pledgeIcon.setImageResource(R.drawable.target);
        }
    }

    @Override
    public int getItemCount() {
        return stringPledges.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pledgeText;
        RelativeLayout pledgeLayout;
        ImageView pledgeIcon;
        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            pledgeText = (TextView) itemView.findViewById(R.id.txtPledge);
            pledgeLayout = itemView.findViewById(R.id.pledge_layout);
            pledgeIcon = itemView.findViewById(R.id.pledgeIcon);
        }
    }
}
