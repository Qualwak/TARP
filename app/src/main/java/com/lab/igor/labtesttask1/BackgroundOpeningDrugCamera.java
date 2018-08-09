package com.lab.igor.labtesttask1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

/**
 * Created by Igor on 08-Aug-18.
 */

public class BackgroundOpeningDrugCamera extends AsyncTask<Void, Void, Void> {

    private ProgressBar progressBar;
    private Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
