package com.lab.igor.labtesttask1.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import com.lab.igor.labtesttask1.AppPreLoadNew;
import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.SearchDrugsAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DrugsActivity extends AppCompatActivity {

    private static final String TAG = "DsActivity";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchDrugsAdapter adapter;

    TextView textView;

    MaterialSearchBar materialSearchBar;

    List<String> suggestList;
    List<String> listOfUsersDrugs;
    List<String> listSuggestedTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);

        if (Objects.isNull(AppPreLoadNew.cResources)) {
            AppPreLoadNew.cResources = getResources();
            AppPreLoadNew.reinitialize();
        }

        recyclerView = findViewById(R.id.recycler_search);
        materialSearchBar = findViewById(R.id.search_bar);
        textView = findViewById(R.id.text_found);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        listOfUsersDrugs = getIntent().getStringArrayListExtra("text_view");
        listSuggestedTest = foo();


        Log.v(TAG, textView.getText().toString());

        materialSearchBar.setHint("Search");

        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList) {
                    if (search.toLowerCase().startsWith(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {

            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                try {
                    startSearch(text.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {}
        });

        adapter = new SearchDrugsAdapter(this, listSuggestedTest, listOfUsersDrugs);
        recyclerView.setAdapter(adapter);

    }

    private void startSearch(String text) throws IOException {
        List<String> oneElemNew = foo().stream()
                                       .filter(string -> string.contains(text.substring(1)))
                                       .collect(Collectors.toList());

        adapter = new SearchDrugsAdapter(this, oneElemNew, listOfUsersDrugs);
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = listSuggestedTest;
        materialSearchBar.setLastSuggestions(suggestList.subList(0, 15));
    }

    public List<String> foo() {
        return AppPreLoadNew.getFooDrugs();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DrugsActivity.this, ProfileActivity.class));
    }

}
