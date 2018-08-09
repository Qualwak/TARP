package com.lab.igor.labtesttask1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    // creating links to Button objects
    private Button buttonDrugs;
    private Button buttonDrugInteractionsLookup;
    private Button buttonDrugDrugInteractionChecker;
    private Button buttonFoodInteractionLookup;
    private Button buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load our activity
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        if (firstStart) {
            showStartDialog();
        }

        //buttonDrugs = findViewById(R.id.drugs);
//        buttonDrugs.setOnClickListener(this);

        buttonDrugDrugInteractionChecker = findViewById(R.id.drug_drug_interaction_checker);
        buttonDrugDrugInteractionChecker.setOnClickListener(this);

        buttonDrugInteractionsLookup = findViewById(R.id.drug_interaction_lookup);
        buttonDrugInteractionsLookup.setOnClickListener(this);

        buttonFoodInteractionLookup = findViewById(R.id.food_interaction_lookup);
        buttonFoodInteractionLookup.setOnClickListener(this);

        buttonSettings = findViewById(R.id.settings);
        buttonSettings.setOnClickListener(this);


    }

    public void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Settings")
                .setMessage("Do you want to set your information?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent3 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent3);
                    }
                })
                .create().show();
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    /*
    * sending searching text to needed activity
    * */
    public void onClick(View view) {

        switch (view.getId()) {
            /*case R.id.drugs:
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.putExtra("info", buttonDrugs.getText().toString());
                startActivity(intent);
                break;*/
            case R.id.drug_interaction_lookup:

                Toast.makeText(this, "Please wait, it is opening...", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(Main2Activity.this, OcrCaptureActivity.class);
                        intent1.putExtra("info", buttonDrugInteractionsLookup.getText().toString());
                        startActivity(intent1);
                    }
                }).start();
                break;
            case R.id.food_interaction_lookup:
//                Intent intent2 = new Intent(Main2Activity.this, MainActivity.class);
//                intent2.putExtra("info", buttonFoodInteractionLookup.getText().toString());
//                startActivity(intent2);
                Toast.makeText(this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent2 = new Intent(Main2Activity.this, OcrCaptureActivity.class);

                        intent2.putExtra("info", buttonFoodInteractionLookup.getText().toString());

                        startActivity(intent2);
                    }
                }).start();
//                Intent intent2 = new Intent(Main2Activity.this, OcrCaptureActivity.class);
//
//                Toast.makeText(this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
//                intent2.putExtra("info", buttonFoodInteractionLookup.getText().toString());
//
//                startActivity(intent2);
                break;
            case R.id.drug_drug_interaction_checker:
                Intent intent3 = new Intent(Main2Activity.this, MainActivity.class);
                intent3.putExtra("info", buttonDrugDrugInteractionChecker.getText().toString());
                startActivity(intent3);
                break;
            case R.id.settings:
                Intent intent4 = new Intent(Main2Activity.this, MainSpeechActivity.class);
                intent4.putExtra("info", buttonDrugDrugInteractionChecker.getText().toString());
                startActivity(intent4);
                break;
            default:
                break;


        }
    }

}
