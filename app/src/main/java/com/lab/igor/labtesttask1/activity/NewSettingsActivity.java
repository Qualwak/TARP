package com.lab.igor.labtesttask1.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

import com.lab.igor.labtesttask1.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NewSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_settings);

    }

    public void openTalkBackSupportActivity(View view) {
        startActivity(new Intent(NewSettingsActivity.this, DevelopingActivity.class));
    }

    public void openChooseModeActivity(View view) {
        startActivity(new Intent(NewSettingsActivity.this, DevelopingActivity.class));
    }

    public void openProfileSettingsActivity(View view) {
        startActivity(new Intent(NewSettingsActivity.this, ProfileActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewSettingsActivity.this, HomeActivity.class));
    }
}
