package com.lab.igor.labtesttask1.adapter;

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
import com.lab.igor.labtesttask1.activity.ProfileActivity;
import com.lab.igor.labtesttask1.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

class SearchViewDrugsHolder extends RecyclerView.ViewHolder {

    public TextView name, description, type;
    public Button buttonSave;


    public SearchViewDrugsHolder(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        name = (TextView) itemView.findViewById(R.id.name);
        buttonSave = (Button) itemView.findViewById(R.id.add_drug_to_profile);
//        description = (TextView) itemView.findViewById(R.id.description);
//        type = (TextView) itemView.findViewById(R.id.type);

    }


}

public class SearchDrugsAdapter extends RecyclerView.Adapter<SearchViewDrugsHolder> {

    private Context context;
    private List<String> drugs;
    private List<String> list;

    public SearchDrugsAdapter(Context context, List<String> drugs, List<String> list) {
        this.context = context;
        this.drugs = drugs;
        this.list = list;
    }

    // list Of searched items
    @Override
    public SearchViewDrugsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);

        return new SearchViewDrugsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchViewDrugsHolder holder, final int position) {
//        holder.name.setText(drugs.get(position).getName());
        holder.name.setText(drugs.get(position));


        holder.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
//                list.add(drugs.get(position).getName());

                list.add(drugs.get(position).substring(0, 1).toUpperCase() + drugs.get(position).substring(1));
                String json = gson.toJson(list);
                editor.putString("drug list", json);
                editor.apply();
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                view.getContext().startActivity(intent);
            }
        });
//        holder.description.setText(drugs.get(position).getDescription());
//        holder.type.setText(drugs.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
