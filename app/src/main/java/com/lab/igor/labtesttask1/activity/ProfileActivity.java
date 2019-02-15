package com.lab.igor.labtesttask1.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.igor.labtesttask1.PickerDialogs;
import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.ProfileDrugsAdapter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ProfileActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    public final static String MALE = "Male";
    public final static String FEMALE = "Female";

    ArrayList<String> drugs;
    RecyclerView recyclerView;
    TextView textView;

    MaterialBetterSpinner materialBetterSpinner;

    ProfileDrugsAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView = (TextView) findViewById(R.id.users_birth_date);
        loadData();


        Button button = (Button) findViewById(R.id.button_on_date);
        button.setOnClickListener(view -> {
            DialogFragment datePicker = new PickerDialogs();
            datePicker.show(getFragmentManager(), "date_picker");
        });

        Button addNewDrug = (Button) findViewById(R.id.add_drug_profile);
        addNewDrug.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, DrugsActivity.class);
            intent.putStringArrayListExtra("text_view", drugs);
            startActivity(intent);
        });

        Button saveInfo = (Button) findViewById(R.id.button_save_profile);
        saveInfo.setOnClickListener(view -> saveGenderData());

        Button drugInteractions = (Button) findViewById(R.id.user_drug_interactions);
        drugInteractions.setOnClickListener(view -> {
            Toast.makeText(ProfileActivity.this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();

            new Thread(() -> {
                Intent intent = new Intent(ProfileActivity.this, OcrCaptureActivity.class);

                // variables for passing to ocrcaptureactivity
                intent.putStringArrayListExtra("users_drugs", drugs);
                intent.putExtra("info", "Drug");
                startActivity(intent);
            }).start();
        });

        Button foodInteractions = (Button) findViewById(R.id.user_food_interactions);
        foodInteractions.setOnClickListener(view -> {
            Toast.makeText(ProfileActivity.this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
            new Thread(() -> {
                Intent intent = new Intent(ProfileActivity.this, OcrCaptureActivity.class);
                intent.putExtra("info", "Food");
                intent.putStringArrayListExtra("users_drugs", drugs);
                // variables for passing to ocrcaptureactivity
                startActivity(intent);
            }).start();
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Arrays.asList(MALE, FEMALE));

        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);

        loadGenderData();

        materialBetterSpinner.setAdapter(arrayAdapter);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile_drugs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProfileDrugsAdapter(getApplicationContext(), drugs);

        recyclerView.setAdapter(adapter);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("saved_text", textView.getText().toString());
        editor.apply();
    }

    private void saveGenderData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("saved_gender", materialBetterSpinner.getText().toString());
        editor.apply();
    }

    private void loadGenderData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String savedText = sharedPreferences.getString("saved_gender", "Press to choose your gender");
        materialBetterSpinner.setHint("Gender");
        materialBetterSpinner.setText(savedText);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("drug list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        drugs = new Gson().fromJson(json, type);

        if (Objects.isNull(drugs)) {
            drugs = new ArrayList<>();
        }

        String savedText = sharedPreferences.getString("saved_text", "Date of birth: unspecified yet");
        textView.setText(savedText);
    }

    public void setBirthDate(View view) {
        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getFragmentManager(), "date_picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Toast.makeText(this, String.format(Locale.ENGLISH, "selected date: %d/%d/%d",
                dayOfMonth, month, year), Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL)
                                                       .format(calendar.getTime());

        textView.setText(String.format("Date of birth: %s", currentDateString));

        saveData();
    }


}
