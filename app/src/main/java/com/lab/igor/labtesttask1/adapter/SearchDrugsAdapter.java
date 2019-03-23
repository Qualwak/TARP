package com.lab.igor.labtesttask1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lab.igor.labtesttask1.activity.DrugsActivity;
import com.lab.igor.labtesttask1.activity.ProfileActivity;
import com.lab.igor.labtesttask1.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

import android.support.annotation.NonNull;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

class SearchViewDrugsHolder extends RecyclerView.ViewHolder implements Serializable {

    public TextView name;
    public Button buttonSave;


    public SearchViewDrugsHolder(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        name = (TextView) itemView.findViewById(R.id.name);
        buttonSave = (Button) itemView.findViewById(R.id.add_drug_to_profile);
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
    @NonNull
    @Override
    public SearchViewDrugsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);

        return new SearchViewDrugsHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final SearchViewDrugsHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.name.setText(drugs.get(position));

        holder.buttonSave.setOnClickListener(view -> {
            Log.e("SAVE BUTTON", "You have just pressed save button");

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.e("SAVE BUTTON", "Everything is okay till now");
            String drugName = drugs.get(position).substring(0, 1).toUpperCase() + drugs.get(position).substring(1);
//            String drugNameNew = Base64.getEncoder().encodeToString(drugName.getBytes());
//            if (list.contains(drugName)) {
//            } else {
//                list.add("HEELO" + list.size());
//                editor.putStringSet("druglist", null);
//                editor.apply();
//                editor.putStringSet("druglist", new HashSet<>(list));
            if (list.contains(drugName)) {
                Toast.makeText(context, "You already have " + drugName + " in you list!", Toast.LENGTH_SHORT).show();
            } else {
                list.add(drugName);
                String json = new Gson().toJson(list);
                editor.putString("drug list", json);
                editor.apply();
                Toast.makeText(context, drugName + "\nwas successfully added to your list", Toast.LENGTH_SHORT).show();
            }
            context.startActivity(new Intent(context, ProfileActivity.class));
//            }
        });

    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }
}
