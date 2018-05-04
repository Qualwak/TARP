package com.lab.igor.labtesttask1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    // creating links to Button objects
    private Button buttonDrugs;
    private Button buttonDrugInteractionsLookup;
    private Button buttonDrugDrugInteractionChecker;
    private Button buttonFoodInteractionLookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //load our activity
        setContentView(R.layout.activity_main2);

        buttonDrugs = findViewById(R.id.drugs);
        buttonDrugs.setOnClickListener(this);

        buttonDrugDrugInteractionChecker = findViewById(R.id.drug_drug_interaction_checker);
        buttonDrugDrugInteractionChecker.setOnClickListener(this);

        buttonDrugInteractionsLookup = findViewById(R.id.drug_interaction_lookup);
        buttonDrugInteractionsLookup.setOnClickListener(this);

        buttonFoodInteractionLookup = findViewById(R.id.food_interaction_lookup);
        buttonFoodInteractionLookup.setOnClickListener(this);


    }

    /*
    * sending searching text to needed activity
    * */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drugs:
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.putExtra("info", buttonDrugs.getText().toString());
                startActivity(intent);
                break;
            case R.id.drug_interaction_lookup:
                Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
                intent1.putExtra("info", buttonDrugInteractionsLookup.getText().toString());
                startActivity(intent1);
                break;
            case R.id.food_interaction_lookup:
                Intent intent2 = new Intent(Main2Activity.this, MainActivity.class);
                intent2.putExtra("info", buttonFoodInteractionLookup.getText().toString());
                startActivity(intent2);
                break;
            case R.id.drug_drug_interaction_checker:
                Intent intent3 = new Intent(Main2Activity.this, MainActivity.class);
                intent3.putExtra("info", buttonDrugDrugInteractionChecker.getText().toString());
                startActivity(intent3);
                break;
            default:
                break;


        }
    }
}
