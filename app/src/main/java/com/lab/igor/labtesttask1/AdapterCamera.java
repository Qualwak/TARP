package com.lab.igor.labtesttask1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Igor on 23-Jul-18.
 */

public class AdapterCamera extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String[] items;

    public AdapterCamera(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.layout_item_camera, parent, false);
        DrugItem drugItem = new DrugItem(row);
        return drugItem;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DrugItem) holder).textView.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class DrugItem extends RecyclerView.ViewHolder {
        TextView textView;

        public DrugItem(View itemView) {
            super(itemView);
            textView.findViewById(R.id.drug_item);
        }
    }
}
