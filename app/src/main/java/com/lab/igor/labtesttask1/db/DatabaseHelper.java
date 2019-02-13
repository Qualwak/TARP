package com.lab.igor.labtesttask1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lab.igor.labtesttask1.model.Drug;
import com.lab.igor.labtesttask1.model.FoodInteractions;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 06-Apr-18.
 * With help of this Class we can get needed list with drug names, descriptions, interactions.
 */

public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "drugDB.db";
    //private static final String DB_NAME = "drugDB.db";
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



    /*public List<DrugInteractions> getDrugInteractionsByDrugName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"drug2_id"};
        String tableName = "drug_interactions";
        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, "id = ?", new String[] {"1"}, null, null, null);
        List<DrugInteractions> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                DrugInteractions drugInteractions = new DrugInteractions();
                drugInteractions.setDrug2Id(cursor.getInt(cursor.getColumnIndex("drug2_id")));

                result.add(drugInteractions);
            } while (cursor.moveToNext());
        }

        return result;
    }*/


 /*   public List<DrugInteractions> getDrugInteractions(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
*/
    // column names in table
    //String[] sqlSelect = {"drugs.name AS drug_name, drug_interactions.name, drug_interactions.description"};
    //     String[] sqlSelect = {"name"};
    //String[] sqlSelect = {"drug_interactions.id, drug_interactions.drug1_id, drug_interactions.drug2_id"};
    //String tableName = "drug_interactions INNER JOIN drug_interaction_relations ON drug_interactions.id = drug_interaction_relations.drug_interaction_id INNER JOIN drugs ON drug_interaction_relations.drug_id = drugs.id"; // name of the table in your database;

    //    String tableName = "drug_interactions";
    //String tableName = "drug_interactions INNER JOIN drugs on drug_interactions.drug1_id = drugs.id";

    //String[] sqlSelect = {"name, name AS drug_name, description"};
    //String tableName = "drug_interactions";
    //     qb.setTables(tableName);

    //This will be like query: select * from FRIENDs WHERE Name LIKE %pattern%
    //if you want to get extract name, just change
    //Cursor cursor = qb.query(db, sqlSelect, "Name = ?", new String[] {name}, null, null, null);

    //      Cursor cursor = qb.query(db, sqlSelect, "id = ?", new String[] {"1"}, null, null, null);
    //Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[] {"%"+name+"%","%"+name+"%"}, null, null, null);

//        List<DrugInteractions> result = new ArrayList<DrugInteractions>();

    //      if (cursor.moveToFirst()) {

    //        do {
    //          DrugInteractions drugInteractions = new DrugInteractions();
    //drugInteraction.setDrugName(cursor.getString(cursor.getColumnIndex("drug_name")));
    //        drugInteractions.setDrug2Id(cursor.getInt(cursor.getColumnIndex("drug2_id")));
    //drugInteraction.setDescription(cursor.getString(cursor.getColumnIndex("description")));

    //      result.add(drugInteractions);
    //} while (cursor.moveToNext());
    //}

//        return result;
    //  }

    public List<FoodInteractions> getFoodInteractionsByDrugName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"interaction"};
        String tableName = "food_interactions INNER JOIN drugs on drugs.name = food_interactions.drug_name";
        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);
        List<FoodInteractions> result = new ArrayList<FoodInteractions>();

        if (cursor.moveToFirst()) {
            do {
                FoodInteractions foodInteractions = new FoodInteractions();
                foodInteractions.setInteraction(cursor.getString(cursor.getColumnIndex("interaction")));

                result.add(foodInteractions);
            } while (cursor.moveToNext());
        }

        return result;
    }


    //function gets drugs by name
    public List<Drug> getDrugsByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"id", "name", "description", "type", "synonyms"};
        String tableName = "drugs"; // name of the table in your database;

        //set name of table from which we need to get information
        qb.setTables(tableName);

        //
        Cursor cursor = qb.query(db, sqlSelect, "synonyms LIKE ? OR name LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);

        List<Drug> result = new ArrayList<Drug>();

        if (cursor.moveToFirst()) {
            //adding names that we need to our list
            do {
                Drug drug = new Drug();
                drug.setId(cursor.getInt(cursor.getColumnIndex("id")));
                drug.setName(cursor.getString(cursor.getColumnIndex("name")));
                drug.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                drug.setType(cursor.getString(cursor.getColumnIndex("type")));
                drug.setSynonyms(cursor.getString(cursor.getColumnIndex("synonyms")));


                result.add(drug);
            } while (cursor.moveToNext());
        }

        return result;
    }

    public String getDrugInteractionByDrugName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // column names in table
        String[] sqlSelect = {"drugs2.name AS drug2_name"};
        String tableName = "drug_interactions INNER JOIN drugs on drug_interactions.drug1_id = drugs.id INNER JOIN drugs AS drugs2 ON drugs2.id = drug_interactions.drug2_id"; // name of the table in your database;

        //set name of table from which we need to get information
        qb.setTables(tableName);

        //
        Cursor cursor = qb.query(db, sqlSelect, "drugs.name LIKE ? OR drugs.synonyms LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null, "1");

        String result = "";
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("drug2_name"));
            cursor.close();
        }

        return result;
    }


}
