package com.lab.igor.labtesttask1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Adapter.SearchAdapter;
import com.lab.igor.labtesttask1.Adapter.SearchAdapter1;
import com.lab.igor.labtesttask1.Database.DatabaseHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

// Drug Interaction Lookup


public class Main4Activity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter1 adapter;
    TextView textView2;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<String>();

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);


        textView2 = (TextView) findViewById(R.id.text_found);

        textView2.setText(getIntent().getStringExtra("info"));

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
                for (String search: suggestList){
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

        adapter = new SearchAdapter1(this, databaseHelper.getDrugInteractions(textView2.getText().toString()));
        recyclerView.setAdapter(adapter);
        int i = adapter.getItemCount();
        String elems = Integer.toString(i);
        Log.i(TAG, elems);
    }

    private void startSearch(String text) {
        adapter = new SearchAdapter1(this, databaseHelper.getDrugInteractions(text));
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = databaseHelper.getNames();
        materialSearchBar.setLastSuggestions(suggestList);
    }
}

