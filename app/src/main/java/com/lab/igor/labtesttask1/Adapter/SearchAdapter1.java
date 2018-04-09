package com.lab.igor.labtesttask1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Model.Drug;
import com.lab.igor.labtesttask1.Model.DrugInteraction;
import com.lab.igor.labtesttask1.R;

import java.util.List;

/**
 * Created by Igor on 06-Apr-18.
 */

class SearchViewHolder1 extends RecyclerView.ViewHolder {

    public TextView name, description, drugName;
    public SearchViewHolder1(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.drugName);
        description = (TextView) itemView.findViewById(R.id.description);
        drugName = (TextView) itemView.findViewById(R.id.drug_name);

    }


}

public class SearchAdapter1 extends RecyclerView.Adapter<SearchViewHolder1> {

    private Context context;
    private List<DrugInteraction> drugInteractions;

    public SearchAdapter1(Context context, List<DrugInteraction> drugInteractions) {
        this.context = context;
        this.drugInteractions = drugInteractions;
    }



    @Override
    public SearchViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item2, parent, false);

        return new SearchViewHolder1(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder1 holder, int position) {
        holder.name.setText(drugInteractions.get(position).getName());
        holder.description.setText(drugInteractions.get(position).getDescription());
        holder.drugName.setText(drugInteractions.get(position).getDrugName());
    }

    @Override
    public int getItemCount() {
        return drugInteractions.size();
    }
}
