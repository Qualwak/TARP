package com.lab.igor.labtesttask1.activity;

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
import com.lab.igor.labtesttask1.AppPreLoadNew;
import com.lab.igor.labtesttask1.background.BackgroundLoadDrugInteractions;
import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.SearchDrugInteractionsAdapter;
import com.lab.igor.labtesttask1.model.DrugInteraction;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DrugInteractionsActivity extends AppCompatActivity {

    private static final String TAG = "DIsActivity";

    TextView textView;
    TextView numberOfInteractions;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    SearchDrugInteractionsAdapter adapter;

    ProgressBar progressBar;
    MaterialSearchBar materialSearchBar;

    private static byte whereToGo = 0;

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


        String label = getIntent().getStringExtra("text_view").split("[\\n\\s]")[0];
        String formattedLabel = label.substring(0, 1).toUpperCase() + label.substring(1).toLowerCase();
        textView.setText(formattedLabel);

        Log.d(TAG, String.format("recognized text was formatted to %s", formattedLabel));

        materialSearchBar.setHint("Search");


        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search : AppPreLoadNew.getFooInteractedDrugsNames()) {
                    if (search.toLowerCase().startsWith(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }

                if (suggest.size() > 15) {
                    materialSearchBar.setLastSuggestions(suggest.subList(0, 15));
                } else {
                    materialSearchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {}

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {}
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new BackgroundLoadDrugInteractions(recyclerView, progressBar,
                this, textView.getText().toString(), numberOfInteractions, materialSearchBar)
                .execute();
    }

    private void startSearch(String text) {
        List<DrugInteraction> drugs = getInteractedDrugs();
        String updatedText = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        if (drugs == null) {
            Log.v(TAG, "LIST IS NULL");
        } else {

            List<DrugInteraction> drugsForSearch = drugs.stream()
                                                        .filter(drug -> drug.getName()
                                                                .contains(updatedText))
                                                        .collect(Collectors.toList());

            whereToGo++;

            adapter = new SearchDrugInteractionsAdapter(this, drugsForSearch);
            recyclerView.setAdapter(adapter);
        }
    }

    private List<DrugInteraction> getInteractedDrugs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String response = sharedPreferences.getString("interacted_drugs", null);

        return new Gson().fromJson(response,
                new TypeToken<List<DrugInteraction>>() {}.getType());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && whereToGo != 0) {
            adapter = new SearchDrugInteractionsAdapter(this, getInteractedDrugs());
            recyclerView.setAdapter(adapter);
            whereToGo = 0;
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
