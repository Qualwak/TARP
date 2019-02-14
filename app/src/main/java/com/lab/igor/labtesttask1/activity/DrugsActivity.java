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

import com.lab.igor.labtesttask1.R;
import com.lab.igor.labtesttask1.adapter.SearchDrugsAdapter;
import com.lab.igor.labtesttask1.db.DatabaseHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DrugsActivity extends AppCompatActivity {

    private static final String TAG = "DrugsActivity";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchDrugsAdapter adapter;
    TextView textView;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<String>();
    ArrayList<String> listOfUsersDrugs;
    ArrayList<String> listSuggestedTest;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        textView = (TextView) findViewById(R.id.text_found);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("text_view");
        listOfUsersDrugs = list;
        try {
            listSuggestedTest = returning2();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v(TAG, textView.getText().toString());

        databaseHelper = new DatabaseHelper(this);

        materialSearchBar.setHint("Search");

        loadSuggestList();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void afterTextChanged(Editable editable) {}
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
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

        adapter = new SearchDrugsAdapter(this, listSuggestedTest, list);

        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startSearch(String text) throws IOException {
//        String[] localDrugs = returning();
//        ArrayList<String> oneElem = new ArrayList<String>();
//        for (int i = 0; i < localDrugs.length; i++)
//            if (localDrugs[i].contains(text.substring(1)))
//                oneElem.add(localDrugs[i]);

        List<String> oneElem = Arrays.stream(returning())
                                        .filter(string -> string.contains(text.substring(1)))
                                        .collect(Collectors.toList());

        adapter = new SearchDrugsAdapter(this, oneElem, listOfUsersDrugs);
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = databaseHelper.getNames();
        suggestList = listSuggestedTest;
        materialSearchBar.setLastSuggestions(suggestList);
    }

    public String[] returning() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.test_8_output);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine().toLowerCase();
        String[] strings = new String[23027];
        int i = 0;
        while (line != null) {
            strings[i] = line;
            line = bufferedReader.readLine();
            if (line != null)
                line = line.toLowerCase();
            i++;
        }
        bufferedReader.close();
        return strings;
    }

    public ArrayList<String> returning2() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.test_8_output);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine().toLowerCase();
        String[] strings = new String[23027];
        ArrayList<String> stringsList = new ArrayList<>();
        int i = 0;
        while (line != null) {
            strings[i] = line;
            stringsList.add(line);
            line = bufferedReader.readLine();
            if (line != null)
                line = line.toLowerCase();
            i++;
        }
        bufferedReader.close();
        return stringsList;
    }
}
