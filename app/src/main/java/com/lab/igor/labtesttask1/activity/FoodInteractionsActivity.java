package com.lab.igor.labtesttask1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lab.igor.labtesttask1.background.BackgroundLoadFoodInteractions;
import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.SearchAdapter2;
import com.lab.igor.labtesttask1.db.DatabaseHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class FoodInteractionsActivity extends AppCompatActivity {

    private static final String TAG = "q";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter2 adapter;
    TextView textView2;
    TextView textViewNumberOfInteractions;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<String>();

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_interactions);
        progressBar = (ProgressBar) findViewById(R.id.progressBarFoodInteractions);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        textView2 = (TextView) findViewById(R.id.text_found);
        textViewNumberOfInteractions = (TextView) findViewById(R.id.number_of_interaction);


        Intent intent2 = getIntent();
        String test_cut = intent2.getStringExtra("text_view");
        String[] answer_cut = test_cut.split("[\\n\\s]");
        textView2.setText(answer_cut[0]);

        // init DB
        databaseHelper = new DatabaseHelper(this);

        // setup search bar
        materialSearchBar.setHint("Search");


        //materialSearchBar.setText(intent.getStringExtra("text_view"));
        //materialSearchBar.setCardViewElevation(10);
        loadSuggestList();


        materialSearchBar.addTextChangeListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        // Intent intent1 = getIntent();
        // init Adapter default set all result
        //adapter = new SearchAdapter(this, databaseHelper.getDrug());

        // adapter = new SearchAdapter2(this, databaseHelper.getFoodInteractionsByDrugName(textView2.getText().toString()));
        // recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new BackgroundLoadFoodInteractions(recyclerView, progressBar, this, textView2.getText().toString(), textViewNumberOfInteractions).execute();
    }

    private void startSearch(String text) {
        adapter = new SearchAdapter2(this, databaseHelper.getFoodInteractionsByDrugName(text));
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = databaseHelper.getNames();
        materialSearchBar.setLastSuggestions(suggestList);
    }
}


