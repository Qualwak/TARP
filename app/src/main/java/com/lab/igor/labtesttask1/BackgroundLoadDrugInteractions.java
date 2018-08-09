package com.lab.igor.labtesttask1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Adapter.SearchAdapter4;
import com.lab.igor.labtesttask1.Database.DatabaseHelper;
import com.lab.igor.labtesttask1.Model.DrugInteractionsN;
import com.lab.igor.labtesttask1.Model.FoodInteractions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Igor on 21-May-18.
 */

public class BackgroundLoadDrugInteractions extends AsyncTask<Void, DrugInteractionsN, Void> {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private TextView textView;
    private int j = 0;
    private SearchAdapter4 adapter;
    private ArrayList<DrugInteractionsN> drugInteractionsNS = new ArrayList<DrugInteractionsN>();
    private String drugName;
    private static final String TAG = "myLogs";

    public BackgroundLoadDrugInteractions(RecyclerView recyclerView, ProgressBar progressBar, Context context, String drugName, TextView textView) {
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.context = context;
        this.drugName = drugName;
        this.textView = textView;
    }

    public BackgroundLoadDrugInteractions() {

    }

    //in main UI thread
    @Override
    protected void onPreExecute() {
        adapter = new SearchAdapter4(drugInteractionsNS);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
    }

    //in separate background thread
    @Override
    protected Void doInBackground(Void... voids) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"drug_interactions.description, drugs2.name AS drug2_name"};
        String tableName = "drug_interactions INNER JOIN drugs on drug_interactions.drug1_id = drugs.id INNER JOIN drugs AS drugs2 ON drugs2.id = drug_interactions.drug2_id";
        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[] {"%"+drugName+"%", "%"+drugName+"%"}, null, null, null);
        String description, nameOfDrugInteraction;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                j++;
                Log.i(TAG, "number for now" + (++i));
                description = cursor.getString(cursor.getColumnIndex("drug2_name"));
                nameOfDrugInteraction = cursor.getString(cursor.getColumnIndex("description"));
                publishProgress(new DrugInteractionsN(description, nameOfDrugInteraction));
               /* try {
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
    protected void onProgressUpdate(DrugInteractionsN... values) {
        drugInteractionsNS.add(values[0]);
        adapter.notifyDataSetChanged();

    }

    //in main UI thread
    @Override
    protected void onPostExecute(Void aVoid) {
        if (j == 1)
            textView.setText(j + " drug interaction with " + drugName);
        else textView.setText(j + " drug interactions with " + drugName);
        progressBar.setVisibility(View.GONE);

    }


}
