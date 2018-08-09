package com.lab.igor.labtesttask1;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
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
import com.lab.igor.labtesttask1.Adapter.MyRecyclerViewAdapter;
import com.lab.igor.labtesttask1.Adapter.MyRecyclerViewAdapterProfile;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    String[] SPINNERLIST = {"Male", "Female"};

    List<String> usersDrugs = new ArrayList<String>();
    ArrayList<String> drugssss;// = new ArrayList<String>();
    RecyclerView recyclerView;
    TextView textView;
    MaterialBetterSpinner materialBetterSpinner;

    MyRecyclerViewAdapterProfile adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView = (TextView) findViewById(R.id.users_birth_date);
        loadData();


        Button button = (Button) findViewById(R.id.button_on_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new PickerDialogs();
                datePicker.show(getFragmentManager(), "date_picker");
            }
        });

        Button addNewDrug = (Button) findViewById(R.id.add_drug_profile);
        addNewDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity3.class);

                intent2.putStringArrayListExtra("text_view", drugssss);
                startActivity(intent2);
            }
        });

        Button saveInfo = (Button) findViewById(R.id.button_save_profile);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGenderData();
            }
        });

//        Button startApp = (Button) findViewById(R.id.start_working);
//        startApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProfileActivity.this, Main2Activity.class);
//                startActivity(intent);
//            }
//        });

        Button drugInteractions = (Button) findViewById(R.id.user_drug_interactions);
        drugInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ProfileActivity.this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProfileActivity.this, OcrCaptureActivity.class);
                        // variables for passing to ocrcaptureactivity
                        intent.putStringArrayListExtra("users_drugs", drugssss);
                        intent.putExtra("info", "Drug");
                        startActivity(intent);
                    }
                }).start();
            }
        });

        Button foodInteractions = (Button) findViewById(R.id.user_food_interactions);
        foodInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ProfileActivity.this, "Please wait, it is opening...", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProfileActivity.this, OcrCaptureActivity.class);
                        intent.putExtra("info", "Food");
                        intent.putStringArrayListExtra("users_drugs", drugssss);
                        // variables for passing to ocrcaptureactivity
                        startActivity(intent);
                    }
                }).start();
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);
        loadGenderData();
        materialBetterSpinner.setAdapter(arrayAdapter);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_profile_drugs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapterProfile(getApplicationContext(), drugssss);
        recyclerView.setAdapter(adapter);


    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(drugssss);
//        editor.putString("drug list", json);
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

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("drug list", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        drugssss = gson.fromJson(json, type);

        if (drugssss == null) {
            drugssss = new ArrayList<String>();

        }
        String savedText = sharedPreferences.getString("saved_text", "Date of birth: unspecified yet");
        textView.setText(savedText);
    }

    public void setBirthDate(View view) {
        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getFragmentManager(), "date_picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(this, "selected date: " + i2 + "/" + i1 + "/" + i, Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        String currentDateString = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());

        textView.setText("Date of birth: " + currentDateString);
        saveData();
    }


//    public void loadBirthData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
//        calendarString = sharedPreferences.getString("birthday", "");
//        textView.setText(calendarString);
//    }


}
