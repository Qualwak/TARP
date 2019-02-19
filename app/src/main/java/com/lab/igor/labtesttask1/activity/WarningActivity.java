package com.lab.igor.labtesttask1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.igor.labtesttask1.R;

public class WarningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        TextView sign = (TextView) findViewById(R.id.sign);
        if (getIntent().getStringExtra("fromWhere").equals("food")) {
            sign.setText("No known food interactions");
        } else {
            sign.setText("No know drug interactions");
        }
    }

    public void goToMainButton(View view) {
        startActivity(new Intent(WarningActivity.this, StartActivity.class));
    }

    public void tryAgainButton(View view) {
        Intent intent = new Intent(WarningActivity.this, OcrCaptureActivity.class);
        if (getIntent().getStringExtra("fromWhere").equals("food")) {
            intent.putExtra("info", "Food");
        } else {
            intent.putExtra("info", "Drug");
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        tryAgainButton(findViewById(R.id.try_again));
    }
}
