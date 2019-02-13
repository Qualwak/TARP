package com.lab.igor.labtesttask1.background;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.adapter.SearchAdapter2;
import com.lab.igor.labtesttask1.db.DatabaseHelper;
import com.lab.igor.labtesttask1.model.FoodInteractions;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Igor on 21-May-18.
 */

public class BackgroundLoadFoodInteractions extends AsyncTask<Void, FoodInteractions, Void> {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private TextView textView;
    private int numberOfInteractions = 0;
    private SearchAdapter2 adapter;
    private ArrayList<FoodInteractions> foodInteractions = new ArrayList<FoodInteractions>();
    private String drugName;


    public BackgroundLoadFoodInteractions(RecyclerView recyclerView, ProgressBar progressBar, Context context, String drugName, TextView textView) {
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
                numberOfInteractions++;
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
//        if (numberOfInteractions == 1)
//            textView.setText(numberOfInteractions + " food interaction with " + drugName);
//        else textView.setText(numberOfInteractions + " food interactions with " + drugName);


//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(numberOfInteractions);
//        if (numberOfInteractions == 1) stringBuilder.append(" food interaction with ");
//        else stringBuilder.append(" food interactions with ");
//        stringBuilder.append(drugName);
//        textView.setText(stringBuilder);

        String pattern = numberOfInteractions == 1 ? "" : "s";
        textView.setText(String.format(Locale.ENGLISH, "%d food interaction%s with %s",
                numberOfInteractions, pattern, drugName));

        progressBar.setVisibility(View.GONE);
    }


}
