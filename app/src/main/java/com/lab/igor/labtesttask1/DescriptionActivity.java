package com.lab.igor.labtesttask1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Igor on 28-May-18.
 */

public class DescriptionActivity extends AppCompatActivity {

    private static final String TAG = "DescriptionActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_drug_interaction);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("description")) {
            String description = getIntent().getStringExtra("description");

            setDescription(description);
        }

    }

    private void setDescription(String description) {
        TextView desc = findViewById(R.id.drug_drug_description);
        desc.setText(description);
    }
}
