package com.lab.igor.labtesttask1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lab.igor.labtesttask1.model.DrugInteraction;
import com.lab.igor.labtesttask1.R;

import java.util.ArrayList;
import java.util.List;


class SearchViewDrugInteractionsHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "SearchDrugInteractions";

    TextView interactions, description;
    ImageView imgButton;
    LinearLayout linearLayout;

    SearchViewDrugInteractionsHolder(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        interactions = (TextView) itemView.findViewById(R.id.interactions);
        description = (TextView) itemView.findViewById(R.id.description_of_interactions);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        imgButton = itemView.findViewById(R.id.arrow_down);
    }


}

public class SearchDrugInteractionsAdapter extends RecyclerView.Adapter<SearchViewDrugInteractionsHolder> {

    private Context context;

    private static final String TAG = "SearchDrugInteractions";
    private List<DrugInteraction> drugInteractions = new ArrayList<>();


    public SearchDrugInteractionsAdapter(ArrayList<DrugInteraction> drugInteractions) {
        this.drugInteractions = drugInteractions;
    }

    public SearchDrugInteractionsAdapter(Context context, List<DrugInteraction> drugInteractions) {
        this.context = context;
        this.drugInteractions = drugInteractions;
    }


    // list Of searched items
    @NonNull
    @Override
    public SearchViewDrugInteractionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item7, parent, false);

        return new SearchViewDrugInteractionsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewDrugInteractionsHolder holder, final int position) {
        holder.interactions.setText(drugInteractions.get(position).getName());
        holder.description.setText("");
        holder.imgButton.setVisibility(View.VISIBLE);

        holder.linearLayout.setOnClickListener(view -> {
            Log.d(TAG, "onClick: clicked on:" + drugInteractions.get(position).getDescription());

            holder.description.setText(String.format("Description: %s",
                    drugInteractions.get(position).getDescription()));

            holder.imgButton.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return drugInteractions.size();
    }
}