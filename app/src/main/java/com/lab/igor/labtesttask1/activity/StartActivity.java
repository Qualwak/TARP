package com.lab.igor.labtesttask1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lab.igor.labtesttask1.AppPreLoadNew;
import com.lab.igor.labtesttask1.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonDrugInteractionsLookup;
    private Button buttonFoodInteractionLookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        AppPreLoadNew.cResources = getResources();
        AppPreLoadNew.reinitialize();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        if (firstStart) showStartDialog();

        buttonDrugInteractionsLookup = findViewById(R.id.drug_interaction_lookup);
        buttonDrugInteractionsLookup.setOnClickListener(this);

        buttonFoodInteractionLookup = findViewById(R.id.food_interaction_lookup);
        buttonFoodInteractionLookup.setOnClickListener(this);

        Button buttonSettings = findViewById(R.id.settings);
        buttonSettings.setOnClickListener(this);


    }

    public void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Settings")
                .setMessage("Do you want to set your personal information?")
                .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    Intent intent3 = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent3);
                })
                .create().show();
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drug_interaction_lookup:
                Toast.makeText(this, "Please wait, it is opening...", Toast.LENGTH_SHORT).show();
                new Thread(() -> {
                    Intent intent1 = new Intent(StartActivity.this, OcrCaptureActivity.class);
                    intent1.putExtra("info", buttonDrugInteractionsLookup.getText().toString());
                    startActivity(intent1);
                }).start();
                break;
            case R.id.food_interaction_lookup:
                Toast.makeText(this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
                new Thread(() -> {
                    Intent intent2 = new Intent(StartActivity.this, OcrCaptureActivity.class);
                    intent2.putExtra("info", buttonFoodInteractionLookup.getText().toString());
                    startActivity(intent2);
                }).start();
                break;
            case R.id.settings:
                Intent intent4 = new Intent(StartActivity.this, NewSettingsActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
