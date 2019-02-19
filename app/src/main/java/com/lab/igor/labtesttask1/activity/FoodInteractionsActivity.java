package com.lab.igor.labtesttask1.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.background.BackgroundLoadFoodInteractions;
import com.lab.igor.labtesttask1.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class FoodInteractionsActivity extends AppCompatActivity {

    private static final String TAG = "FIsActivity";

    ProgressBar progressBar;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;


    TextView textView;
    TextView textViewNumberOfInteractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_interactions);

        progressBar = (ProgressBar) findViewById(R.id.progressBarFoodInteractions);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        textView = (TextView) findViewById(R.id.text_found);
        textViewNumberOfInteractions = (TextView) findViewById(R.id.number_of_interaction);

        String label = getIntent().getStringExtra("text_view").split("[\\n\\s]")[0];
        String formattedLabel = label.substring(0, 1).toUpperCase() + label.substring(1).toLowerCase();
        textView.setText(formattedLabel);

        Log.d(TAG, String.format("recognized text was formatted to %s", formattedLabel));
    }

    @Override
    protected void onResume() {
        super.onResume();

        new BackgroundLoadFoodInteractions(recyclerView, progressBar, this,
                textView.getText().toString(), textViewNumberOfInteractions).execute();
    }

}


