package com.lab.igor.labtesttask1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Adapter.SearchAdapter2;
import com.lab.igor.labtesttask1.Database.DatabaseHelper;
import com.lab.igor.labtesttask1.Model.FoodInteractions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 21-May-18.
 */

public class BackgroundTask extends AsyncTask<Void, FoodInteractions, Void> {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private TextView textView;
    private int i = 0;
    private SearchAdapter2 adapter;
    private ArrayList<FoodInteractions> foodInteractions = new ArrayList<FoodInteractions>();
    private String drugName;


    public BackgroundTask(RecyclerView recyclerView, ProgressBar progressBar, Context context, String drugName, TextView textView) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
        this.drugName = drugName;
        this.textView = textView;
    }


    //in main UI thread
    @Override
    protected void onPreExecute() {
        adapter = new SearchAdapter2(foodInteractions);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    //in separate background thread
    @Override
    protected Void doInBackground(Void... voids) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"interaction"};
        String tableName = "food_interactions INNER JOIN drugs on drugs.name = food_interactions.drug_name";
        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[]{"%" + drugName + "%", "%" + drugName + "%"}, null, null, null);
        String interaction;

        if (cursor.moveToFirst()) {
            do {
                i++;
                interaction = cursor.getString(cursor.getColumnIndex("interaction"));
                publishProgress(new FoodInteractions(interaction));
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            } while (cursor.moveToNext());


        }

        cursor.close();
        databaseHelper.close();
        return null;
    }


    //in main UI thread
    @Override
    protected void onProgressUpdate(FoodInteractions... values) {
        foodInteractions.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    //in main UI thread
    @Override
    protected void onPostExecute(Void aVoid) {
        if (i == 1)
            textView.setText(i + " food interaction with " + drugName);
        else textView.setText(i + " food interactions with " + drugName);
        progressBar.setVisibility(View.GONE);
    }


}
