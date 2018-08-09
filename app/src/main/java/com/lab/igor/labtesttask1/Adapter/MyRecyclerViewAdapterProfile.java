package com.lab.igor.labtesttask1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lab.igor.labtesttask1.Main2Activity;
import com.lab.igor.labtesttask1.Main7Activity;
import com.lab.igor.labtesttask1.ProfileActivity;
import com.lab.igor.labtesttask1.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Igor on 24-Jul-18.
 */

public class MyRecyclerViewAdapterProfile extends RecyclerView.Adapter<MyRecyclerViewAdapterProfile.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapterProfile(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_drugs_in_profile, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String animal = mData.get(position);
        holder.myTextView.setText(animal);
        holder.button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(view.getContext(), Main7Activity.class);
                intent3.putExtra("text_view", animal);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent3);
            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mData.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mData.size());
//                view.setVisibility(View.GONE);
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                mData.remove(position);
                notifyItemRemoved(position);
                //notifyItemRangeChanged(position, mData.size());
                view.setVisibility(View.GONE);
                String json = gson.toJson(mData);
                editor.putString("drug list", json);
                editor.apply();

            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button button_view;
        Button button_delete;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.drug_profile_item);
            button_view = itemView.findViewById(R.id.button_profile_drug_view);
            button_delete = itemView.findViewById(R.id.button_profile_drug_delete);
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
