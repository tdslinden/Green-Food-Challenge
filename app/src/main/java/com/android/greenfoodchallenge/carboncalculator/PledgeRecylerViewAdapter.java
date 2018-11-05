package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class PledgeRecylerViewAdapter extends RecyclerView.Adapter<PledgeRecylerViewAdapter.ViewHolder>{
    private ArrayList<String> mDatabasePledges = new ArrayList<>();

    private Context mContent;

    public PledgeRecylerViewAdapter(ArrayList<String> databasePledges, Context Content) {
        mDatabasePledges = databasePledges;
        mContent = mContent;
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
        holder.pledgeText.setText(mDatabasePledges.get(position));
    }

    @Override
    public int getItemCount() {
        //get amount of pledges on firebase
        Log.d("MyApp", Integer.toString(mDatabasePledges.size()));
        return mDatabasePledges.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pledgeText;
        RelativeLayout pledgeLayout;
        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            pledgeText = (TextView) itemView.findViewById(R.id.txtPledge);
            pledgeLayout = itemView.findViewById(R.id.pledge_layout);
        }
    }
}
