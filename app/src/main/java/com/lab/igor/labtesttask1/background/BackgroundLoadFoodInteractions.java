package com.lab.igor.labtesttask1.background;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.activity.FoodInteractionsActivity;
import com.lab.igor.labtesttask1.activity.WarningActivity;
import com.lab.igor.labtesttask1.adapter.SearchFoodInteractionsAdapter;
import com.lab.igor.labtesttask1.db.DatabaseHelperNew;
import com.lab.igor.labtesttask1.model.FoodInteraction;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BackgroundLoadFoodInteractions extends AsyncTask<Void, FoodInteraction, Void> {

    private Context context;
    private TextView textView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private String drugName;
    private SearchFoodInteractionsAdapter adapter;

    private int numberOfInteractions = 0;

    private ArrayList<FoodInteraction> foodInteractions = new ArrayList<FoodInteraction>();

    public BackgroundLoadFoodInteractions(RecyclerView recyclerView, ProgressBar progressBar, Context context, String drugName, TextView textView) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
        this.drugName = drugName;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        adapter = new SearchFoodInteractionsAdapter(foodInteractions);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = DatabaseHelperNew.getInstance(context).getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] sqlSelect = {"interaction"};
            String tableName = "food_interactions INNER JOIN drugs on drugs.name = food_interactions.drug_name";
            qb.setTables(tableName);

            cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?",
                    new String[]{"%" + drugName + "%", "%" + drugName + "%"}, null,
                    null, null);

            String interaction;

            if (cursor.moveToFirst()) {
                do {
                    numberOfInteractions++;
                    interaction = cursor.getString(cursor.getColumnIndex("interaction"));
                    publishProgress(new FoodInteraction(interaction));
                } while (cursor.moveToNext());
            }
        } finally {
            Objects.requireNonNull(cursor, "");
            cursor.close();
            DatabaseHelperNew.getInstance(context).close();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(FoodInteraction... values) {
        foodInteractions.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        String pattern = numberOfInteractions == 1 ? "" : "s";
        textView.setText(String.format(Locale.ENGLISH, "%d food interaction%s with %s",
                numberOfInteractions, pattern, drugName));

        progressBar.setVisibility(View.GONE);
    }

}
