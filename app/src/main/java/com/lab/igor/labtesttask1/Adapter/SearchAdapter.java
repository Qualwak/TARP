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
 * For search field
 */

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView name, description, type;

    public SearchViewHolder(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        name = (TextView) itemView.findViewById(R.id.name);
        description = (TextView) itemView.findViewById(R.id.description);
        type = (TextView) itemView.findViewById(R.id.type);

    }


}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private Context context;
    private List<Drug> drugs;

    public SearchAdapter(Context context, List<Drug> drugs) {
        this.context = context;
        this.drugs = drugs;
    }



    // list Of searched items
    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.name.setText(drugs.get(position).getName());
        holder.description.setText(drugs.get(position).getDescription());
        holder.type.setText(drugs.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
