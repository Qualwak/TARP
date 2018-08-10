package com.lab.igor.labtesttask1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Adapter.SearchAdapter;
import com.lab.igor.labtesttask1.Database.DatabaseHelper;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// All drugs

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "q";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    TextView textView2;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<String>();
    ArrayList<String> listOfUsersDrugs;
    ArrayList<String> listSuggestedTest;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        // init View
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        textView2 = (TextView) findViewById(R.id.text_found);


        Intent intent = getIntent();
//        String test_cut = intent.getStringExtra("text_view");
        ArrayList<String> list = intent.getStringArrayListExtra("text_view");
        listOfUsersDrugs = list;
        try {
            listSuggestedTest = returning2();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String final_cut = "";
//        for (int i = 0; i < test_cut.length(); i++) {
//            if (test_cut.charAt(i) != ' ' || test_cut.charAt(i) != '\n') {
//                final_cut += test_cut.charAt(i);
//            } else break;
//        }
//        final_cut = final_cut.split("\n")[0];
//
//        textView2.setText(final_cut.substring(0, final_cut.length() - 1));

        Log.v(TAG, textView2.getText().toString());
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
//                startSearch(text.toString());
                try {
                    startSearch(text.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        // Intent intent1 = getIntent();


        // init Adapter default set all result
        //adapter = new SearchAdapter(this, databaseHelper.getDrug());


//        adapter = new SearchAdapter(this, databaseHelper.getDrug(), list); through array

        adapter = new SearchAdapter(this, listSuggestedTest, list);


        //adapter = new SearchAdapter(this, databaseHelper.getDrugsByName(textView2.getText().toString()));
        recyclerView.setAdapter(adapter);
    }

    private void startSearch(String text) throws IOException {
        String[] localDrugs = returning();
        int searchedElementPosition = -1;
        ArrayList<String> oneElem = new ArrayList<String>();
        for (int i = 0; i < localDrugs.length; i++)
            if (localDrugs[i].contains(text.substring(1)))
                oneElem.add(localDrugs[i]);
        adapter = new SearchAdapter(this, oneElem, listOfUsersDrugs);
        recyclerView.setAdapter(adapter);
    }

    private void loadSuggestList() {
        //suggestList = databaseHelper.getNames();
//        suggestList = listSuggestedTest;
//        materialSearchBar.setLastSuggestions(suggestList);
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
