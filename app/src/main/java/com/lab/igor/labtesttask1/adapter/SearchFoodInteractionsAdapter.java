package com.lab.igor.labtesttask1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.igor.labtesttask1.model.FoodInteraction;
import com.lab.igor.labtesttask1.R;

import java.util.ArrayList;
import java.util.List;

class SearchViewFoodInteractionsHolder extends RecyclerView.ViewHolder {

    public TextView interaction;

    SearchViewFoodInteractionsHolder(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        interaction = (TextView) itemView.findViewById(R.id.interaction);
    }

}

public class SearchFoodInteractionsAdapter extends RecyclerView.Adapter<SearchViewFoodInteractionsHolder> {

    private List<FoodInteraction> foodInteractions = new ArrayList<>();

    public SearchFoodInteractionsAdapter(ArrayList<FoodInteraction> foodInteractions) {
        this.foodInteractions = foodInteractions;
    }

    // list Of searched items
    @NonNull
    @Override
    public SearchViewFoodInteractionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item3, parent, false);

        return new SearchViewFoodInteractionsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewFoodInteractionsHolder holder, int position) {
        holder.interaction.setText(foodInteractions.get(position).getInteraction());

    }

    @Override
    public int getItemCount() {
        return foodInteractions.size();
    }
}