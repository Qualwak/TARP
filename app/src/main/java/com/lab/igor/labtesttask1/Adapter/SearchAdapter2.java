package com.lab.igor.labtesttask1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Model.FoodInteractions;
import com.lab.igor.labtesttask1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 20-May-18.
 */
class SearchViewHolder2 extends RecyclerView.ViewHolder {

    public TextView interaction;

    public SearchViewHolder2(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        interaction = (TextView) itemView.findViewById(R.id.interaction);
    }


}

public class SearchAdapter2 extends RecyclerView.Adapter<SearchViewHolder2> {

    private Context context;
    private List<FoodInteractions> foodInteractions = new ArrayList<>();

    public SearchAdapter2(ArrayList<FoodInteractions> foodInteractions) {
        this.foodInteractions = foodInteractions;
    }

    public SearchAdapter2(Context context, List<FoodInteractions> foodInteractions) {
        this.context = context;
        this.foodInteractions = foodInteractions;
    }


    // list Of searched items
    @Override
    public SearchViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item3, parent, false);

        return new SearchViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder2 holder, int position) {
        holder.interaction.setText(foodInteractions.get(position).getInteraction());

    }

    @Override
    public int getItemCount() {
        return foodInteractions.size();
    }
}