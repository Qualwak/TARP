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

    public List<FoodInteraction> getFoodInteractionsByDrugName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"interaction"};
        String tableName = "food_interactions INNER JOIN drugs on drugs.name = food_interactions.drug_name";
        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);
        List<FoodInteraction> result = new ArrayList<FoodInteraction>();

        if (cursor.moveToFirst()) {
            do {
                FoodInteraction foodInteractions = new FoodInteraction();
                foodInteractions.setInteraction(cursor.getString(cursor.getColumnIndex("interaction")));

                result.add(foodInteractions);
            } while (cursor.moveToNext());
        }

        return result;
    }


    /* not used */
    //function gets drugs by name
//    public List<Drug> getDrugsByName(String name) {
//        SQLiteDatabase db = getReadableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        // column names in table
//        String[] sqlSelect = {"id", "name", "description", "type", "synonyms"};
//        String tableName = "drugs"; // name of the table in your database;
//
//        //set name of table from which we need to get information
//        qb.setTables(tableName);
//
//        //
//        Cursor cursor = qb.query(db, sqlSelect, "synonyms LIKE ? OR name LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);
//
//        List<Drug> result = new ArrayList<Drug>();
//
//        if (cursor.moveToFirst()) {
//            //adding names that we need to our list
//            do {
//                Drug drug = new Drug();
//                drug.setId(cursor.getInt(cursor.getColumnIndex("id")));
//                drug.setName(cursor.getString(cursor.getColumnIndex("name")));
//                drug.setDescription(cursor.getString(cursor.getColumnIndex("description")));
//                drug.setType(cursor.getString(cursor.getColumnIndex("type")));
//                drug.setSynonyms(cursor.getString(cursor.getColumnIndex("synonyms")));
//
//
//                result.add(drug);
//            } while (cursor.moveToNext());
//        }
//
//        return result;
//    }

    /* not used */
//    public String getDrugInteractionByDrugName(String name) {
//        SQLiteDatabase db = getReadableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        // column names in table
//        String[] sqlSelect = {"drugs2.name AS drug2_name"};
//        String tableName = "drug_interactions INNER JOIN drugs on drug_interactions.drug1_id = drugs.id INNER JOIN drugs AS drugs2 ON drugs2.id = drug_interactions.drug2_id"; // name of the table in your database;
//
//        //set name of table from which we need to get information
//        qb.setTables(tableName);
//
//        //
//        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null, "1");
//
//        String result = "";
//        if (cursor != null && cursor.moveToFirst()) {
//            result = cursor.getString(cursor.getColumnIndex("drug2_name"));
//            cursor.close();
//        }
//
//        return result;
//    }


}
