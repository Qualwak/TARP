package com.lab.igor.labtesttask1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity  implements View.OnClickListener {

    //private static final String TAG = "q";
    private TextView textView1;
    private Button buttonDrugs;
    private Button buttonDrugInteractionsLookup;
    DatabaseHelper0 databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonDrugs = findViewById(R.id.drugs);
        buttonDrugs.setOnClickListener(this);
        buttonDrugInteractionsLookup = findViewById(R.id.drug_interaction_lookup);
        buttonDrugInteractionsLookup.setOnClickListener(this);


        textView1 = findViewById(R.id.text_search);

        Intent intent = getIntent();
        String test_cut = intent.getStringExtra("text_view");
        String final_cut = "";
        for (int i = 0; i < test_cut.length(); i++) {
            if (test_cut.charAt(i) != ' ') {
                final_cut += test_cut.charAt(i);
            } else break;
        }

        textView1.setText(final_cut.substring(0, final_cut.length() - 1));
      //  Log.v(TAG, textView1.getText().toString());
       // databaseHelper = new DatabaseHelper0(getApplicationContext());
    //    databaseHelper.createDataBase();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drugs:

                Intent intent = new Intent(Main2Activity.this, MainActivity3.class);
                intent.putExtra("info", textView1.getText().toString());
                startActivity(intent);
                break;
            case R.id.drug_interaction_lookup:
                Intent intent1 = new Intent(Main2Activity.this, Main4Activity.class);
                intent1.putExtra("info", textView1.getText().toString());
                startActivity(intent1);
                break;

            default:
                break;


        }
    }
}
