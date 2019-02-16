package com.lab.igor.labtesttask1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lab.igor.labtesttask1.model.Drug;
import com.lab.igor.labtesttask1.model.FoodInteraction;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "drugDB.db";
    private static final int DB_VER = 1;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // function that gets all drugs from database
    public List<Drug> getDrug() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"id", "name", "description", "type", "synonyms"};
        String tableName = "drugs"; // name of the table in your database;

        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<Drug> result = new ArrayList<Drug>();

        if (cursor.moveToFirst()) {
            do {
                Drug drug = new Drug();
                drug.setId(cursor.getInt(cursor.getColumnIndex("id")));
                drug.setName(cursor.getString(cursor.getColumnIndex("name")));
                drug.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                drug.setType(cursor.getString(cursor.getColumnIndex("type")));
                drug.setType(cursor.getString(cursor.getColumnIndex("synonyms")));

                result.add(drug);
            } while (cursor.moveToNext());
        }

        return result;
    }

}
