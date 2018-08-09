package com.lab.igor.labtesttask1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.igor.labtesttask1.Main5Activity;
import com.lab.igor.labtesttask1.Main7Activity;
import com.lab.igor.labtesttask1.MainActivity3;
import com.lab.igor.labtesttask1.R;

import java.util.List;

/**
 * Created by Igor on 24-Jul-18.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private List<String> mUsersDrugs;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String whereToGo;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<String> data, List<String> usersDrugs, String whereToGo) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mUsersDrugs = usersDrugs;
        this.whereToGo = whereToGo;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_camera, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (whereToGo.contains("Food")) {
            final String animal = mData.get(position);
            holder.myTextView.setText(animal);
            String[] forSplit = animal.split(" has food interaction");
            if (mUsersDrugs != null && mUsersDrugs.contains(forSplit[0])) {
                holder.myTextView.setTextColor(Color.parseColor("#29a53e"));
            }
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent3 = new Intent(view.getContext(), Main5Activity.class);
                    intent3.putExtra("text_view", animal);
                    view.getContext().startActivity(intent3);
                }
            });
        } else {
            final String animal = mData.get(position);
            holder.myTextView.setText(animal);
            String[] forSplit = animal.split(" interacts with");
            if (mUsersDrugs != null && mUsersDrugs.contains(forSplit[0])) {
                holder.myTextView.setTextColor(Color.parseColor("#29a53e"));
            }
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent3 = new Intent(view.getContext(), Main7Activity.class);
                    intent3.putExtra("text_view", animal);
                    view.getContext().startActivity(intent3);
                }
            });
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.drug_item);
            button = itemView.findViewById(R.id.button_camera_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
