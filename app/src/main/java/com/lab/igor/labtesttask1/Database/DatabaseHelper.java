package com.lab.igor.labtesttask1.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lab.igor.labtesttask1.Model.Drug;
import com.lab.igor.labtesttask1.Model.DrugInteraction;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 06-Apr-18.
 * With help of this Class we can get needed list with drug names, descriptions, interactions.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "drug_scheme.db";
    private static final int DB_VER = 1;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // function that gets all drugs from database
    public List<Drug> getDrug() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"id, name, description, type"};
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



                result.add(drug);
            } while (cursor.moveToNext());
        }

        return result;
    }

    //function that gets all drugs's name
    public List<String> getNames() {
        // creating SQLiteDatabase object
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"name"};
        String tableName = "drugs"; // name of the table in your database;

        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<String> result = new ArrayList<String>();

        if (cursor.moveToFirst()) {

            do {
                result.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<DrugInteraction> getDrugInteractions(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"drugs.name AS drug_name, drug_interactions.name, drug_interactions.description"};
        String tableName = "drug_interactions INNER JOIN drug_interaction_relations ON drug_interactions.id = drug_interaction_relations.drug_interaction_id INNER JOIN drugs ON drug_interaction_relations.drug_id = drugs.id"; // name of the table in your database;
        //String[] sqlSelect = {"name, name AS drug_name, description"};
        //String tableName = "drug_interactions";
        qb.setTables(tableName);

        //This will be like query: select * from FRIENDs WHERE Name LIKE %pattern%
        //if you want to get extract name, just change
        //Cursor cursor = qb.query(db, sqlSelect, "Name = ?", new String[] {name}, null, null, null);
        Cursor cursor = qb.query(db, sqlSelect, "drug_name LIKE ?", new String[] {"%"+name+"%"}, null, null, null);

        List<DrugInteraction> result = new ArrayList<DrugInteraction>();

        if (cursor.moveToFirst()) {

            do {
                DrugInteraction drugInteraction = new DrugInteraction();
                //drugInteraction.setDrugName(cursor.getString(cursor.getColumnIndex("drug_name")));
                drugInteraction.setName(cursor.getString(cursor.getColumnIndex("name")));
                //drugInteraction.setDescription(cursor.getString(cursor.getColumnIndex("description")));



                result.add(drugInteraction);
            } while (cursor.moveToNext());
        }

        return result;
    }


    //function gets drugs by name
    public List<Drug> getDrugsByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"id, name, description, type"};
        String tableName = "drugs"; // name of the table in your database;

        //set name of table from which we need to get information
        qb.setTables(tableName);

        //
        Cursor cursor = qb.query(db, sqlSelect, "Name LIKE ?", new String[] {"%"+name+"%"}, null, null, null);

        List<Drug> result = new ArrayList<Drug>();

        if (cursor.moveToFirst()) {
            //adding names that we need to our list
            do {
                Drug drug = new Drug();
                drug.setId(cursor.getInt(cursor.getColumnIndex("id")));
                drug.setName(cursor.getString(cursor.getColumnIndex("name")));
                drug.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                drug.setType(cursor.getString(cursor.getColumnIndex("type")));



                result.add(drug);
            } while (cursor.moveToNext());
        }

        return result;
    }


}
