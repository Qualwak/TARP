package com.lab.igor.labtesttask1.background;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lab.igor.labtesttask1.activity.FoodInteractionsActivity;
import com.lab.igor.labtesttask1.activity.WarningActivity;
import com.lab.igor.labtesttask1.adapter.SearchDrugInteractionsAdapter;
import com.lab.igor.labtesttask1.db.DatabaseHelperNew;
import com.lab.igor.labtesttask1.model.DrugInteraction;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

public class BackgroundLoadDrugInteractions extends AsyncTask<Void, DrugInteraction, Void> {

    private static final String TAG = "myLogs";

    private Context context;
    private TextView textView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MaterialSearchBar materialSearchBar;

    private int numberOfInteractions = 0;

    private String drugName;
    private SearchDrugInteractionsAdapter adapter;

    private ArrayList<DrugInteraction> drugInteractions = new ArrayList<DrugInteraction>();
    private ArrayList<String> drugInteractionNames = new ArrayList<String>();
    private ArrayList<DrugInteraction> interactedDrugs = new ArrayList<DrugInteraction>();

    public BackgroundLoadDrugInteractions(RecyclerView recyclerView,
                                          ProgressBar progressBar, Context context, String drugName,
                                          TextView textView, MaterialSearchBar materialSearchBar) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
        this.drugName = drugName;
        this.textView = textView;
        this.materialSearchBar = materialSearchBar;
    }

    @Override
    protected void onPreExecute() {
        adapter = new SearchDrugInteractionsAdapter(drugInteractions);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Void doInBackground(Void... voids) {
        SQLiteDatabase db = DatabaseHelperNew.getInstance(context).getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        Cursor cursor = null;
        try {
            String[] sqlSelect = {"drug_interactions.description, drugs2.name AS drug2_name"};
            String tableName = "drug_interactions INNER JOIN drugs on drug_interactions.drug1_id = drugs.id INNER JOIN drugs AS drugs2 ON drugs2.id = drug_interactions.drug2_id";
            qb.setTables(tableName);

            cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[] {"%"+drugName+"%", "%"+drugName+"%"}, null, null, null);

            int i = 0;
            String description, nameOfDrugInteraction;
            if (cursor.moveToFirst()) {
                do {
                    numberOfInteractions++;
                    Log.i(TAG, "number for now" + (++i));

                    nameOfDrugInteraction = cursor.getString(cursor.getColumnIndex("drug2_name"));
                    description = cursor.getString(cursor.getColumnIndex("description"));

                    publishProgress(new DrugInteraction(nameOfDrugInteraction, description));

                    drugInteractionNames.add(nameOfDrugInteraction);
                    interactedDrugs.add(new DrugInteraction(nameOfDrugInteraction, description));
                } while (cursor.moveToNext());
            } else {
                Intent intent = new Intent(context.getApplicationContext(), WarningActivity.class);
                intent.putExtra("fromWhere", "drug");
                context.startActivity(intent);
            }

            passingInteractedDrugs(interactedDrugs);
        } finally {
            Objects.requireNonNull(cursor);
            cursor.close();
            DatabaseHelperNew.getInstance(context).close();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(DrugInteraction... values) {
        drugInteractions.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        materialSearchBar.setLastSuggestions(drugInteractionNames);

        String pattern = numberOfInteractions == 1 ? "" : "s";
        textView.setText(String.format(Locale.ENGLISH, "%d drug interaction%s with %s",
                numberOfInteractions, pattern, drugName));

        progressBar.setVisibility(View.GONE);
    }

    private void passingInteractedDrugs(ArrayList<DrugInteraction> interactedDrugs) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        String jsonDrugs = new Gson().toJson(interactedDrugs);

        editor.putString("interacted_drugs", jsonDrugs);
        editor.apply();
    }

}
