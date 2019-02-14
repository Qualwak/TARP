package com.lab.igor.labtesttask1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lab.igor.labtesttask1.background.BackgroundLoadDrugInteractions;
import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.SearchDrugInteractionsAdapter;
import com.lab.igor.labtesttask1.model.DrugInteraction;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DrugInteractionsActivity extends AppCompatActivity {

    private static final String TAG = "DrugInteractions";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    SearchDrugInteractionsAdapter adapter;
    TextView textView;
    TextView numberOfInteractions;
    boolean progress;
    private static byte whereToGo = 0;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_interactions);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        textView = (TextView) findViewById(R.id.text_found);
        progressBar = (ProgressBar) findViewById(R.id.progressBarDrugInteractions);
        numberOfInteractions = (TextView) findViewById(R.id.number_of_interactions);
        progress = false;


        Intent intent2 = getIntent();
        String test_cut = intent2.getStringExtra("text_view");
        final String[] answer_cut = test_cut.split("[\\n\\s]");
        answer_cut[0] = answer_cut[0].substring(0, 1).toUpperCase() + answer_cut[0].substring(1).toLowerCase();
        textView.setText(answer_cut[0]);
        Log.v(TAG, textView.getText().toString());

        // setup search bar
        materialSearchBar.setHint("Search");


        //materialSearchBar.setText(intent.getStringExtra("text_view"));
        //materialSearchBar.setCardViewElevation(10);
        loadSuggestList();


        materialSearchBar.addTextChangeListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.v(TAG, "BEFORE");

//                recyclerView.setAdapter(adapter);
//                materialSearchBar.setLastSuggestions(suggestList);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
                Log.v(TAG, "ON");
//                if (adapter !=  new SearchDrugInteractionsAdapter(getApplicationContext(), getInteractedDrugs())){
//                    adapter = new SearchDrugInteractionsAdapter(getApplicationContext(), getInteractedDrugs());
//                    recyclerView.setAdapter(adapter);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.v(TAG, "AFTER");
//                adapter = new SearchDrugInteractionsAdapter(getApplicationContext(), getInteractedDrugs());
//                recyclerView.setAdapter(adapter);
//                materialSearchBar.setLastSuggestions(suggestList);
            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
//                if (!enabled)
//                    recyclerView.setAdapter(adapter);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                Log.v(TAG, "WHAT IS THE PAGE");
//                recyclerView.setAdapter(adapter);
            }
        });
        // Intent intent1 = getIntent();
        // init Adapter default set all result
        //adapter = new SearchDrugsAdapter(this, databaseHelper.getDrug());

//        if (getInteractedDrugs() != null)
//            adapter = new SearchDrugInteractionsAdapter(this, getInteractedDrugs());
//        new BackgroundLoadDrugInteractions(recyclerView, progressBar, this, textView.getText().toString(), numberOfInteractions).execute();
//        adapter = new SearchDrugInteractionsAdapter(this, getInteractedDrugs());
        Log.v(TAG, "Here is nothing");
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new BackgroundLoadDrugInteractions(recyclerView, progressBar,
                this, textView.getText().toString(), numberOfInteractions, materialSearchBar)
                .execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startSearch(String text) {
//        adapter = new SearchDrugInteractionsAdapter(this, new BackgroundLoadDrugInteractions(recyclerView, progressBar, this, text, numberOfInteractions).getDrugInteractionsNS());
        List<DrugInteraction> drugs = getInteractedDrugs();
        String updatedText = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        if (drugs == null) Log.v(TAG, "LIST IS NULL");
        else {
//            List<DrugInteraction> drugsForSearch = new ArrayList<DrugInteraction>();
//            for (DrugInteraction drug : drugs) {
//                if (drug.getNameOfDrugInteraction().contains(updatedText)) drugsForSearch.add(drug);
//            }
//
//            adapter = new SearchDrugInteractionsAdapter(this, drugsForSearch);
//
//
            List<DrugInteraction> drugsForSearch = drugs.stream()
                                                           .filter(drug -> drug.getNameOfDrugInteraction()
                                                                          .contains(updatedText))
                                                           .collect(Collectors.toList());
            adapter = new SearchDrugInteractionsAdapter(this, drugsForSearch);

            whereToGo++;
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadSuggestList() {
        suggestList = getNamesOfDrugInteractions();
        materialSearchBar.setLastSuggestions(suggestList);
    }

    private List<String> getNamesOfDrugInteractions() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Set<String> set = preferences.getStringSet("names", null);
        List<String> names = new ArrayList<String>();

        if (set != null) names.addAll(set);
        else names.add("no interactions");

        return names;
    }

    private List<DrugInteraction> getInteractedDrugs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String response = sharedPreferences.getString("interacted_drugs", null);

        ArrayList<DrugInteraction> drugs = gson.fromJson(response,
                new TypeToken<List<DrugInteraction>>() {
                }.getType());

        return drugs;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v(TAG, "ON KEY DOWN");
        if (keyCode == KeyEvent.KEYCODE_BACK && whereToGo != 0) {
            adapter = new SearchDrugInteractionsAdapter(this, getInteractedDrugs());
            recyclerView.setAdapter(adapter);
            whereToGo = 0;
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public void onBackPressed() {
//        Log.v(TAG, "BACK PRESSED");
//        if (materialSearchBar.isSearchEnabled()) {
//            adapter = new SearchDrugInteractionsAdapter(this, getInteractedDrugs());
//            materialSearchBar.disableSearch();
//        } else {
//            super.onBackPressed();
//        }
//    }

}
